package Models;

import java.util.List;


import geometries.*;
import primitives.*;

public class hellicopter extends Geometries {

    private Color emission = new Color(0, 0, 100);
    private Material material = new Material().setkD(new Double3(0.5)).setkS(new Double3(0.5)).setnShininess(300);
    Point center ; 
    double size ;
    private static int  dimrx  = 10 , dimry = 4 ,dimrz = 5 ;  
    private hellicopter(Point center ,double size ,double angleFront,double angleBack){
        this.add(new Elepsoaide(center, size * 10 , size*4, size*5)
        .setEmisson(emission)
        .setMaterial(material));
    // Cylinder mainRoterHandle;
    this.add(new Cylinder(new Ray(center.add(new Vector(0, size * dimry, 0)), new Vector(0, 1, 0)),
                        size / 2,
                        size)
        .setEmisson(emission)
        .setMaterial(material));


    Vector normal = new Vector(0, size * 5 + size, 0).crossProduct(new Vector(size * 5, size * 5, 0)).normalize() ;

    // Polygon mainRotorWingX;
    this.add(createRectangleYRotate(center.add(new Vector(0, size * dimry + size, 0)), size * 20, size,angleFront,new Vector(0, 1, 0))
        .setEmisson(emission)
        .setMaterial(material));
    // Polygon mainRotorWingY;
    this.add(createRectangleYRotate(center.add(new Vector(0, size * dimry + size, 0)), size, size * 20,angleFront,new Vector(0, 1, 0))
        .setEmisson(emission)
        .setMaterial(material));
   
   
   
   
        // Triangle tail;
    this.add(new Triangle(center.add(new Vector(size * dimrx, size * dimry, 0).normalize().scale(size * 5)),
                        center.add(new Vector(size * dimrx, size * dimry, 0).normalize().scale(size * 5)).add(new Vector(size * 15, -size, 0)),
                        center.add(new Vector(size * dimrx, size *(-dimry *2 ), 0).normalize().scale(size * 5)))
        .setEmisson(emission)
        .setMaterial(material));
    // Cylinder tailRoterHandle;
    this.add(new Cylinder(new Ray(center.add(new Vector(size * dimrx, size * dimry, 0).normalize().scale(size * 5)).add(new Vector(size * 15, -size, 0)),
                                    new Vector(0, 0, 1)),
                        size / 2,
                        size)
        .setEmisson(emission)
        .setMaterial(material));
    // Polygon tailRotorWingX;
    this.add(createRectangleZRotate(center.add(new Vector(size * dimrx, size * dimry, 0).normalize().scale(size * 5)).add(new Vector(size * 15, -size, 0)).add(new Vector(0, 0, size)),
                            size * 5, size/2,angleBack,new Vector(0,0,1))
        .setEmisson(emission)
        .setMaterial(material));
    // Polygon tailRotorWingY;
    this.add(createRectangleZRotate(center.add(new Vector(size * dimrx, size * dimry, 0).normalize().scale(size * 5)).add(new Vector(size * 15, -size, 0)).add(new Vector(0, 0, size)),
                            size/2, size * 5,angleBack,new Vector(0,0,1))
        .setEmisson(emission)
        .setMaterial(material));


            // Polygon mainWings;
    this.add(createRectangleYRotate(center.add(new Vector(0, 0 ,size * dimrz + size)), size * 5, size * 80,angleFront,new Vector(0, 1, 0))
    .setEmisson(emission)
    .setMaterial(material));

    }
    public hellicopter(Point center, double size) {
        this.center = center ; 
        this.size = size ;
        // Sphere cocpit;
        this.add(new Sphere(center, size * 5)
            .setEmisson(emission)
            .setMaterial(material));
        // Cylinder mainRoterHandle;
        this.add(new Cylinder(new Ray(center.add(new Vector(0, size * 5, 0)), new Vector(0, 1, 0)),
                            size / 2,
                            size)
            .setEmisson(emission)
            .setMaterial(material));
        // Polygon mainRotorWingX;
        this.add(createRectangleY(center.add(new Vector(0, size * 5 + size, 0)), size * 20, size)
            .setEmisson(emission)
            .setMaterial(material));
        // Polygon mainRotorWingY;
        this.add(createRectangleY(center.add(new Vector(0, size * 5 + size, 0)), size, size * 20)
            .setEmisson(emission)
            .setMaterial(material));
        // Triangle tail;
        this.add(new Triangle(center.add(new Vector(size * 5, size * 5, 0).normalize().scale(size * 5)),
                            center.add(new Vector(size * 5, size * 5, 0).normalize().scale(size * 5)).add(new Vector(size * 15, -size, 0)),
                            center.add(new Vector(size * 5, size * -3, 0).normalize().scale(size * 5)))
            .setEmisson(emission)
            .setMaterial(material));
        // Cylinder tailRoterHandle;
        this.add(new Cylinder(new Ray(center.add(new Vector(size * 5, size * 5, 0).normalize().scale(size * 5)).add(new Vector(size * 15, -size, 0)),
                                        new Vector(0, 0, 1)),
                            size / 2,
                            size)
            .setEmisson(emission)
            .setMaterial(material));
        // Polygon tailRotorWingX;
        this.add(createRectangleZ(center.add(new Vector(size * 5, size * 5, 0).normalize().scale(size * 5)).add(new Vector(size * 15, -size, 0)).add(new Vector(0, 0, size)),
                                size * 5, size)
            .setEmisson(emission)
            .setMaterial(material));
        // Polygon tailRotorWingY;
        this.add(createRectangleZ(center.add(new Vector(size * 5, size * 5, 0).normalize().scale(size * 5)).add(new Vector(size * 15, -size, 0)).add(new Vector(0, 0, size)),
                                size, size * 5)
            .setEmisson(emission)
            .setMaterial(material));
    }

