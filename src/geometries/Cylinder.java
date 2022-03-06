package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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
    public Vector getNormal(Point point) {
   
        double radiusSquared = this.radius * this.radius;
        Vector toOtherBase = this.axisRay.getDir().scale(this.height) ; 
        Point p0Ver2 = this.axisRay.getP0().add(toOtherBase);
        double crossProductp01 =  this.axisRay.getDir().dotProduct(this.axisRay.getP0().subtract(point));
        double crossProductp02 =   this.axisRay.getDir().dotProduct(p0Ver2.subtract(point));
        return   (point.distanceSquared(this.axisRay.getP0()) < radiusSquared  && crossProductp01  == 0) || 
        (point.distanceSquared(p0Ver2) < radiusSquared && crossProductp02 == 0) ? this.axisRay.getDir() : super.getNormal(point) ;
  
      
    }

}
