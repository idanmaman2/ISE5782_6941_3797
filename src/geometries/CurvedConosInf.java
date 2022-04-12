
package geometries;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import primitives.*;

/**
 *Spgere
 *
 * @author Idan and Eliyahu
 */
public class CurvedConosInf extends Geometry{
    Point center;
    double radius;

    public CurvedConosInf(Point center, double radius) {//simple constructor
        this.radius = radius ;
        this.center= center ;
    }

    @Override
    public Vector getNormal(Point point) { //by the formulla in the slides 
        Ray axisRay = new Ray (center ,new Vector(1,0,0));
        double t  =  axisRay.getDir().dotProduct(
            point.subtract(
                axisRay.getP0()));
        Point O = ( t == 0 ?axisRay.getP0() :  axisRay.getP0().add(
            axisRay.getDir().scale(t)));
        return point.subtract(O).normalize();
    }



    @Override 
    public List<Point> findIntsersections(Ray rayC){
        Point centerZero = new Point(0,0,0);
        rayC = new Ray(rayC.getP0().subtract(center), rayC.getDir());
        double h1 = rayC.getDir().getX() , h2 = rayC.getDir().getY() , h3  = rayC.getDir().getZ()
        ,h12 = h1 * h1 , h22 = h2 * h2 , h32 = h3 * h3 , 
        q1 = rayC.getP0().getX() , q2 = rayC.getP0().getY() , q3 =rayC.getP0().getZ(),
        q12 = q1 * q1 , q22 = q2 *q2 ,q32 = q3 * q3 ; 
        double r12 = radius * radius  ; 
        double a = h22  + h32  , 
        b = 2 * ( h2*q2  + h3*q3)+h1*q1 , 
        c =   q22   + q32 - r12  ;
        double sqrt = b*b - 4 * a*c ;  
        if(sqrt < 0 ){
            return null ; 
        }
        sqrt = Math.sqrt(sqrt); 
        Point centerOps = new Point(center.getX() *-1 , center.getY() * -1 , center.getZ() * -1);
        if(sqrt == 0 ){
            double t = sqrt / (2 * a);
            return List.of(new Point(h1*t + q1 , h2 * t + q2 , h3 * t + q3).subtract(centerOps));
        }
        double t1 = (-b - sqrt)/(2*a) ; 
        double t2 = (-b - sqrt)/(2*a) ; 
        return List.of(
           new Point(h1*t1 + q1 , h2 * t1 + q2 , h3 * t1 + q3).subtract(centerOps), 
            new Point(h1*t2 + q1 , h2 * t2 + q2 , h3 * t2 + q3).subtract(centerOps) 
        );
    }




    @Override 
    public List<GeoPoint> findGeoIntersectionsHelper(Ray rayC){
        Point centerZero = new Point(0,0,0);
        rayC = new Ray(rayC.getP0().subtract(center), rayC.getDir());
        double h1 = rayC.getDir().getX() , h2 = rayC.getDir().getY() , h3  = rayC.getDir().getZ()
        ,h12 = h1 * h1 , h22 = h2 * h2 , h32 = h3 * h3 , 
        q1 = rayC.getP0().getX() , q2 = rayC.getP0().getY() , q3 =rayC.getP0().getZ(),
        q12 = q1 * q1 , q22 = q2 *q2 ,q32 = q3 * q3 ; 
        double r12 = radius * radius  ; 
        double a = h12 + h22  + h32  , 
        b = 2 * (h1*q1 + h2*q2  + h3*q3) , 
        c =  q12* + q22   + q32 - r12  ;
        double sqrt = b*b - 4 * a*c ;  
        if(sqrt < 0 ){
            return null ; 
        }
        sqrt = Math.sqrt(sqrt); 
        Point centerOps = new Point(center.getX() *-1 , center.getY() * -1 , center.getZ() * -1);
        if(sqrt == 0 ){
            double t = sqrt / (2 * a);
            return List.of(new Intersectable.GeoPoint(new Point(h1*t + q1 , h2 * t + q2 , h3 * t + q3).subtract(centerOps), this));
        }
        double t1 = (-b - sqrt)/(2*a) ; 
        double t2 = (-b - sqrt)/(2*a) ; 
        return List.of(
            new Intersectable.GeoPoint(new Point(h1*t1 + q1 , h2 * t1 + q2 , h3 * t1 + q3).subtract(centerOps), this) , 
            new Intersectable.GeoPoint(new Point(h1*t2 + q1 , h2 * t2 + q2 , h3 * t2 + q3).subtract(centerOps), this) 
        );
    }








}