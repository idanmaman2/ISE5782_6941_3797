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
            Vector normal =new Vector(0,1,0);
            Vector v =pt.point.subtract(pn.q0);
            Vector po = v.add(v.projection(normal).scale(-1));
            Double3 x2 = po.xyz.subtract(pn.q0.xyz);
            int  x =Math.abs((int)x2.d1); 
            int y =Math.abs((int)x2.d3); 
            int color = image.getRGB(x % nX ,  y% nY ); 
            Color xc = new Color(new java.awt.Color(color));
            return xc ;  

        }
        if(pt.geometry instanceof TSphere){

    Sphere sp = ((Sphere)pt.geometry);
    Point.LongLat lt =pt.point.ToLonLat(sp.radius);
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
    
    
}

