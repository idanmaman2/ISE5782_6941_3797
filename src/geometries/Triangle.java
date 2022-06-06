package geometries;

import java.util.Arrays;
import java.util.List;
import primitives.*;
import primitives.Texture.ImageCords;
import primitives.Texture.ImageVector;

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
        Vector PA =P.subtract(A);
        double abcArea = CA.crossProduct(BA).lengthSquared();
        double uSquared = Util.alignZero( CA.crossProduct(PA).lengthSquared() / abcArea)  ; 
        double vSquared = Util.alignZero(BA.crossProduct(PA).lengthSquared() / abcArea )   ; 
        double wSqaured =  Util.alignZero((B.subtract(P)).crossProduct(C.subtract(P)).lengthSquared() / abcArea)    ; 
        double u = Math.sqrt(uSquared);
        double v = Math.sqrt(vSquared);
        double w = Math.sqrt(wSqaured);
        if(   Util.isZero(w+v+u-1) ){
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

    
    static public class TrilinearPoint {
        final private  Double3 cords ; 
        final private Triangle tr ; 
        public TrilinearPoint(Point pt , Triangle tr ){
         
            this.tr= tr ;
            Point A = tr.vertices.get(0); 
            Point B = tr.vertices.get(1); 
            Point C = tr.vertices.get(2);   
            Ray CA =new Ray( C, C.subtract(A));
            Ray BA = new Ray(A,B.subtract(A));
            Ray BC =new Ray(B,C.subtract(B)); 
            cords = new Double3(pt.distanceFromLine(BC),pt.distanceFromLine(CA),pt.distanceFromLine(BA));
        }
        private  TrilinearPoint(Double3 db3, Triangle tr){
            cords = db3 ; 
            this.tr = tr ;
        }
        public TrilinearPoint(double d1 , double d2 , double d3,Triangle tr){
            cords = new Double3(d1, d2, d3);
            this.tr =tr ; 
        }
 
    
        public TrilinearPoint changeTriangle(Triangle newTriangle) {
            Point A = tr.vertices.get(0); 
            Point B = tr.vertices.get(1); 
            Point C = tr.vertices.get(2);   
            Vector CA = C.subtract(A);
            Vector BA = B.subtract(A);
            Vector BC =C.subtract(B); 
            double angle11  =Math.acos(CA.dotProduct(BA)/(CA.length() * BA.length())) ;
            double angle12  = Math.acos(BA.dotProduct(BC)/(BA.length() * BC.length())) ;
            double angle13 = Math.acos(CA.dotProduct(BC)/(CA.length() * BC.length())) ;
            List<Double> angles1 = List.of(angle11,angle12,angle13).stream().map((ele)-> ( ele> Math.PI ?Math.PI - ele : ele)).toList();
            Point cord1 = newTriangle.vertices.get(0);
            Point cord2 =newTriangle.vertices.get(1);
            Point cord3 = newTriangle.vertices.get(2); 
            Vector  vCord12  = cord1.subtract(cord2);
            Vector vCord13 = cord1.subtract(cord3);
            Vector vCord32 = cord2.subtract(cord3);
            double angle21 =  Math.acos(vCord12.dotProduct(vCord13)/(vCord12.length() * vCord13.length())) ;
            double angle22 = Math.acos(vCord12.dotProduct(vCord32)/(vCord12.length() * vCord32.length())) ;
            double angle23 = Math.acos(vCord13.dotProduct(vCord32)/(vCord32.length() * vCord13.length())) ;
            List<Double> angles2 = List.of(angle21,angle22,angle23).stream().map((ele)-> ( ele> Math.PI ?Math.PI - ele : ele)).toList();
            List<Integer> indexes = angles1.stream().map((ele)-> angles2.indexOf(ele)).toList();
            if(indexes.indexOf(-1) != -1){
                throw new IllegalArgumentException("there is no imagaine between the triangles!!!");
            }
            List<Double> cordsList =List.of(cords.d1 , cords.d2 , cords.d3);
            Double3 cordsNew = new Double3(cordsList.get(indexes.get(0)), cordsList.get(indexes.get(1)), cordsList.get(indexes.get(2))); 
            return new TrilinearPoint(cordsNew,newTriangle);
        }
        public Point toPoint(){
            Point A = tr.vertices.get(0); 
            Point B = tr.vertices.get(1); 
            Point C = tr.vertices.get(2);   
            Ray CA =new Ray( C, C.subtract(A));
            Ray BA = new Ray(A,B.subtract(A));
            Ray BC =new Ray(B,C.subtract(B)); 
            double a = BC.getDir().length() , b = CA.getDir().length() , c =BA.getDir().length(), 
            x = cords.d1 ,y = cords.d2 ,z =cords.d3, axbycz = a*x + b*y + c*z;
            return new Point(x*a/axbycz,y*b/axbycz , z*c/axbycz);
        }
    
        public  double findK(){
            Point A = tr.vertices.get(0); 
            Point B = tr.vertices.get(1); 
            Point C = tr.vertices.get(2);   
            Ray CA =new Ray( C, C.subtract(A));
            Ray BA = new Ray(A,B.subtract(A));
            Ray BC =new Ray(B,C.subtract(B)); 
            return tr.getS()*2/(cords.d1 * BC.getDir().length() + cords.d2 * CA.getDir().length() + cords.d3 * BA.getDir().length());
        }
    

        
    }
    
    
}