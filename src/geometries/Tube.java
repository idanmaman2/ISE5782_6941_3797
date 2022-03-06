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
    public Vector getNormal(Point point) {
        return null;
    }
}
