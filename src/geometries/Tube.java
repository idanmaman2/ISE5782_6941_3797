package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;

public class Tube implements Geometry{

    protected Ray axisRay;
    protected double radius;

    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
