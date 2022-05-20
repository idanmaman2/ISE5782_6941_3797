package geometries;

import java.util.Arrays;
import java.util.List;
import primitives.*;

/**
 *Triangle
 *
 * @author Idan and Eliyahu
 */
public class TTriangle extends Triangle implements Textureable {
    Texture tx ;
    double scaleFactor = 100 ; 
    TPlane TexturePlane = new TPlane(this.plane,tx);
   public  TTriangle(Point x1, Point x2, Point x3,Texture tx)
    {
        
        super(x1,x2,x3);
        this.tx = tx ; 


     }
     public  TTriangle(Triangle t1,Texture tx )
     {
         
         super(t1.vertices.get(0),t1.vertices.get(1),t1.vertices.get(2));
         this.tx = tx ; 
 
     } 
    @Override
public Color getEmisson(GeoPoint x){
    return tx.getColor(x,super.getEmisson(x)); 
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