package primitives;

import java.awt.*;

public class Vector extends Point {

    public Vector(double d1, double d2, double d3) {
        super(d1, d2, d3);
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

    public double lenghtSquared() {
        return this.dotProduct(this);
    }

    public double length() {
        return Math.sqrt(this.lenghtSquared());
    }

    public Vector normalize(){
        double length = this.length() ;
        return (length == 1  ?
                    this  :
                      this.scale(length));
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
}
