package geometries;

import java.util.Arrays;
import java.util.List;
import primitives.*;

/**
 *Triangle
 *
 * @author Idan and Eliyahu
 */
public class Triangle extends Polygon {

   public  Triangle(Point x1, Point x2, Point x3)
    {
        super(x1,x2,x3);

    }
    
    @Override
    public boolean equals(Object obj) {//checks if equals
        return (obj instanceof Triangle) && super.equals(obj);
    }
    



    @Override 
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double max){
        /**
         * Triangle Area = abs(b-a) * abs(c-a) * sin(theta) / 2 
         * Triangle Area = abs((b-a) x (c-a)) / 2 
         * 
         */
try{
        List<GeoPoint> intersections = this.plane.findGeoIntersections(ray);
        Point P = intersections.get(0).point;
        Point A = this.vertices.get(0); 
        Point B = this.vertices.get(1); 
        Point C = this.vertices.get(2);     
        Vector CA = C.subtract(A);
        Vector BA = B.subtract(A);
        Vector PA;
        PA = P.subtract(A);
        double capArea = CA.crossProduct(PA).lengthSquared();
        double abcArea = CA.crossProduct(BA).lengthSquared();
        double abpArea = BA.crossProduct(PA).lengthSquared();
        double bcpArea;
        bcpArea = (B.subtract(P)).crossProduct(C.subtract(P)).lengthSquared(); 
        double uSquared = Util.alignZero(capArea / abcArea)  ; 
        double vSquared = Util.alignZero(abpArea / abcArea )   ; 
        double wSqaured =  Util.alignZero(bcpArea / abcArea)    ; 
        double u = Math.sqrt(uSquared);
        double v = Math.sqrt(vSquared);
        double w = Math.sqrt(wSqaured);
        if( Util.alignZero(u-1) < 0  && Util.alignZero(v-1)< 0 &&Util.alignZero(w-1)< 0 && !Util.isZero(u) && !Util.isZero(v)  && !Util.isZero(w) &&  Util.isZero(w+v+u-1) ){
            GeoPoint pt = new Intersectable.GeoPoint(P,this);
            if(Util.alignZero(pt.point.distanceSquared(ray.getP0()) -max * max  ) <= 0 ){
                return List.of(pt) ; 
            }
            return null ;
        } 
        return null ;
    }
    catch(Exception e) {
        return null ; 
    } 

    } 
    public double getS(){
        Point A = this.vertices.get(0); 
        Point B = this.vertices.get(1); 
        Point C = this.vertices.get(2);   
        Vector CA = C.subtract(A);
        Vector BA = B.subtract(A);
        return CA.crossProduct(BA).lengthSquared();
    }

    
}