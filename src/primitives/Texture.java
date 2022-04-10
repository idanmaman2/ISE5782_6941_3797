package primitives;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.*;
import javax.print.DocFlavor.URL;

import geometries.Plane;
import geometries.Sphere;
import geometries.TPlane;
import geometries.TSphere;
import geometries.TTriangle;
import geometries.Triangle;
import geometries.Intersectable.GeoPoint;
public class Texture {
    private int nX = 617 ;
	private int nY = 617 ;

	private static final String FOLDER_PATH = System.getProperty("user.dir") + "/imagesT";
	private BufferedImage image;
	private Logger logger = Logger.getLogger("ImageWriter");


    public Texture(String name){

       try {
            image = ImageIO.read(new File(FOLDER_PATH+'/'+name));
            nX = image.getWidth() ; 
            nY = image.getHeight() ;
        } catch (IOException e) {
            throw new IllegalAccessError("hjsadas");
        }

    }


    public Color getColor(int y , int x , int px , int py){
        int color = image.getRGB(x * nX / px   ,  y * nY / py ); 
    Color xc = new Color(new java.awt.Color(color));
    return xc ;  
    }
    public Color getColor(GeoPoint pt,Color base){
        try {
        if(pt.geometry instanceof TTriangle || pt.geometry instanceof TPlane){
            Plane pn =null ;
            if(pt.geometry instanceof TTriangle){
                 pn = ((TTriangle)pt.geometry).plane;
            }
            if(pt.geometry instanceof TPlane){
                pn = (Plane)pt.geometry;
            }
            Vector hor = new Point(nX, 0 ,0).subtract(new Point(0, 0 ,0));
            Vector ver = new Point(0 , 0 ,nY).subtract(new Point(0 , 0 ,0));
            Vector normal =new Vector(0,1,0);
            hor = hor.subtract(hor.projection(pn.getNormal())).normalize(); 
            ver = ver.subtract(ver.projection(pn.getNormal())).normalize(); 
            Vector v =pt.point.subtract(pn.q0);
            Vector po = v.subtract(v.projection(normal));
            Double3 x2 = po.xyz.subtract(pn.q0.xyz);
            //if((Math.abs((int)x2.d1)/nX) %2 == 0 ){
              // v =pt.point.subtract(pn.q0).Mirror(hor);
                //po = v.subtract(v.projection(normal));
                //x2 = po.xyz.subtract(pn.q0.xyz);
            //}
            if((Math.abs((int)x2.d3)/nY) %2 == 1 ){
                v =pt.point.subtract(pn.q0).Mirror(ver);
                v = v.add(new Vector(nX/0.510 ,0,0));
                po = v.subtract(v.projection(normal));
                x2 = po.xyz.subtract(pn.q0.xyz);
               
            }
            int  x =Math.abs((int)x2.d1); 
            int y =Math.abs((int)x2.d3); 
            int color = image.getRGB(x % nX ,  y% nY ); 
            Color xc = new Color(new java.awt.Color(color));
            return xc ;  

        }
        if(pt.geometry instanceof TSphere){

    Sphere sp = ((Sphere)pt.geometry);
    Point.LongLat lt =pt.point.ToLonLat(sp.radius );
     double longitude = lt.lon ; 
     double latitude = lt.lat; 
    double PI = Math.PI; 
    // get x value
    int x = (int)(longitude+180)*(nX/360); 

    // convert from degrees to radians
    double latRad = latitude*PI/180;

    // get y value
    double  mercN = Math.log(Math.tan((PI/4)+(latRad/2)));
    int y = (int)( (nY/2)-(nX*mercN/(2*PI)));
    int color = image.getRGB(x % nX ,  y% nY ); 
    Color xc = new Color(new java.awt.Color(color));
    return xc ;  
        } 
    } 
    catch(Exception x ){
       
    }
   return base ;
       
    }


    class ImageCord{
        double  x ; 
        double  y; 
        public ImageCord(double  x , double y){
            this.x = x; 
            this.y =y ;
        }
        public ImageCord subtract(ImageCord n){
            return new ImageCord(x - n.x , y - n.x);

        }
        public ImageCord add(ImageCord n){
            return new ImageCord(x + n.x , y + n.x);

        }
        public double cross2d(ImageCord n){
            return x*n.y - y*n.x;
        }

    }

    ImageCord bilinearMapping (  ImageCord p,  ImageCord a,  ImageCord b , ImageCord c, ImageCord d ){
      
        {
            ImageCord res = new ImageCord(-1.0,-1.0);
        
            ImageCord e = b.subtract(a);
            ImageCord f = d.subtract(a);
            ImageCord g = d.subtract(b).add(c.subtract(d));
            ImageCord h = p.subtract(a);
                
            double k2 = g.cross2d(f );
            double k1 = e.cross2d( f ) + h.cross2d( g );
            double k0 = h.cross2d(e );
            
            // if edges are parallel, this is a linear equation
            if( Math.abs(k2)<0.001 )
            {
                res = new ImageCord( (h.x*k1+f.x*k0)/(e.x*k1-g.x*k0), -k0/k1 );
            }
            // otherwise, it's a quadratic
            else
            {
                double w = k1*k1 - 4.0*k0*k2;
                if( w<0.0 ) return new ImageCord(-1.0,-1.0);
                w = Math.sqrt( w );
        
                double ik2 = 0.5/k2;
                double v = (-k1 - w)*ik2;
                double u = (h.x - f.x*v)/(e.x + g.x*v);
                
                if( u<0.0 || u>1.0 || v<0.0 || v>1.0 )
                {
                   v = (-k1 + w)*ik2;
                   u = (h.x - f.x*v)/(e.x + g.x*v);
                }
                res = new ImageCord( u, v );
            }
            
            return res;
        }
        

    }

    
    
}

