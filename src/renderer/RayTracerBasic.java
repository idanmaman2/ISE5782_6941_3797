package renderer;

import java.util.LinkedList;
import java.util.List;

import Acc.Grid;
import Acc.Voxel;
import geometries.Intersectable;
import geometries.Intersectable.GeoPoint;
import lightning.*;
import primitives.*;
import Scene.Scene;
import primitives.Util.*;
public class RayTracerBasic extends RayTracerBase {
    private static final double DELTA = 0.1;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final Double3 INITIAL_K = new Double3(1.0);
    private static final double EPS = 0.1;
    static int total = 0 ; 
    static int hit ; 
    private final boolean fast3DDDA ;
    private  int size ; 
    private Grid grid =null;
    private int i =0, jy=0 , px=0 ,  py=0;
    public RayTracerBasic (Scene scene , boolean fast3DDDA) {
   
        super(scene);
        this.fast3DDDA = fast3DDDA; 
    }
    public RayTracerBasic (Scene scene ) {
        super(scene);
        this.fast3DDDA = false; 
    }

    public RayTracerBasic setSize(int size){
        this.size = size ;
        return this ;
    }
    
    public int getSize(){
        return this.size ;
    }

    public Grid getGrid(){
        if(grid == null){
            if(this.size == 0 ){
                throw new IllegalArgumentException("Grid size cant be 0 ");
            }
            this.grid = new Grid(sn.voxelableOnes.getItems() , size);
        }
        return this.grid;
    }



