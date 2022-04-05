package renderer;

import java.util.List;

import Scene.Scene;
import geometries.Intersectable.GeoPoint;
import lightning.LightSource;
import primitives.*;

public class RayTracerBasic extends RayTracerBase{
    public RayTracerBasic(Scene sn) {
        super(sn);
    }
    private Color calcColor(GeoPoint pt, Ray ray){
      
        Color cr =  sn.al.getIntensity().add(pt.geometry.getEmisson());
        for(LightSource x : sn.lights){
            Vector n = pt.geometry.getNormal(pt.point).normalize();
            Vector l = x.getL(pt.point).normalize();
            Vector r = l.subtract(n.scale(l.dotProduct(n) * 2)).normalize();
            Color IL = x.getIntensity(pt.point);
            Vector v = ray.getDir().normalize();
            if(v.dotProduct(n) * l.dotProduct(n) > 0){
              // return cr ;
            }
            Double3 kd = pt.geometry.getMaterial().getKd();
            Double3 ks = pt.geometry.getMaterial().getKs();
            int shine = pt.geometry.getMaterial().getnShininess();
            cr = cr.
            add(IL.scale( kd.scale(Math.abs(l.dotProduct(n)))))
            .add(IL.scale(ks.scale(Math.pow(Math.max(0,v.dotProduct(r)),shine))));
        }
        return cr;
    }
    @Override 
    public Color traceRay (Ray ray) {
       List<GeoPoint> intersecions = sn.geometries.findGeoIntersections(ray);
       if(intersecions == null  ){
           return sn.bg ;
       }
       GeoPoint closet = ray.findClosestGeoPoint(intersecions);
       return this.calcColor(closet,ray);

    }

}
