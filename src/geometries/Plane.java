package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane {
    public final Point q0 ;
    private final Vector normal ;
    public Plane(Point q1 , Point q2 , Point q3 ){
        this.q0 = q1 ;
        // to check that q1q2 is not colinear to q2q3 (that this is a plane and not a line )-TODO
        this.normal = new Vector(q1,q2).crossProduct(new Vector(q2,q3));


    }
    public Plane(Point q0, Vector normal) {
        this.q0 = q0;
        this.normal = normal;
    }

    public Vector getNormal(){
        return normal ;
    }

}
