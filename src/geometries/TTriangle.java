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
public class TTriangle extends Triangle implements Textureable {
    Texture tx ;
    double scaleFactor = 100 ; 
    TPlane TexturePlane;
    List<ImageCords> speCords = null ; 
   public  TTriangle(Point x1, Point x2, Point x3,Texture tx)
    {
        
        super(x1,x2,x3);
        this.tx = tx ; 
        TexturePlane = new TPlane(this.plane,tx);

     }
     public  TTriangle(Triangle t1,Texture tx )
     {   
         this(t1.vertices.get(0),t1.vertices.get(1),t1.vertices.get(2),tx);
     } 
     public  TTriangle(Point x1, Point x2, Point x3,List<ImageCords> cords ,Texture tx)
     {
         super(x1,x2,x3);
         this.tx = tx ; 
         TexturePlane = new TPlane(this.plane,tx);
         speCords = cords ; 

      }
      public  TTriangle(Triangle t1,List<ImageCords> cords ,Texture tx )
      {
          this(t1.vertices.get(0),t1.vertices.get(1),t1.vertices.get(2),cords,tx);
      } 
    @Override
public Color getEmisson(GeoPoint x){
    return tx.getColor(x,super.getEmisson(x)); 
}
    
public ImageCords imaingnaion(Point pt , List<ImageCords> cordsOfVT ){
    // get the point to start in zero 
    // get the points to be in the first quarter 
    // straight scale 
    // dedo all what we did 
    // return the image cords 
    Point A = this.vertices.get(0); 
    Point B = this.vertices.get(1); 
    Point C = this.vertices.get(2);   
    Vector CA = C.subtract(A);
    Vector BA = B.subtract(A);
    Vector BC =C.subtract(B); 
    double angle11  =Math.acos(CA.dotProduct(BA)/(CA.length() * BA.length())) ;
     double angle12  = Math.acos(BA.dotProduct(BC)/(BA.length() * BC.length())) ;
     double angle13 = Math.acos(CA.dotProduct(BC)/(CA.length() * BC.length())) ;
     if(angle11 > Math.PI){
         angle11 = Math.PI-angle11 ; 
     }
     if(angle12 > Math.PI){
        angle12 = Math.PI-angle12 ; 
    }
    if(angle13 > Math.PI){
        angle13 = Math.PI-angle13 ; 
    }
    ImageCords cord1 = cordsOfVT.get(0);
    ImageCords cord2 =cordsOfVT.get(1);
    ImageCords cord3 = cordsOfVT.get(2); 
    ImageVector  vCord12  = cord1.substract(cord2);
    ImageVector vCord13 = cord1.substract(cord3);
    ImageVector vCord32 = cord2.substract(cord3);
    double angle21 =  Math.acos(vCord12.dotProduct(vCord13)/(vCord12.length() * vCord13.length())) ;
    double angle22 = Math.acos(vCord12.dotProduct(vCord32)/(vCord12.length() * vCord32.length())) ;
    double angle23 = Math.acos(vCord13.dotProduct(vCord32)/(vCord32.length() * vCord13.length())) ;
    if(angle21 > Math.PI){
        angle21 = Math.PI-angle21 ; 
    }
    if(angle22 > Math.PI){
       angle23 = Math.PI-angle22 ; 
   }
   if(angle23 > Math.PI){
       angle23 = Math.PI-angle23 ; 
   }



    return null ; 



}
  
public Texture.ImageCords TextureEmession(Point pt,int nX,int nY){
    return TexturePlane.TextureEmession(pt, nX, nY);
}
public Texture.ImageCords getDims(){
    Point A = this.vertices.get(0); 
    Point B = this.vertices.get(1); 
    Point C = this.vertices.get(2);   
    Vector CA = C.subtract(A);
    Vector BA = B.subtract(A);
    return new Texture.ImageCords(CA.length(),BA.length())  ;
}
}