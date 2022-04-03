package renderer;

import java.util.List;

import Scene.Scene;
import primitives.*;

public class RayTracerBasic extends RayTracerBase{
    public RayTracerBasic(Scene sn) {
        super(sn);
    }
    private Color calcColor(Point pt){
        return sn.al.getIntensity() ;

    }
    @Override 
    public Color traceRay (Ray ray) {
       List<Point> intersecions = sn.geometries.findIntsersections(ray);
       if(intersecions == null  ){
           return sn.bg ;
       }
       Point closet = ray.findClosestPoint(intersecions);
       return this.calcColor(closet);

    }

}
