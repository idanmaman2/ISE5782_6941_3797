package renderer;

import java.util.List;

import Scene.Scene;
import geometries.TSphere;
import geometries.Intersectable.GeoPoint;
import lightning.LightSource;
import primitives.*;

public class RayTracerBasic extends RayTracerBase{
    public RayTracerBasic(Scene sn) {
        super(sn);
    }
    private Color calcColor(GeoPoint intersection, Ray ray) {
        return sn.al.getIntensity()
            .add(intersection.geometry.getEmisson(intersection))
            .add(calcLocalEffects(intersection, ray));
    }

    private Color calcLocalEffects(GeoPoint intersection, Ray ray) {

        Vector v = ray.getDir();
        Vector n = intersection.geometry.getNormal(intersection.point);
        double nv = Util.alignZero(n.dotProduct(v));
        if (nv == 0)
            return Color.BLACK;
        Material material = intersection.geometry.getMaterial();
        int nShininess = material.getnShininess();
        Double3 kd = material.getKd(), ks = material.getKs();
        //Color color = Color.BLACK;
        Color color = intersection.geometry.getEmisson(intersection);
        for (LightSource lightSource : sn.lights) {
            Vector l = lightSource.getL(intersection.point);
            double nl = Util.alignZero(n.dotProduct(l));
            if (nl * nv > 0) { 
                Color lightIntensity = lightSource.getIntensity(intersection.point);
                color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                                    calcSpecular(ks, l, n, v, nShininess, lightIntensity));
            } 
        }
        return color;
    }

    private Color calcSpecular(Double3 kS, Vector l, Vector n, Vector v, int nShininess, Color intensity) {
        Vector r = l.subtract(n.scale(2 * (l.dotProduct(n))));
        return intensity.scale(kS.scale( Math.pow(Math.max(-v.dotProduct(r), 0), nShininess)));
    }

    private Color calcDiffusive(Double3 kd, Vector l, Vector n, Color lightIntensity) {
        return lightIntensity.scale(kd.scale(Math.abs(l.dotProduct(n))));
    }

    @Override 
    public Color traceRay (Ray ray , int i , int j  , int px , int py) {
       List<GeoPoint> intersecions = sn.geometries.findGeoIntersections(ray);
       if(intersecions == null  ){
           return sn.getBg(i, j ,px ,py ) ;
       }
       GeoPoint closet = ray.findClosestGeoPoint(intersecions);
       return this.calcColor(closet,ray);

    }

}
