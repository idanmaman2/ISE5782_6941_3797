package primitives;

import java.awt.*;
/**
 *represnts linear vector in the real numbers world
 *
 * @author Idan and Eliyahu
 */
public class Vector extends Point {

    public Vector (Point pt1 , Point pt2 ) { //from pt1 to pt2  -> pt1pt2 vector
        super(pt2.xyz.d1 - pt1.xyz.d1 ,
                pt2.xyz.d2 - pt1.xyz.d2,
                pt2.xyz.d3 - pt1.xyz.d3 );
        if (this.xyz.equals(Double3.ZERO))//gets a point
            throw  new IllegalArgumentException("cant enter the zero vector");
    }
    public Vector(double d1, double d2, double d3) { //simple constructor
        super(d1, d2, d3);
        if (this.xyz.equals(Double3.ZERO))//gets a point
            throw  new IllegalArgumentException("cant enter the zero vector");

    }

    @Override
    public boolean equals(Object obj) { // using fathers func
        return super.equals(obj);
    }

    @Override
    public String toString() { // prints the point
        return "Vector{" +
                "xyz=" + xyz +
                '}';
    }

    public Vector add(Vector vc) { // adding all vals for each parameter
        return new Vector(this.xyz.d1 + vc.xyz.d1,
                this.xyz.d2 + vc.xyz.d2,
                this.xyz.d3 + vc.xyz.d3);
    }

    public Vector scale(double sc) { // duplicate by scale
        return new Vector(sc * this.xyz.d1,
                sc * this.xyz.d2,
                sc * this.xyz.d3
        );
    }

    public double dotProduct(Vector vc) { // shows with product
        return this.xyz.d1 * vc.xyz.d1 +
                this.xyz.d2 * vc.xyz.d2 +
                this.xyz.d3 * vc.xyz.d3;

    }

    public double lengthSquared() { // Dot product
        return this.dotProduct(this);
    }

    public double length() { // returns square root
        return Math.sqrt(this.lengthSquared());
    }

    public Vector normalize(){ // normalize
        return this.scale(1/this.length());
    }
    /*
     * (x1,y1,z1) ( x2,y2,z2)
     *
     * x1 | y1  z1 x1 y1
     * x2 |  y2 z2 x2 y2
     * */
    public Vector crossProduct(Vector vc){ // two different vectors (minus), normal formula
        return new Vector( (this.xyz.d2 * vc.xyz.d3 - this.xyz.d3 * vc.xyz.d2)  ,
                (this.xyz.d3 * vc.xyz.d1 - this.xyz.d1 * vc.xyz.d3 )  ,
                (this.xyz.d1 * vc.xyz.d2 - this.xyz.d2 * vc.xyz.d1) ) ; 

    }

}
