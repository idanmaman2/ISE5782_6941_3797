package lightning;

import primitives.*;
/**
 *PointLight
 *
 * @author Idan and Eliyahu
 */

/**
 *PointLight
 */
public class PointLight  extends Light implements LightSource   {
   
    protected Point position ; 
    protected double kC,kL,kQ ;
    /**
     *GetPosition
     */
    public Point getPosition() {
        return this.position;
    }
    /**
     *SetPosition
     */
    public PointLight setPosition(Point position) {
        this.position = position;
        return this;
    }
    /**
     *GetKC
     */
    public double getKC() {
        return this.kC;
    }
    /**
     *SetKC
     */
    public PointLight setKC(double kC) {
        this.kC = kC;
        return this;
    }
    /**
     *GetKL
     */
    public double getKL() {
        return this.kL;
    }
    /**
     *SetKL
     */
    public PointLight setKL(double kL) {
        this.kL = kL;
        return this; 
    }
    /**
     *GetKQ
     */
    public double getKQ() {
        return this.kQ;
    }
    /**
     *SetKQ
     */
    public PointLight setKQ(double kQ) {
        this.kQ = kQ;
        return this;
    }
    /**
     *How strong is the light, getIntensity
     */
    @Override
    public Color getIntensity(Point p){
        double d2 = position.distanceSquared(p);
        double d = Math.sqrt(d2);
        return intensity.scale(1.0d/ (kC + kL *d + kQ *d2 ));
        
    }
    /**
     *Direction of the light, PointLight
     */
    public PointLight(Point position, Color intensity){
        this.intensity = intensity ;
        this.position = position;
        kC =1 ;
        kL = 0 ;
        kQ = 0 ;
    }
    /**
     *Direction of the light, PointLight
     */
    public PointLight(Point position, double kC,double kL,double kQ , Color intensity){
        this.intensity = intensity;
        this.position = position;
        this.kC =kC ;
        this.kL = kL ;
        this.kQ = kQ ;
    }


    @Override
public Vector getL(Point p){
    return p.subtract(position).normalize();
}
public double distanceSquared(Point p){
    return position.distanceSquared(p);
}

}