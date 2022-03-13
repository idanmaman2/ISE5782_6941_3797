package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
/**
 *Limited Tube
 *
 * @author Idan and Eliyahu
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
    @Override
    public boolean equals(Object obj) {//checks if equals
        return (obj instanceof Cylinder) &&
        super.equals(obj)&&  this.height == ((Cylinder) obj).height;
    }
    @Override
    public Vector getNormal(Point point) {
        // checks if it in the bases center to avoid zero vector 
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

}
