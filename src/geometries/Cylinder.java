package geometries;

import primitives.Point;
import primitives.Vector;

public class Cylinder extends Tube{
    double height;

    public Cylinder(double height) {//simple constructor
        this.height = height;
    }

    @Override
    public Vector getNormal(Point point) {
        return super.getNormal(point);
    }
}
