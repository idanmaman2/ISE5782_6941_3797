package lightning;

import primitives.*;

public class SpotLight extends PointLight {

    Vector  direction;
    public SpotLight(Vector direction , Point position ,Color intensity){
        super(position,intensity);
        this.direction = direction;
    }

    public SpotLight(Vector direction , Point position, double kC,double kL,double kQ,Color intensity){
        super(position, kC, kL, kQ,intensity);
        this.direction = direction;
    }

    @Override
    public Color getIntensity(Point p){
        double d2 = position.distanceSquared(p);
        double d = Math.sqrt(d2);
        return intensity.scale(Math.max(0,direction.dotProduct(getL(p)))).scale(1.0/ (kC + kL *d + kQ *d2));
        
    }

}