    /**
     *Calculate all the effects in project
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray, Double3 k) {

    
        Vector v = ray.getDir();
        Vector n = intersection.geometry.getNormal(intersection.point);
        double nv = Util.alignZero(n.dotProduct(v));
        if (nv == 0)
            return Color.BLACK;

        Material material = intersection.geometry.getMaterial();
        int nShininess = material.getnShininess();
        Double3 kd = material.getKd(), ks = material.getKs();
        Color color = intersection.geometry.getEmisson(intersection);
        for (LightSource lightSource : sn.lights) {
            Vector l = lightSource.getL(intersection.point);
            double nl = Util.alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                Double3 ktr = transparency(intersection, lightSource, l, n);
                if (!(ktr.product(k).lowerThan(MIN_CALC_COLOR_K))) {
                    Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);
                    color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                            calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                }
            } 
        }
        return color;
    }
    /**
     *Calculate the specular
     */
    private Color calcSpecular(Double3 kS, Vector l, Vector n, Vector v, int nShininess, Color intensity) {
        Vector r = l.subtract(n.scale(2 * (l.dotProduct(n))));
        return intensity.scale(kS.scale(Math.pow(Math.max(v.scale(-1).dotProduct(r), 0), nShininess)));
    }
    /**
     *Calculate the diffusion
     */
    private Color calcDiffusive(Double3 kd, Vector l, Vector n, Color lightIntensity) {
        return lightIntensity.scale(kd.scale(Math.abs(l.dotProduct(n))));
    }
    /**
     *all shades intersections
     */
    private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint geoPoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : - DELTA);
        Point point = geoPoint.point.add(delta);
        Ray lightRay = new Ray(point, lightDirection);
        List<GeoPoint> intersections = sn.geometries
                .findGeoIntersections(lightRay,Math.sqrt(light.distanceSquared(geoPoint.point)));
        return intersections == null || intersections.isEmpty() || geoPoint.geometry.getMaterial().kT != Double3.ZERO;
    }
    /**
     *Trace the ray
     */
    public Color  traceRay(Ray ray,int i ,int jy,int px , int py ){
        this.i= i ; 
        this.jy=jy;
        this.px=px ;
        this.py=py ;
    
        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ?  sn.getBg(i, jy, px, py): calcColor(closestPoint, ray);
    }
    /**
     *what color ray, calcColor
     */
    private Color calcColor(GeoPoint gp, Ray ray) {
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(sn.al.getIntensity());
    }
    /**
     *Again, Calculate all the effects on the Ray
     */
    private Color calcGlobalEffects(GeoPoint gp, Vector v, int level, Double3 k) {
        Color color = Color.BLACK; Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        Double3 kkr = k.product(material.kR);
        if (!(kkr.lowerThan(MIN_CALC_COLOR_K)))
            color = calcGlobalEffect(constructReflectedRay(gp.point, v, n), level, material.kR, kkr);
        Double3 kkt = k.product(material.kT);
        if (!(kkt.lowerThan(MIN_CALC_COLOR_K)))
            color = color.add(
                    calcGlobalEffect(constructRefractedRay(gp.point, v, n), level, material.kT, kkt));
        return color;
    }
    /**
     *Again, Calculate all the effects on the Ray
     */
    private Color calcGlobalEffect(Ray ray, int level, Double3 kx, Double3 kkx) {
        GeoPoint gp = findClosestIntersection(ray);
        return (gp == null ? sn.getBg(i, jy, px, py)  : calcColor(gp, ray, level-1, kkx)).scale(kx);
    }
    /**
     *Again, Calculate the Colors on the Ray
     */
    private Color calcColor(GeoPoint intersection, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(intersection, ray, k);
        return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray.getDir(), level, k));
    }
    /**
     *find Closest Intersection that the ray hits
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        if(fast3DDDA){
            return findClosestIntersectionFast(ray);
        }
        return findClosestIntersectionSlow(ray);
    }


    private GeoPoint findClosestIntersectionSlow (Ray ray ){
        List<GeoPoint> intersections = sn.geometries.findGeoIntersections(ray);
        if (intersections == null) return null;
        return ray.findClosestGeoPoint(intersections);
    }

    private GeoPoint findClosestIntersectionFast(Ray ray){
        List<GeoPoint> intersections = sn.regularOnes.findGeoIntersections(ray);
        GeoPoint closetVoxel = findClosestIntersectionFastVoxels(ray);
        if (intersections == null){
            return closetVoxel ;
        }
        GeoPoint closetUnVoxel =  ray.findClosestGeoPoint(intersections);
        if(closetVoxel == null ){
            
            return closetUnVoxel; 
        }
        if(ray.getP0().distanceSquared(closetUnVoxel.point) > ray.getP0().distanceSquared(closetVoxel.point)){
            return closetVoxel ; 
        }
        return closetUnVoxel ; 
    }


    private GeoPoint findCloserTest(Ray ray){
        GeoPoint closet = null ;
        for(int i= 0 ; i < getGrid().getSize() ; i++){ 
            for(int j =0  ; j < getGrid().getSize() ; j++){
                for(int k=0;k<getGrid().getSize() ; k++){
            
                    if(grid.collision(ray)){
                        Voxel vx =getGrid().getVoxel(i, j, k);
                    List<GeoPoint> points =vx .collisoned(ray);
                    if(points != null){
                        GeoPoint pot = ray.findClosestGeoPoint(points);
                        if(closet == null || pot.point.distanceSquared(ray.getP0()) < closet.point.distanceSquared(ray.getP0())){
                            closet = pot ; 
                        }
                    }
                }
                }
            }
        }
        return closet ; 

    }
<<<<<<< HEAD
=======
     int  is = 0 ; 
    private GeoPoint findCloserTest2(Ray ray){
        GeoPoint closet = null ;
        int size = getGrid().getSize();
        Voxel vx =getGrid().getVoxel((is/(size*size))%size, (is/size)%size, is%size);
                    List<GeoPoint> points =vx .collisoned(ray);
                    if(points != null){
                        GeoPoint pot = ray.findClosestGeoPoint(points);
                        if(closet == null || pot.point.distanceSquared(ray.getP0()) < closet.point.distanceSquared(ray.getP0())){
                            closet = pot ; 
                        }
                    }
                
                
            
        
        return closet ; 

    }
>>>>>>> f24d2b8c5af054fc9b79996d9fd09630b33492a6



    //TO DO  : TEST
    private GeoPoint findClosestIntersectionFastVoxels(Ray ray) {
      for(Voxel vox:grid.getPath(ray)){
          if(vox != null){
            List<GeoPoint> points = vox.collisoned(ray); 
            if(points != null ){
                return ray.findClosestGeoPoint(points);
            }
          }
  
      }
      return null ; 
    }


    /**
     *build to construct Reflected Ray
     */
    private Ray constructReflectedRay(Point p, Vector v, Vector n) {
        Vector r = v.subtract(n.scale(2 * v.dotProduct(n)));
        return new Ray(p, r,n);
    }
    /**
     *build to construct Reflected Ray
     */
    private Ray constructRefractedRay(Point p, Vector v, Vector n) {
        return new Ray(p, v,n);
    }
    /**
     *transparency, how transparent and if it is what texture
     */
    private Double3 transparency(GeoPoint geoPoint, LightSource ls, Vector l, Vector n) {

        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geoPoint.point, lightDirection, n);
        double lightDistance = ls.distanceSquared(geoPoint.point);
        var intersections = sn.geometries.findGeoIntersections(lightRay);
        if (intersections == null) return new Double3(1.0);
        Double3 ktr = new Double3(1.0);
        for (GeoPoint gp : intersections) {
            if (Util.alignZero(gp.point.distanceSquared(geoPoint.point) - lightDistance) <= 0) {
                ktr = ktr.product(gp.geometry.getMaterial().kT);
                if (ktr.lowerThan(MIN_CALC_COLOR_K)) return new Double3(0.0);
            }
        }
        return ktr;







    }
}