    Polygon createRectangleY(Point canter, double width, double height) {
        return new Polygon(new Point[] {
            canter.add(new Vector(width/2, 0, height/2)),
            canter.add(new Vector(width/2, 0, -height/2)),
            canter.add(new Vector(-width/2, 0, -height/2)),
            canter.add(new Vector(-width/2, 0, height/2)),
        });
    }
    Polygon createRectangleYRotate(Point canter, double width, double height,double angle ,Vector axsis) {
        return new Polygon(new Point[] {
            canter.add(new Vector(width/2, 0, height/2).Roatate(angle, axsis)),
            canter.add(new Vector(width/2, 0, -height/2).Roatate(angle, axsis)),
            canter.add(new Vector(-width/2, 0, -height/2).Roatate(angle, axsis)),
            canter.add(new Vector(-width/2, 0, height/2).Roatate(angle, axsis)),
        });
    }
    Polygon createRectangleZ(Point canter, double width, double height) {
        return new Polygon(new Point[] {
            canter.add(new Vector(width/2, height/2, 0)),
            canter.add(new Vector(width/2, -height/2, 0)),
            canter.add(new Vector(-width/2, -height/2, 0)),
            canter.add(new Vector(-width/2, height/2, 0)),
        });
    }
    Polygon createRectangleZRotate(Point canter, double width, double height,double angle , Vector axsis) {
        return new Polygon(new Point[] {
            canter.add(new Vector(width/2, height/2, 0).Roatate(angle, axsis)),
            canter.add(new Vector(width/2, -height/2, 0).Roatate(angle, axsis)),
            canter.add(new Vector(-width/2, -height/2, 0).Roatate(angle, axsis)),
            canter.add(new Vector(-width/2, height/2, 0).Roatate(angle, axsis)),
        });
    }
    public hellicopter rotateFrontRotor(double angleF,double angleB){
        return new hellicopter(center,size,angleF,angleB); 
    }

    




}
