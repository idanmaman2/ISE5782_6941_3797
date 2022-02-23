package primitives;

import java.awt.*;

public class Vector extends Point {

    public Vector (Point pt1 , Point pt2 ) { //from pt1 to pt2  -> pt1pt2 vector
        super(pt2.xyz.d1 - pt1.xyz.d1 ,
                pt2.xyz.d2 - pt1.xyz.d2,
                pt2.xyz.d3 - pt1.xyz.d3 );
    }
    public Vector(double d1, double d2, double d3) {
        super(d1, d2, d3);
        if (new Double3(d1,d2,d3).equals(Double3.ZERO))
            throw  new IllegalArgumentException("cant enter the zero vector");

    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "Vector{" +
                "xyz=" + xyz +
                '}';
    }

    public Vector add(Vector vc) {
        return new Vector(this.xyz.d1 + vc.xyz.d1,
                this.xyz.d2 + vc.xyz.d2,
                this.xyz.d3 + vc.xyz.d3);
    }

    public Vector scale(double sc) {
        return new Vector(sc * this.xyz.d1,
                sc * this.xyz.d2,
                sc * this.xyz.d3
        );
    }

    public double dotProduct(Vector vc) {
        return this.xyz.d1 * vc.xyz.d1 +
                this.xyz.d2 * vc.xyz.d2 +
                this.xyz.d3 * vc.xyz.d3;

    }

    public double lengthSquared() {
        return this.dotProduct(this);
    }

    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    public Vector normalize(){
        double length = this.length() ;
        return (length == 1  ?
                this  :
                this.scale(1/length));
    }
    /*
     * (x1,y1,z1) ( x2,y2,z2)
     *
     * x1 | y1  z1 x1 y1
     * x2 |  y2 z2 x2 y2
     * */
    public Vector crossProduct(Vector vc){
        return new Vector( (this.xyz.d2 * vc.xyz.d3 - this.xyz.d3 * vc.xyz.d2)  ,
                (this.xyz.d3 * vc.xyz.d1 - this.xyz.d1 * vc.xyz.d3 )  ,
                (this.xyz.d1 * vc.xyz.d2 - this.xyz.d2 * vc.xyz.d1) ) ;

    }
    public boolean checkColinear(Vector v){
        double a = this.xyz.d1 / v.xyz.d1 ;
        double b = this.xyz.d2 / v.xyz.d2 ;
        double c = this.xyz.d3 / v.xyz.d3 ;
        return  ( a == b && b == c ); // a == c cause it is tarnsitiv


    }
}
