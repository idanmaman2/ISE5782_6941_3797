package renderer;

import java.util.MissingResourceException;


import primitives.*;
/**
 *Camera
 *
 * @author Idan and Eliyahu
 */
public class Camera {
    private Point p0 ; 
    private Vector vTo,vRight,vUp ;
    private double height,width,length ; 
    private ImageWriter writer ; 
    private RayTracerBase rayTrace ;
    /**
     *SetWriter
     */
    public Camera setWriter(ImageWriter writer) {
        this.writer = writer;
        return this;
    }
    /**
     *The angle we want to set
     */
    public Camera setAngle(double angle, Vector k) {
       
       vTo = vTo.Roatate(angle, k).normalize();
       vUp = vUp.Roatate(angle, k).normalize();
       vRight = vTo.crossProduct(vUp);
        return this;
    }
    /**
     *Set the Ray Trace
     */
    public Camera setRayTrace(RayTracerBase rayTrace) {
        this.rayTrace = rayTrace;
        return this;
    }

    /**
     *Set the Ray Trace
     */
    public Point getP0() {
        return this.p0;
    }
    /**
     *getvector To
     */
    public Vector getvTo() {
        return this.vTo;
    }

    /**
     *getvector right
     */
    public Vector getvRight() {
        return this.vRight;
    }


    /**
     *getvector top
     */
    public Vector getvUp() {
        return this.vUp;
    }

    /**
     *getvector height
     */
    public double getHeight() {
        return this.height;
    }


    /**
     *get the width
     */
    public double getWidth() {
        return this.width;
    }

    /**
     *get the length
     */
    public double getLength() {
        return this.length;
    }

    /**
     *camera position
     */
    public Camera(Point p0, Vector vTo , Vector vUp) {
        this.p0 = p0;
        this.vUp = vUp.normalize();
        this.vTo = vTo.normalize();
        if(!Util.isZero(vTo.dotProduct(vUp))){
            throw  new IllegalArgumentException("the vectors are not in 90 degrees or pi/2 rad ");
        }
        this.vRight = vTo.crossProduct(vUp); // it is normalize cause |x| * |y| * sin(theta ) => theta = 90 sin(90) =1 ,  |x| =1 , |y| = 1 


    }
    /**
     *Set the size of the VP
     */
    public Camera setVPSize(double width , double height ){
        this.height = height ; 
        this.width = width ; 
        return this ; 

    }
    /**
     *Set the Distance of the VP
     */
    public Camera setVPDistance(double distance){
        this.length = distance ; 
        return this ; 
    }
    /**
     *Build to construct the Ray
     */
    public Ray constructRay(int nX, int nY, int j, int i){
       Point Pc  = this.p0 .add(this.vTo.scale(this.length));
       double  Rx = this.width / (double)nX ;
       double Ry = this.height / (double)nY ; 
       double xj = (j-(nX-1)/2.0d)*Rx ; 
       double yi = -(i-(nY-1)/2.0d)*Ry ; 
       Point Pij = Pc;
        if (xj != 0) {
            Pij = Pij.add(this.vRight.scale(xj));
        }
        if (yi != 0){
            Pij = Pij.add(this.vUp.scale(yi));
        } 
       return new Ray(this.p0 , Pij.subtract(this.p0));
    }
    /**
     *Camera Renderer Image
     */
    public Camera renderImage() {
        if(this.rayTrace == null || 
        this.writer == null || 
        this.height == 0 || this.width == 0 || this.length == 0 || 
        this.vTo == null || this.vUp == null || this.p0 == null){
            throw new MissingResourceException("enter all the values","Camera","i am not your slave");
        }
        int sum = 0 ; 
        for(int i=0 ; i < writer.getNx() ; i++ ){
            for(int j=0 ; j< writer.getNy() ; j++ ){
                Color color = rayTrace.traceRay(this.constructRay(writer.getNx(), writer.getNy(), j, i));
                if(color !=null){
                    writer.writePixel(j, i, color);
                }
                
            }
        } 
        return this;
    }
    /**
     *Grid for the light, to know where it was hit
     */
    public void printGrid(int interval, Color color) {
        double  Rx =  (double)writer.getNx()/this.width;
        double Ry =  (double)writer.getNy()/this.height ; 
        for (int i = 0; i < writer.getNy(); i++) {
            for (int j = 0; j < writer.getNx(); j++) {
                if (j % interval ==0  || i % interval == 0)
                    writer.writePixel(j, i, color);
            }
        }
    }
    /**
     *Simple write to Image
     */
    public void writeToImage() {
        writer.writeToImage();
    }

    //TO DO -> CHNAGE DIR AND ANGLE OF CAMERA METHODS 

 

    

    
}
