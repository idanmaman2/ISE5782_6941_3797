package geometries;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


import primitives.*;

/**
 *Spgere
 *
 * @author Idan and Eliyahu
 */
public class TSphere extends Sphere implements Textureable{
    Point center;
    double radius;
    Texture tx ; 
    public TSphere(Point center, double radius ,Texture tx ) {//simple constructor
        super(center,radius);
        this.tx =tx ; 

    }
    @Override
    public Color getEmisson(GeoPoint x){
        return tx.getColor(x,super.getEmisson(x)); 
    }

    public Texture.ImageCords TextureEmession(Point pt,int nX,int nY){
        //(𝑦−𝑥cos𝜃sin𝜙,𝑧+𝑥sin𝜃sin𝜙
        double O = Math.acos((new Vector(1,0,0)).dotProduct(this.getNormal(pt))) ,
          Q = Math.acos((new Vector(0,1,0)).dotProduct(this.getNormal(pt))) , sinq = Math.sin(Q) ;
        return new Texture.ImageCords((int) ( pt.getY() - pt.getX() *Math.cos(O)*sinq ),
        (int)(pt.getZ() + pt.getX() *Math.sin(O) * sinq) );
    }
public Texture.ImageCords getDims(){
    return null ;
}
}