package geometries;


import primitives.Point;
import primitives.Vector;

public interface Geometry extends Intersectable {
    public Vector getNormal (Point point );
}

