package primitives;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.*;
import javax.print.DocFlavor.URL;

import geometries.Plane;
import geometries.Polygon;
import geometries.Sphere;
import geometries.TPlane;
import geometries.TPolygon;
import geometries.TSphere;
import geometries.TTriangle;
import geometries.Triangle;
import geometries.Textureable;
import geometries.Intersectable.GeoPoint;
public class Texture {
    private int nX = 617 ;
	private int nY = 617 ;

	private static final String FOLDER_PATH = "/Users/idang/Documents/ISE5782_6941_3797" + "/imagesT";
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

    public Color getColorReapeat(ImageCords cord){
        int color = image.getRGB((int)cord.x %nX   ,  (int)cord.y % nY ); 
    Color xc = new Color(new java.awt.Color(color));
    return xc ;  
    }
    public Color  getColorFill(ImageCords cord,  double px , double py){
        int color = image.getRGB((int)((cord.x * nX / px ) %nX ), (int)( (cord.y * nY / py)%nY )); 
    Color xc = new Color(new java.awt.Color(color));
    return xc ;  
    }

    public Color getColor(GeoPoint pt,Color base){
        ImageCords dims =((Textureable)pt.geometry).getDims() ;
      if(dims == null){
        return getColorReapeat(((Textureable)pt.geometry).TextureEmession(pt.point,nX,nY));
      }
      else{
        return getColorFill(((Textureable)pt.geometry).TextureEmession(pt.point,nX,nY),dims.x,dims.y);
      } 
       

    }

   static public class ImageCords{
       double x; 
       double y ;
       public ImageCords(double x , double y){
           this.x  =Math.abs(x) ;
            this.y =Math.abs(y) ;
       }
   }
    
    
}

