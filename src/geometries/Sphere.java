package geometries;

import primitives.Point;
import primitives.Vector;
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
}