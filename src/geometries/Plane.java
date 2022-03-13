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
        Vector v1 = q2.subtract(q1) ; // two new vectors
        Vector v2 =q3.subtract(q2) ; // two new vectors ;
        this.normal = v1.crossProduct(v2).normalize();
        this.q0 = q1 ;
    }
    public Plane(Point q0, Vector normal) { //simple constructor
        this.q0 = q0;
        this.normal = normal;
    }
        @Override
        public Vector getNormal(Point point) { //get
            return normal ;
        }
    public Vector getNormal() { // get without the point
        return normal ;
    }
    @Override
    public boolean equals(Object obj) {//checks if equals
        return (obj instanceof Plane) && this.normal.equals(((Plane) obj).normal) 
        && q0.subtract(((Plane) obj).q0).dotProduct(normal) == 0  ;
    }

}
