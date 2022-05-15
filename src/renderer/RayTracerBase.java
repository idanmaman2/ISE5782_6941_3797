package renderer;

import Scene.Scene;
import primitives.Color;
import primitives.Ray;
/**
 *ImageWriter
 *
 * @author Idan and Eliyahu
 */
public abstract class RayTracerBase  {
    protected Scene sn ;
    RayTracerBase(Scene sn){
        this.sn = sn ;
    }
    abstract  Color traceRay(Ray ray,int i ,int jy,int px , int py ) ;
}