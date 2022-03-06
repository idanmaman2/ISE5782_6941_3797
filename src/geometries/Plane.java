package geometries;

import primitives.Point;
import primitives.Vector;
/**
 *Plane
 *
 * @author Idan and Eliyahu
 */
public class Plane implements Geometry {
    public final Point q0 ;
    private final Vector normal ;
    public Plane(Point q1 , Point q2 , Point q3 ){ //constructor
        Vector v1 = new Vector(q1,q2) ; // two new vectors
        Vector v2 = new Vector(q2,q3) ;
        this.normal = v1.crossProduct(v2).normalize();
        this.q0 = q1 ;
    }
    public Plane(Point q0, Vector normal) { //simple constructor
        this.q0 = q0;
        this.normal = normal;
    }
        @Override
        public Vector getNormal(Point point) { //get
            return null ;
        }
    public Vector getNormal() { // get without the point
        return null ;
    }

}
