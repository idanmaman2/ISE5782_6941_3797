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

    /**
    *
    *constructor that gets three points 
    *
    * @author Idan and Eliyahu
    */
    public Plane(Point q1 , Point q2 , Point q3 ){
        Vector v1 = q2.subtract(q1); // two new vectors
        Vector v2 = q3.subtract(q3) ;
        this.normal = v1.crossProduct(v2).normalize();
        this.q0 = q1 ;
    }

    /**
    *
    *simple constructor
    *
    * @author Idan and Eliyahu
    */
    public Plane(Point q0, Vector normal) { 
        this.q0 = q0;
        this.normal = normal;
    }

    /**
    *
    *get to Noraml with point as paramter 
    *
    * @author Idan and Eliyahu
    */
    @Override
    public Vector getNormal(Point point) { 
        return normal ;
    }

    /**
    *
    *get to Noraml withput paramters 
    *
    * @author Idan and Eliyahu
    */
    public Vector getNormal() { 
        return normal ;
    }

}
