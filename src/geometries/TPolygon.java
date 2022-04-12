package geometries;

import java.util.Arrays;
import java.util.List;
import primitives.*;

/**
 *Triangle
 *
 * @author Idan and Eliyahu
 */
public class TPolygon extends Polygon implements Textureable {
    Texture tx ;
   public  TPolygon(Texture tx , Point... vertices )
    {
        super(vertices);
        this.tx = tx ; 
    }
    @Override
public Color getEmisson(GeoPoint x){
    return tx.getColor(x,super.getEmisson(x)); 
}
    
public Texture.ImageCords TextureEmession(Point pt,int nX,int nY){
    try{
        Vector normal =new Vector(0,1,0);
        Vector v =pt.subtract(plane.q0);
        Vector pro =v.projection(normal);
        if( pro == null || v.equals(pro)) {
            return new Texture.ImageCords((int)v.getX(),(int)v.getZ()) ;
        }
        Vector po = v.subtract(v.projection(normal));
        Double3 x2 = po.xyz.subtract(plane.q0.xyz);
        //if((Math.abs((int)x2.d1)/nX) %2 == 0 ){
          // v =pt.point.subtract(pn.q0).Mirror(hor);
            //po = v.subtract(v.projection(normal));
            //x2 = po.xyz.subtract(pn.q0.xyz);
        //}
        int  x =Math.abs((int)x2.d1); 
        int y =Math.abs((int)x2.d3); 
return new Texture.ImageCords(x,y);


}
catch(Exception e){
    return new Texture.ImageCords(0, 0);
}
}

public Texture.ImageCords getDims(){
    return null ;
}
}