package renderer;

import primitives.*;

public class Camera {
    private Point p0 ; 
    private Vector vTo,vRight,vUp ;

    private double height,width,length ; 

    public Point getP0() {
        return this.p0;
    }

    public Vector getvTo() {
        return this.vTo;
    }


    public Vector getvRight() {
        return this.vRight;
    }



    public Vector getvUp() {
        return this.vUp;
    }


    public double getHeight() {
        return this.height;
    }



    public double getWidth() {
        return this.width;
    }


    public double getLength() {
        return this.length;
    }


    public Camera(Point p0, Vector vTo , Vector vUp) {
        this.p0 = p0;
        this.vUp = vUp.normalize();
        this.vTo = vTo.normalize();
        if(!Util.isZero(vTo.dotProduct(vUp))){
            throw  new IllegalArgumentException("the vectors are not in 90 degrees or pi/2 rad ");
        }
        this.vRight = vTo.crossProduct(vUp); // it is normalize cause |x| * |y| * sin(theta ) => theta = 90 sin(90) =1 ,  |x| =1 , |y| = 1 


    }

    public Camera setVPSize(double width , double height ){
        this.height = height ; 
        this.width = width ; 
        return this ; 

    }

    public Camera setVPDistance(double distance){
        this.length = distance ; 
        return this ; 
    }

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

    //TO DO -> CHNAGE DIR AND ANGLE OF CAMERA METHODS 



    

    
}


