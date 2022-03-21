package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;
/**
 *Tube
 *
 * @author Idan and Eliyahu
 */
public class Tube implements Geometry{

    protected Ray axisRay;
    protected double radius;

    public Tube(Ray axisRay, double radius) {//simple constructor
        this.axisRay = axisRay;
        this.radius = radius;
    }

    @Override
    public Vector getNormal(Point point) { //by the formulla in the slides 
        double t  =  axisRay.getDir().dotProduct(
            point.subtract(
                axisRay.getP0()));
        Point O = axisRay.getP0().add(
            axisRay.getDir().scale(t));
        return point.subtract(O).normalize();
    }
    @Override
    public boolean equals(Object obj) {//checks if equals
        return (obj instanceof Tube) && this.axisRay.equals(((Tube) obj).axisRay) &&  this.radius == ((Tube) obj).radius;
    }
}