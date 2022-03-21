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

    /**
     * t - sclar to mult the dir 
     * O - center of the circle of the tube in the plane of the point
     * @return normal 
     */
    @Override
    public Vector getNormal(Point point) { //by the formulla in the slides 
        double t  =  axisRay.getDir().dotProduct(
            point.subtract(
                axisRay.getP0()));
        Point O = ( t == 0 ?axisRay.getP0() :  axisRay.getP0().add(
            axisRay.getDir().scale(t)));
        return point.subtract(O).normalize();
    }
    
    @Override
    public boolean equals(Object obj) {//checks if equals
        return (obj instanceof Tube) && this.axisRay.equals(((Tube) obj).axisRay) &&  this.radius == ((Tube) obj).radius;
    }
}