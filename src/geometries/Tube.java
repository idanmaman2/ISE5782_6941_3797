package geometries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import primitives.*;
/**
 *Tube
 *
 * @author Idan and Eliyahu
 */
public class Tube extends Geometry{

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
    @Override
    public List<Point> findIntsersections(Ray ray) {
        Vector dir = ray.getDir();
        Vector v = axisRay.getDir();
        double dirV = dir.dotProduct(v);

        if (ray.getP0().equals(axisRay.getP0())) { // In case the ray starts on the p0.
            if (Util.isZero(dirV))
                return List.of(ray.getPoint(radius));

            if (dir.equals(v.scale(dir.dotProduct(v))))
                return null;

            return List.of(ray.getPoint(
                    Math.sqrt(radius * radius / dir.subtract(v.scale(dir.dotProduct(v)))
                            .lengthSquared())));
        }

        Vector deltaP = ray.getP0().subtract(axisRay.getP0());
        double dpV = deltaP.dotProduct(v);

        double a = 1 - dirV * dirV;
        double b = 2 * (dir.dotProduct(deltaP) - dirV * dpV);
        double c = deltaP.lengthSquared() - dpV * dpV - radius * radius;

        if (Util.isZero(a)) {
            if (Util.isZero(b)) { // If a constant equation.
                return null;
            }
            return List.of(ray.getPoint(-c / b)); // if it's linear, there's a solution.
        }

        double discriminant = Util.alignZero(b * b - 4 * a * c);

        if (discriminant < 0) // No real solutions.
            return null;

        double t1 = Util.alignZero(-(b + Math.sqrt(discriminant)) / (2 * a)); // Positive solution.
        double t2 = Util.alignZero(-(b - Math.sqrt(discriminant)) / (2 * a)); // Negative solution.

        if (discriminant <= 0) // No real solutions.
            return null;

        if (t1 > 0 && t2 > 0) {
            List<Point> _points = new LinkedList<Point>();
            _points.add(ray.getPoint(t1));
            _points.add(ray.getPoint(t2));
            return _points;
        }
        else if (t1 > 0) {
            List<Point> _points = new LinkedList<Point>();
            _points.add(ray.getPoint(t1));
            return  _points;
        }
        else if (t2 > 0) {
            List<Point> _points = new LinkedList<Point>();
            _points.add(ray.getPoint(t2));
            return _points;
        }
        return null;
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double max) {
        Vector dir = ray.getDir();
        Vector v = axisRay.getDir();
        double dirV = dir.dotProduct(v);

        if (ray.getP0().equals(axisRay.getP0())) { // In case the ray starts on the p0.
            if (Util.isZero(dirV))
                return List.of(new Intersectable.GeoPoint(ray.getPoint(radius),this) );

            if (dir.equals(v.scale(dir.dotProduct(v))))
                return null;

            return List.of(new Intersectable.GeoPoint(ray.getPoint(
                Math.sqrt(radius * radius / dir.subtract(v.scale(dir.dotProduct(v)))
                        .lengthSquared())),this) );
        }

        Vector deltaP = ray.getP0().subtract(axisRay.getP0());
        double dpV = deltaP.dotProduct(v);

        double a = 1 - dirV * dirV;
        double b = 2 * (dir.dotProduct(deltaP) - dirV * dpV);
        double c = deltaP.lengthSquared() - dpV * dpV - radius * radius;

        if (Util.isZero(a)) {
            if (Util.isZero(b)) { // If a constant equation.
                return null;
            }
            return List.of(new Intersectable.GeoPoint(ray.getPoint(-c / b),this)); // if it's linear, there's a solution.
        }

        double discriminant = Util.alignZero(b * b - 4 * a * c);

        if (discriminant < 0) // No real solutions.
            return null;

        double t1 = Util.alignZero(-(b + Math.sqrt(discriminant)) / (2 * a)); // Positive solution.
        double t2 = Util.alignZero(-(b - Math.sqrt(discriminant)) / (2 * a)); // Negative solution.

        if (discriminant <= 0) // No real solutions.
            return null;

        if (t1 > 0 && t2 > 0) {
            List<GeoPoint> _points = new LinkedList<GeoPoint>();
            _points.add(new Intersectable.GeoPoint(ray.getPoint(t1),this));
            _points.add(new Intersectable.GeoPoint(ray.getPoint(t2),this));
            return _points;
        }
        else if (t1 > 0) {
            List<GeoPoint> _points = new LinkedList<GeoPoint>();
            _points.add(new Intersectable.GeoPoint(ray.getPoint(t1),this));
            return  _points;
        }
        else if (t2 > 0) {
            List<GeoPoint> _points = new LinkedList<GeoPoint>();
            _points.add(new Intersectable.GeoPoint(ray.getPoint(t2),this));
            return _points;
        }
        return null;
    }

}