package renderer;

import java.net.Inet4Address;
import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;


import primitives.*;
import primitives.Texture.ImageCords;
/**
 *Camera
 *
 * @author Idan and Eliyahu
 */
public class Camera {
    private Point p0 ; 
    private Vector vTo,vRight,vUp ;
    private double height,width,length,focalLength = 0 ,focalSize =0 ; 
    private ImageWriter writer ; 
    private RayTracerBase rayTrace ;

  
    public Camera setFocalLength(double focalLength){
        this.focalLength = focalLength ; 
        return this; 
    }
    public Camera setFocalSize(double focalSize){
        this.focalSize = focalSize ; 
        return this ; 
    }


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



    public List<Ray> constructRays(int nX, int nY, int j, int i){
        List<Ray> crt = new LinkedList<>() ; 

        final int interval  =1 ; 
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
        double sj = (j-(nX)/2.0d)*Rx;
        double si = -(i-(nY)/2.0d)*Ry ;
        Point Start = Pc ; 
        if (sj != 0) {
           Start = Start.add(this.vRight.scale(sj));
        }
        if (si != 0){
            Start = Start.add(this.vUp.scale(si));
        } 
        for(double  ii=0 ;ii<=Rx ; ii=Util.alignZero(ii+Rx/interval*Math.max(0.5d,Math.random())) ){
            for(double jj=0 ; jj>=-Ry ; jj=Util.alignZero(jj-Ry/interval*Math.max(0.5d,Math.random()))){
                Point ele = Util.randomInEllpiseArea(Rx, Ry, Pij, vUp, vRight);
                Point grid = Start ; 
                if(ii != 0 ){
                    grid = grid.add(vRight.scale( ii )); 
                }
                if(jj!=0 ){
                    grid = grid.add(vUp.scale(jj )); 
                }
                crt.add(new Ray(p0,grid.middle(ele, 1, 1).subtract(p0)));


            }
        }
        return crt;
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
                List<Ray> rays  = constructRays(writer.getNx(), writer.getNy(), j, i);
                final int i1 = i ; 
                final int j1 = j ; 
                Color  color = rays.stream().map((element)->rayTrace.traceRay(element,i1,j1,writer.getNx(),writer.getNy())).reduce(Color.BLACK , (x,y)->x.add(y)).scale(1.0d/rays.size());
                // Color color = rayTrace.traceRay(this.constructRay(writer.getNx(), writer.getNy(), j, i),i,j,writer.getNx(),writer.getNy());
                if(color !=null){
                    writer.writePixel(j, i, color);
                }
                
            }
        } 
        return this;
    }




    public Camera depthRenderImage() {






        if(this.rayTrace == null || 
        this.writer == null || 
        this.height == 0 || this.width == 0 || this.length == 0 || 
        this.vTo == null || this.vUp == null || this.p0 == null){
            throw new MissingResourceException("enter all the values","Camera","i am not your slave");
        }
        int sum = 0 ; 
        for(int i=0 ; i < writer.getNx() ; i++ ){
            for(int j=0 ; j< writer.getNy() ; j++ ){
                List<Ray> rays = constructRays(writer.getNx(), writer.getNy(), j, i);
                List <Ray> raysNew =new  LinkedList<>();
                for(Ray ray :rays){
                    Point P = p0.add(ray.getDir().scale(focalLength));
                    Vector  randomFactor = new Vector(Util.random(-0.5, 0.5),Util.random(-0.5, 0.5),Util.random(-0.5, 0.5)).scale(focalSize);  
                    Point rO = ray.getP0().add(randomFactor);
                    ray  = new Ray(rO, P.subtract(rO));
                    raysNew.add(ray);
                }
                final int i1 = i ; 
                final int j1 = j ; 
                Color  color = raysNew.stream().map((element)->rayTrace.traceRay(element,i1,j1,writer.getNx(),writer.getNy())).reduce(Color.BLACK , (x,y)->x.add(y)).scale(1.0d/rays.size());
                //Color color = rayTrace.traceRay(ray,i,j,writer.getNx(),writer.getNy());
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
    public void writeToImage(String string, ImageCords imageCords) {
        writer.writeToImage(string,imageCords);
    }

    //TO DO -> CHNAGE DIR AND ANGLE OF CAMERA METHODS 

  

    

    
}