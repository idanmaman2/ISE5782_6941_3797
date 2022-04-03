package geometries;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import primitives.*;

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

    /**
    *  get without the point
    * @return normal
    */
    public Vector getNormal() { 
        return normal ;
    }

    /**
    *  get with the point
    * @return normal
    */
    @Override
    public boolean equals(Object obj) {//checks if equals
        return (obj instanceof Plane) && this.normal.equals(((Plane) obj).normal) 
        && q0.subtract(((Plane) obj).q0).dotProduct(normal) == 0  ;
    }

    @Override 
    public List<Point> findIntsersections(Ray ray){

        double nv = this.getNormal().dotProduct(ray.getDir()); 
        if(Util.isZero(nv)){
            return  null ; 
        } 
        else{
            double nqp =  this.getNormal().dotProduct(this.q0.subtract(ray.getP0()));
            double t = nqp / nv  ; 
            if(Util.alignZero(t) > 0 ){
               return List.of(ray.getPoint(t));   
            }
            else{
                return null ; 
            }
        }


    }

    }

