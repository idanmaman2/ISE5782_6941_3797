package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane {
    public final Point q0 ;
    private final Vector normal ;
    public Plane(Point q1 , Point q2 , Point q3 ){
        Vector v1 = new Vector(q1,q2) ;
        Vector v2 = new Vector(q2,q3) ;
        if ( v1.checkColinear(v2))
            throw new IllegalArgumentException("this points creates vectors are colinear ");
        this.normal = v1.crossProduct(v2);
        this.q0 = q1 ;
    }
    public Plane(Point q0, Vector normal) {
        this.q0 = q0;
        this.normal = normal;
    }

    public Vector getNormal(){
        return normal ;
    }

}
