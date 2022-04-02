package geometries;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import primitives.*;
/**
 *Limited Tube
 *
 * @author Idan and Eliyahu
 */

 /**
     * constructor cylinder with get height 
     * @return double
     */
public class Cylinder extends Tube{
    double height;
    public double getHeight() {
        return this.height;
    }
    public Cylinder(Ray axisRay, double radius, double height ) {
        super(axisRay, radius);
        this.height = height; 
    }

    /**
     * equals between objects
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Cylinder) &&
        super.equals(obj)&&  this.height == ((Cylinder) obj).height;
    }

    @Override
    /**
     * get normal of vector
     * @return normal
     */
    public Vector getNormal(Point point) {
        
        //  checks if it in the bases center to avoid zero vector 
        if( point.equals(axisRay.getP0() ) ||  
            point.equals(axisRay.getP0().add(this.axisRay.getDir().scale(height)) ) ){
            return  this.axisRay.getDir() ;
        } 
        // checks if it is in the bases and if it is it returbns axsix dir and if it not calc like tube 
        double radiusSquared = this.radius * this.radius;
        Vector toOtherBase = this.axisRay.getDir().scale(this.height) ; 
        Point p0Ver2 = this.axisRay.getP0().add(toOtherBase);
        double crossProductp01 =  this.axisRay.getDir().dotProduct(this.axisRay.getP0().subtract(point));
        double crossProductp02 =   this.axisRay.getDir().dotProduct(p0Ver2.subtract(point));
        return  (point.distanceSquared(this.axisRay.getP0()) <= radiusSquared  && crossProductp01  == 0) || 
            (point.distanceSquared(p0Ver2) <= radiusSquared && crossProductp02 == 0) ? 
                this.axisRay.getDir() :
                super.getNormal(point) ;
  
      
    }
    
    @Override
    public List<Point> findIntsersections(Ray ray) {
        /**
         * we find the projection and calcs its size and if it is like the height the point inside the cilinder (we can use dot product cause the axsis in normalized )
         * 
         */
        List<Point> res = new ArrayList<>();
        List<Point> lst = super.findIntsersections(ray);
        if (lst != null)
            for (Point point : lst) {
                double distance = Util.alignZero(point.subtract(axisRay.getP0()).dotProduct(axisRay.getDir()));
                if (Util.isZero( distance- this.height))
                    res.add(point);
            }

        if (res.size() == 0)
            return null;
        return res;
    }

}