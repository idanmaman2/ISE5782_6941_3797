package geometries;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import primitives.*;

/**
 *Spgere
 *
 * @author Idan and Eliyahu
 */
public class Sphere implements Geometry{
    Point center;
    double radius;

    public Sphere(Point center, double radius) {//simple constructor
        this.center = center;
        this.radius = radius;
    }
    
    /**
    * calc by the formula of the slides 
    * @return normal 
    */
    @Override
    public Vector getNormal(Point point) {
        return point.subtract(center).normalize();
    }

    public boolean equals(Object obj) {
        return (obj instanceof Sphere) && this.center.equals(((Sphere) obj).center) && this.radius == ((Sphere) obj).radius;
    }

    
    @Override 
    public List<Point> findIntsersections(Ray ray){
        Vector u = this.center.subtract(ray.getP0()); 
        double tm = ray.getDir().dotProduct(u);
        double d  = Math.sqrt(u.lengthSquared() - tm*tm ); 
        if( Util.alignZero(d  - this.radius) > 0  ){
            return null; 
        }
        double th = Math.sqrt(this.radius * this.radius - d * d); 
        double t1 = tm + th ;  
        double t2 = tm - th ; 
        if(t1 < 0 && t2 < 0 ){
            return null ; 
        }
        LinkedList<Point> arr = new LinkedList<>(); 
        if(t1 > 0  ){
            arr.add(ray.getPoint(t1));
        }
        if(t2 > 0 ){
            arr.add(ray.getPoint(t2));
        }
        return arr;
    }
}