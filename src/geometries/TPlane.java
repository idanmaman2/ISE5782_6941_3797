package geometries;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import primitives.*;

/**
 *Plane
 *
 * @author Idan and Eliyahu
 */
public class TPlane extends Plane implements Textureable{

    Texture tx ; 

    public TPlane(Point q1 , Point q2 , Point q3 , Texture x  ){ //constructor
        super(q1,q2,q3);
this.tx = x ;
    }
    

    public TPlane(Point q0, Vector normal,Texture x) { //simple constructor
        super(q0,normal);
        this.tx = x ;
    }
@Override
public Color getEmisson(GeoPoint x){
    return tx.getColor(x,super.getEmisson(x)); 
}

public Texture.ImageCords TextureEmession(Point pt,int nX,int nY){
 try{
     
            Vector normal =new Vector(0,1,0);
            if(getNormal().dotProduct(normal) == 0 ){
                normal = new Vector(0,0,1);
            }
            Vector v =pt.subtract(q0);
            Vector pro =v.projection(normal);
            if( pro == null || v.equals(pro)) {
                return new Texture.ImageCords((int)v.getX(),(int)v.getZ()) ;
            }
            Vector po = v.subtract(v.projection(normal));
            Double3 x2 = po.xyz.subtract(q0.xyz);
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
    return null;
}
    }

