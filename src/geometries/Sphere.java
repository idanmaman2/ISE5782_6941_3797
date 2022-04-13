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
public class Sphere extends Geometry{
    Point center;
    double radius;

    public Sphere(Point center, double radius) {//simple constructor
        this.center = center;
        this.radius = radius;
    }
    
    /**
    * calc by the formula of the slides 
    * @return normal 
    */
    @Override
    public Vector getNormal(Point point) {
        return point.subtract(center).normalize();
    }

    public boolean equals(Object obj) {
        return (obj instanceof Sphere) && this.center.equals(((Sphere) obj).center) && this.radius == ((Sphere) obj).radius;
    }

    
    @Override 
    public List<Point> findIntsersections(Ray rayC){
        Vector u; 
            double tm ;
            double d ; 
        if(center.equals(rayC.getP0())){
            tm = 0 ;
            d =0 ;
        }
        else {
           u = this.center.subtract(rayC.getP0()); 
             tm = rayC.getDir().dotProduct(u);
             d  = Math.sqrt(u.lengthSquared() - (tm*tm) ); 
        }
        

        if(Util.alignZero(d - this.radius) >= 0 ){
            return null; 
        }
        double th = Math.sqrt(this.radius * this.radius - d * d); 
        double t1 = tm + th ;  
        double t2 = tm - th ; 
        if(Util.alignZero(t2) <= 0 || Util.alignZero(t1) <= 0 ){
            return null ; 
        }
        LinkedList<Point> arr = new LinkedList<>(); 
        Point pt1 =  rayC.getPoint(t1);
        Point pt2 = rayC.getPoint(t2);
        if(Util.alignZero(t1) > 0 ) ){
            arr.add(rayC.getPoint(t1));
        }
        if(Util.alignZero(t2) > 0 ){
            arr.add(rayC.getPoint(t2));
        }
        return arr;
    }


    @Override 
    public List<GeoPoint> findGeoIntersectionsHelper(Ray rayC,double max){
        if(center == rayC.getP0()){
            return null;
        }
        Vector u = this.center.subtract(rayC.getP0()); 
        double tm = rayC.getDir().dotProduct(u);
        double d  = Math.sqrt(u.lengthSquared() - (tm*tm) ); 
        if( d >= this.radius ){
            return null; 
        }
        double th = Math.sqrt(this.radius * this.radius - d * d); 
        double t1 = tm + th ;  
        double t2 = tm - th ; 
        if(t1 <= 0 || t2 <= 0 ){
            return null ; 
        }
        Point pt1 =  rayC.getPoint(t1);
        Point pt2 = rayC.getPoint(t2);
        LinkedList<GeoPoint> arr = new LinkedList<>(); 
        if(t1 > 0 && Util.alignZero(pt1.distanceSquared(rayC.getP0()) - max * max) <= 0 ){
            arr.add(new Intersectable.GeoPoint(rayC.getPoint(t1),this));
        }
        if(t2 > 0 && Util.alignZero(pt1.distanceSquared(rayC.getP0()) - max * max) <= 0 ){
            arr.add(new Intersectable.GeoPoint(rayC.getPoint(t2),this) );
        }
        return arr;
    }








}