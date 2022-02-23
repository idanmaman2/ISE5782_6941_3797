package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Cylinder extends Tube{
    double height;

    public Cylinder(Ray axisRay, double radius) {
        super(axisRay, radius);
    }

    @Override
    public Vector getNormal(Point point) {
        return super.getNormal(point);
    }
}
