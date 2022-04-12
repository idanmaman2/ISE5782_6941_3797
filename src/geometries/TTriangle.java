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
   public  TTriangle(Point x1, Point x2, Point x3,Texture tx)
    {
        
        super(x1,x2,x3);
        this.tx = tx ; 

    }
    @Override
public Color getEmisson(GeoPoint x){
    return tx.getColor(x,super.getEmisson(x)); 
}
    
  
public Texture.ImageCords TextureEmession(Point pt,int nX,int nY){
    return new Texture.ImageCords(0,0) ;
}
public Texture.ImageCords getDims(){
    return null ;
}
}