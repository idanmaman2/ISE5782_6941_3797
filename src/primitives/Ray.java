package primitives;

import java.util.List;

import geometries.Intersectable;

/**
 *represnts linear ray in the real numbers world
 *that contains point of start and dir - linear line !
 *
 * @author Idan and Eliyahu
 */
public class Ray {
    private static final double DELTA = 0.1;

    private final  Point p0;
    private final Vector dir;

    public Point getP0() {

        return this.p0;
    }

    public Vector getDir() {
        return this.dir;
    }

    public Ray(Point p0, Vector dir) { 
        this.p0 = p0;
        this.dir = dir.normalize();
    }

public Ray(Point p0, Vector dir, Vector normal) {
        Vector delta = normal.scale(normal.dotProduct(dir) > 0 ? DELTA : - DELTA);
        this.p0 = p0.add(delta);
        this.dir = dir;
    }

    @Override
    /**
    *
    *checks if the two equal
    *
    */
    public boolean equals(Object obj) { 
        return (obj instanceof Ray) && (((Ray)obj).p0.equals(this.p0) && ((Ray)obj).dir.equals(this.dir));
    }

    @Override
    /**
    *converts ray obj to String 
    *
    */
    public String toString() {
        return "Ray{" +
                "p0=" + p0 +
                ", dir=" + dir +
                '}';
    }
    public Point getPoint(double t){
        if(Util.isZero(t)){
            return null ; 
         }
        return this.p0.add(this.dir.scale(t));
        }


    public Point findClosestPoint (List<Point> lst){
        Point closest = lst.get(0); ;
        for(Point item :lst){
            if(item.distanceSquared(this.p0) < closest.distanceSquared(this.p0)){
                closest = item;
            }
        }
        return closest;
    }
    /**
     *findClosestGeoPoint
     *
     * @author Idan and Eliyahu
     */
    public Intersectable.GeoPoint findClosestGeoPoint (List<Intersectable.GeoPoint> lst){
        Intersectable.GeoPoint closest = lst.get(0); ;
        for(Intersectable.GeoPoint item :lst){
            if(item.point.distanceSquared(this.p0) < closest.point.distanceSquared(this.p0)){
                closest = item;
            }
        }
        return closest;
    }

    

}