package primitives;

import java.awt.*;
/**
 *represnts linear vector in the real numbers world
 *
 * @author Idan and Eliyahu
 */
public class Vector extends Point {

    public Vector(Double3 x ) { 
        super(x);
        if (this.xyz.equals(Double3.ZERO))//gets a point
            throw  new IllegalArgumentException("cant enter the zero vector");

    }

    public Vector(double d1, double d2, double d3) {
        super(d1, d2, d3);
        if (this.xyz.equals(Double3.ZERO))//gets a point
            throw  new IllegalArgumentException("cant enter the zero vector");

    }

    @Override
    public boolean equals(Object obj) { // using fathers func
        return obj instanceof Vector && super.equals(obj);
    }

    @Override
    public String toString() { // prints the point
        return "Vector{" +
                "xyz=" + xyz +
                '}';
    }

    /**
    * v+u
    *
    * @author Idan and Eliyahu
    */
    public Vector add(Vector vc) { 
        return new Vector(vc.xyz.add(this.xyz));}


    /**
    * vector * t 
    *
    * @author Idan and Eliyahu
    */
    public Vector scale(double sc) {
        return new Vector(this.xyz.scale(sc));
        
    }

    /**
    *Dot product
    *
    * @author Idan and Eliyahu
    */
    public double dotProduct(Vector vc) { 
        return this.xyz.d1 * vc.xyz.d1 +
                this.xyz.d2 * vc.xyz.d2 +
                this.xyz.d3 * vc.xyz.d3;

    }

    /**
    *length * length
    *
    * @author Idan and Eliyahu
    */
    public double lengthSquared() { 
        return this.dotProduct(this);
    }




    public Vector projection(Vector u){
        if ( u.dotProduct(this) == 0){
            return null;
        } 
        return u.scale( u.dotProduct(this) / u.lengthSquared());
    }



    /**
    * returns square root of length
    *
    * @author Idan and Eliyahu
    */
    public double length() { 
        return Math.sqrt(this.lengthSquared());
    }

    /**
    *normalize
    *
    * @author Idan and Eliyahu
    */
    public Vector normalize(){ 
        return this.scale(1/this.length());
    }

    /*
     * (x1,y1,z1) ( x2,y2,z2)
     *
     * x1 | y1  z1 x1 y1
     * x2 |  y2 z2 x2 y2
     * @ElliotYarmish @idanmaman2
     * */

    public Vector crossProduct(Vector vc){ 
        return new Vector( (this.xyz.d2 * vc.xyz.d3 - this.xyz.d3 * vc.xyz.d2)  ,
                (this.xyz.d3 * vc.xyz.d1 - this.xyz.d1 * vc.xyz.d3 )  ,
                (this.xyz.d1 * vc.xyz.d2 - this.xyz.d2 * vc.xyz.d1) ) ; 

    }
    public Vector Mirror(Vector u){ 
        return this.subtract(this.projection(u).scale(2));

    }

    public Vector Roatate(double angle , Vector axsis ){
        angle = angle / 180 * Math.PI ; 
        double cosa = Math.cos(angle ) , sina = Math.sin(angle); 
        double  x = axsis.xyz.d1 , y = axsis.xyz.d2 , z=axsis.xyz.d3 ,x2 = x*x ,y2 =y*y, z2 = z*z; 
        double tx = this.xyz.d1 , ty = this.xyz.d2 ,tz =this.xyz.d3 ; 
        return new Vector(
           (x2*(1-cosa)+ cosa )*tx +  (x*y*(1-cosa)-sina * z )*ty + (x*z*(1-cosa)+y*sina)*tz , 
           (x*y*(1-cosa)+z*sina)*tx + (y2*(1-cosa)+cosa)*ty + (y*z*(1-cosa)-x*sina)*tz , 
           (x*z*(1-cosa)-y*sina)*tx + (y*z*(1-cosa)+x*sina)*ty + (z2*(1-cosa)+cosa)*tz 
        );

    }

}
