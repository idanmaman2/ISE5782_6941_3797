package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;

public class Tube implements Geometry{

    protected Ray axisRay;
    protected double radius;

    public Tube(Ray axisRay, double radius) {//simple constructor
        this.axisRay = axisRay;
        this.radius = radius;
    }

    @Override
    public Vector getNormal(Point point) {
        double t  =  axisRay.getDir().dotProduct(
            point.subtract(
                axisRay.getP0()));
        Point O = axisRay.getP0().add(
            axisRay.getDir().scale(t));
        return point.subtract(O);
    }
}
