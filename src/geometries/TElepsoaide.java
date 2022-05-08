
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
public class TElepsoaide extends Elepsoaide implements Textureable{
   
    Texture tx ; 


    public TElepsoaide(Point center, double radius1 , double radius2 ,double radius3 ,Texture tx  ) {//simple constructor
       super(center , radius1,radius1,radius3);
       this.tx=tx ;
    }
    
    @Override
    public Color getEmisson(GeoPoint x){
        return tx.getColor(x,super.getEmisson(x)); 
    }


    private class ElpticlCords { //psd 

       public  double phi ; 
        public double theta ;
        private void normalize(){

            this.phi = phi % Math.PI; 
            this.theta = theta%(Math.PI /2);
        
        }

         ElpticlCords(double pi , double theta){
            this.phi=phi ; 
            this.theta = theta ;
            normalize();
        }
        public ElpticlCords(Point pt ){
            /**
             * in ball point 
             * x=r cos theta * sin pi 
             * y = r sin theta * sin pi 
             * z= r cos pi 
             * x^2 + y^2 + z^2 = r^2 
             * 
             * y/x = r/r * tan(theta) * sin pi / sin pi = tan(theat) => arctan(x/y)=theat
             * 
             * 
             * φ(0)=arctan[z(1−e2)p] with p=x2+y2‾‾‾‾‾‾‾√.
             * 
             * 
             * 
             */
            this.theta = Math.atan(pt.getY()/pt.getX()); 
            this.phi = Math.atan(pt.getZ() / ( ( 1-Math.E * Math.E ) * Math.sqrt(pt.getX()*pt.getX() + pt.getY() * pt.getY()) ) );
            normalize();
        }


    }



    public Texture.ImageCords TextureEmessionTest(Point pt,int nX,int nY){
        //(λ, φ)7−→ (u=W2πλ, v =Hπφ).
        ElpticlCords el = new ElpticlCords(pt); 
        return new Texture.ImageCords( (int) (nX/(Math.PI * 2) * el.phi),(int)(nY/Math.PI * el.theta) );

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