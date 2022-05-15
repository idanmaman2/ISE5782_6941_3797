package renderer;

import java.util.List;
import geometries.Intersectable.GeoPoint;
import lightning.*;
import primitives.*;
import Scene.Scene;
import static primitives.Util.*;
/**
 *RayTracerBasic
 *
 * @author Idan and Eliyahu
 */
public class RayTracerBasic extends RayTracerBase {
    private static final double DELTA = 0.1;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final Double3 INITIAL_K = new Double3(1.0);
    private static final double EPS = 0.1;
    int i =0, jy=0 , px=0 ,  py=0;
    public RayTracerBasic (Scene scene) {
        super(scene);
 
    }

    /**
     *Calculate all the effects in project
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray, Double3 k) {

    
        Vector v = ray.getDir();
        Vector n = intersection.geometry.getNormal(intersection.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0)
            return Color.BLACK;
        /**
         *Material type according to geometrys
         */
        Material material = intersection.geometry.getMaterial();
        int nShininess = material.getnShininess();
        Double3 kd = material.getKd(), ks = material.getKs();
        Color color = intersection.geometry.getEmisson(null);
        for (LightSource lightSource : sn.lights) {
            Vector l = lightSource.getL(intersection.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                if (unshaded(lightSource, l, n, intersection)) {
                    Color lightIntensity = lightSource.getIntensity(intersection.point);
                    color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                            calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                }
            } 
        }
        return color; //return a simple color
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
    public Color traceRay(Ray ray,int i ,int jy,int px , int py ) {
        this.i= i ; 
        this.jy=jy;
        this.px=px ;
        this.py=py ;
        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ? sn.getBg(i, jy, px, py) : calcColor(closestPoint, ray);
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
        return (gp == null ? sn.getBg(i, jy, px, py) : calcColor(gp, ray, level-1, kkx)).scale(kx);
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
        List<GeoPoint> intersections = sn.geometries.findGeoIntersections(ray);
        if (intersections == null) return null;
        return ray.findClosestGeoPoint(intersections);
    }
    /**
     *build to construct Reflected Ray
     */
    private Ray constructReflectedRay(Point p, Vector v, Vector n) {
        Vector r = v.subtract(n.scale(2 * v.dotProduct(n)));
        return new Ray(p, r);
    }
    /**
     *build to construct Reflected Ray
     */
    private Ray constructRefractedRay(Point p, Vector v, Vector n) {
        return new Ray(p, v);
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
            if (alignZero(gp.point.distanceSquared(geoPoint.point) - lightDistance) <= 0) {
                ktr = ktr.product(gp.geometry.getMaterial().kT);
                if (ktr.lowerThan(MIN_CALC_COLOR_K)) return new Double3(0.0);
            }
        }
        return ktr;
    }
    
}