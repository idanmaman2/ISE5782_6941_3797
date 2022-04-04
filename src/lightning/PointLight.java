package lightning;

import primitives.*;

public class PointLight  extends Light implements LightSource   {
   
    protected Point position ; 
    protected double kC,kL,kQ ;

    public Point getPosition() {
        return this.position;
    }

    public PointLight setPosition(Point position) {
        this.position = position;
        return this;
    }

    public double getKC() {
        return this.kC;
    }

    public PointLight setKC(double kC) {
        this.kC = kC;
        return this;
    }

    public double getKL() {
        return this.kL;
    }

    public PointLight setKL(double kL) {
        this.kL = kL;
        return this; 
    }

    public double getKQ() {
        return this.kQ;
    }

    public PointLight setKQ(double kQ) {
        this.kQ = kQ;
        return this;
    }
   
    @Override
    public Color getIntensity(Point p){
        double d2 = position.distanceSquared(p);
        double d = Math.sqrt(d2);
        return intensity.scale(1.0/ (kC + kL *d + kQ *d2 ));
        
    }

    public PointLight(Point position, Color intensity){
        this.intensity = intensity ;
        this.position = position;
        kC =0 ;
        kL = 0 ;
        kQ = 1 ;
    }

    public PointLight(Point position, double kC,double kL,double kQ , Color intensity){
        this.intensity = intensity;
        this.position = position;
        this.kC =kC ;
        this.kL = kL ;
        this.kQ = kQ ;
    }


    @Override
public Vector getL(Point p){
    return position.subtract(p).normalize();
}

}
