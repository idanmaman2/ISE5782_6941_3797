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
    
public ImageCords imaingnaion(Point pt , List<ImageCords> cordsOfVT ,int nX , int nY){
    // get the point to start in zero 
    // get the points to be in the first quarter 
    // straight scale 
    // dedo all what we did 
    // return the image cords 
    
    List<Point> ptLst = cordsOfVT.stream().map((ele)-> new Point(ele.getX() * nX , ele.getY() *nY,0)).toList();
    TrilinearPoint thisTri = new TrilinearPoint( pt , this);
    Point ptInter =  thisTri.changeTriangle(new Triangle(ptLst.get(0),ptLst.get(1),ptLst.get(2))).toPoint();
    return new ImageCords(ptInter.getX(), ptInter.getY()) ; 

}
 @Override 
public Texture.ImageCords TextureEmession(Point pt,int nX,int nY){
    if(speCords == null ){
    return TexturePlane.TextureEmession(pt, nX, nY);
    }
   try{
    return  imaingnaion(pt,speCords,nX,nY);
   } 
   catch(Exception e){
       return new ImageCords(0,0);
   }
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