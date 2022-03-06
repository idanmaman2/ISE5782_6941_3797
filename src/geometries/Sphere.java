package geometries;

import primitives.Point;
import primitives.Vector;
/**
 *Spgere
 *
 * @author Idan and Eliyahu
 */
public class Sphere implements Geometry{
    final Point center;
    final double radius;

    public Sphere(Point center, double radius) {//simple constructor
        this.center = center;
        this.radius = radius;
    }

    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
