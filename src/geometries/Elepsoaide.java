
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
public class Elepsoaide extends Geometry{
    Point center;
    public double radius2;
    public double radius3 ; 
    public double radius1 ; 

    public Elepsoaide(Point center, double radius1 , double radius2 ,double radius3 ) {//simple constructor
        this.center = center ;
        this.radius1 = radius1 ; 
        this.center = center;
        this.radius2 = radius2 ; 
        this.radius3 = radius3 ;
    }
    

    public Elepsoaide rotate(Vector axsis , double angle  ){
        Vector x = new Vector(radius1,0,0).Roatate(angle, axsis);
        Vector y = new Vector(0,radius2,0).Roatate(angle, axsis); 
        Vector z = new Vector(0,0,radius3).Roatate(angle, axsis);
        Vector total = x.add(z.add(y));
        return new Elepsoaide(center, total.getX() , total.getY(), total.getZ()) ;
    }





    @Override
    public Vector getNormal(Point point) {
        return new Vector(
        2*(point.getX()-center.getX()) * 1/(radius1*radius1) 
        , 2*(point.getY()-center.getY())  * 1/(radius2*radius2) 
        ,2*(point.getZ()-center.getZ())  * 1/(radius3*radius3) 
        ).normalize(); 
        //return point.subtract(center).normalize();
    }




    @Override 
    public List<GeoPoint> findGeoIntersectionsHelper(Ray rayC,double max){
        Point centerZero = new Point(0,0,0);
        rayC = new Ray(rayC.getP0().subtract(center), rayC.getDir());
        double h1 = rayC.getDir().getX() , h2 = rayC.getDir().getY() , h3  = rayC.getDir().getZ()
        ,h12 = h1 * h1 , h22 = h2 * h2 , h32 = h3 * h3 , 
        q1 = rayC.getP0().getX() , q2 = rayC.getP0().getY() , q3 =rayC.getP0().getZ(),
        q12 = q1 * q1 , q22 = q2 *q2 ,q32 = q3 * q3 ; 
        double r12 = radius1 * radius1 , r22 = radius2 * radius2  , r32 = radius3 * radius3 ; 
        double a = h12 * r22 * r32 + h22 *r12 * r32 + h32 *r12 * r22 , 
        b = 2 * (h1*q1*r22*r32 + h2*q2 * r12*r32 + h3*q3*r12*r22) , 
        c =  q12*r22*r32 + q22 * r12*r32  + q32*r12*r22 - r12 * r22 *r32 ;
        double sqrt = b*b - 4 * a*c ;  
        if(Util.alignZero(sqrt) < 0 ){
            return null ; 
        }
        sqrt = Math.sqrt(sqrt); 
        Point centerOps = new Point(center.getX() *-1 , center.getY() * -1 , center.getZ() * -1);
        if(Util.isZero(sqrt)){
            double t = sqrt / (2 * a);
            GeoPoint pt = new Intersectable.GeoPoint(new Point(h1*t + q1 , h2 * t + q2 , h3 * t + q3).subtract(centerOps),this);
            if(Util.alignZero(pt.point.distanceSquared(rayC.getP0())-max*max) <= 0 ){
                return List.of( pt);
            }
            return null ; 
            
        }
        double t1 = (-b - sqrt)/(2*a) ; 
        double t2 = (-b - sqrt)/(2*a) ; 
        GeoPoint pt1 = new Intersectable.GeoPoint(new Point(h1*t1 + q1 , h2 * t1 + q2 , h3 * t1 + q3).subtract(centerOps), this),
        pt2 =  new Intersectable.GeoPoint(new Point(h1*t2 + q1 , h2 * t2 + q2 , h3 * t2 + q3).subtract(centerOps), this) ;
        LinkedList<GeoPoint> lst = new LinkedList<>();
        if(t1 > 0 && Util.alignZero(pt1.point.distanceSquared(rayC.getP0()) - max * max ) <= 0 ){
            lst.add(pt1); 
        }
        if(t2 > 0 && Util.alignZero(pt2.point.distanceSquared(rayC.getP0()) - max * max ) <= 0 ){
            lst.add(pt2); 
        }

        return lst.isEmpty() ? null : lst ;
    }








}