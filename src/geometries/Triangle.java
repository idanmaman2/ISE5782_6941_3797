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
    public List<Point> findIntsersections(Ray ray){
        /**
         * Triangle Area = abs(b-a) * abs(c-a) * sin(theta) / 2 
         * Triangle Area = abs((b-a) x (c-a)) / 2 
         * 
         */
        List<Point> intersections = this.plane.findIntsersections(ray);
        Point P = intersections.get(0);
        Point A = this.vertices.get(0); 
        Point B = this.vertices.get(1); 
        Point C = this.vertices.get(2);     
        Vector CA = C.subtract(A);
        Vector BA = B.subtract(A);
        Vector PA;
        try{
            PA = P.subtract(A);
        }
        catch(IllegalArgumentException e) {
            return null ; 
        } 
        double capArea = CA.crossProduct(PA).lengthSquared();
        double abcArea = CA.crossProduct(BA).lengthSquared();
        double abpArea = BA.crossProduct(PA).lengthSquared();
        double bcpArea;
        try{
            bcpArea = (B.subtract(P)).crossProduct(C.subtract(P)).lengthSquared();
        }
        catch(IllegalArgumentException e) {
            return null ; 
        }  
        double uSquared = capArea / abcArea ; 
        double vSquared = abpArea / abcArea ; 
        double wSqaured = bcpArea / abcArea ; 
        double u = Math.sqrt(uSquared);
        double v = Math.sqrt(vSquared);
        double w = Math.sqrt(wSqaured);
        if(u < 1 && v< 1 && w < 1 && !Util.isZero(u) && !Util.isZero(v)  && !Util.isZero(w) &&  Util.isZero(w+v+u-1)){
            return List.of(P) ; 
        } 
        return null ;
    } 
    
}