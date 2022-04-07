package lightning;

import primitives.*;

public class DirectionalLight extends Light implements LightSource {
    private Vector direction;

   public  DirectionalLight(Color Intensity,Vector Direction){
       this.direction = Direction ; 
       this.intensity = Intensity;
   }

    public Color getIntensity(Point p){
        return intensity;

    }
    public Vector getL(Point p){
        return direction.normalize() ;
    }

    
}

