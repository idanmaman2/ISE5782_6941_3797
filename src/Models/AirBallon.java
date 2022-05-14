package Models;

import java.util.List;

import geometries.Cylinder;
import geometries.Elepsoaide;
import geometries.Geometries;
import geometries.Geometry;
import geometries.Polygon;
import geometries.Sphere;
import primitives.Color;
import primitives.Double3;
import primitives.Material;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
/**
 *Air Balloon, nice graphical design done by amazing artists
 *
 * @author Idan and Eliyahu
 */
public class AirBallon extends Geometries  {
    Point center ; 
    double ballonSize ; 
    Color red = new Color(255,0,0);
    public AirBallon(Point center  , double ballonSize,Color cBallon  , Color cRope , Color cBacket,Color cTanks ){
        this.center = center ; 
        this.ballonSize = ballonSize ; 
        Geometry ballon = new Elepsoaide(center, ballonSize,ballonSize * 1.5 ,ballonSize).setEmisson(cBallon).setMaterial(new Material().setkD(new Double3(0.5)).setkS(new Double3(0.8)).setnShininess(1300)); 
        Point downCenter = center.add(new Vector(0,-ballonSize * 3 ,0));
        for (int i = 0; i < 360; i += 20) {
            Point stringPoint = center.add(new Vector(ballonSize * 0.95,0,0).Roatate(i, new Vector(0,1,0)));
            Ray stringRay = new Ray(stringPoint , downCenter.subtract(stringPoint) );
            this.add(new Cylinder(stringRay, ballonSize / 100 , 3 * ballonSize * 3/4  ).setEmisson(cRope).setMaterial(new Material().setkD(new Double3(0.5)).setkS(new Double3(0.5)).setnShininess(300)));
        }
        this.add(ballon);
        /**
        * z is for front  x is for side y is for up
        *
        */
        Point st1 = center.add(new Vector(ballonSize ,0,0)) ; 
        Point st2  = center.add(new Vector(-ballonSize ,0,0)) ; 
        Point st3 = center.add(new Vector(0,0,ballonSize  ))  ; 
        Point st4 = center.add(new Vector(0,0,-ballonSize ))  ; 
        Point topbackLeft = downCenter.middle(st2, 2.1, 1) ; 
        Point topbackRight = downCenter.middle(st1, 2.1, 1) ; 
        Point topfrontLeft = downCenter.middle(st4, 2.1, 1); 
        Point topfrontRight = downCenter.middle(st3, 2.1, 1); 
        Vector down = new Vector(0,-ballonSize/2,0);
        Point botbackLeft = topbackLeft.add(down);
        Point botbackRight = topbackRight.add(down) ; 
        Point botfrontLeft = topfrontLeft.add(down); 
        Point botfrontRight =topfrontRight.add(down) ; 
        Geometry [] backet = {

            new Polygon(topbackLeft,topfrontLeft,botfrontLeft,botbackLeft).setEmisson(cBacket).setMaterial(new Material().setkD(new Double3(0.5)).setkS(new Double3(0.5)).setnShininess(300)),
            new Polygon(topbackRight,topfrontRight,botfrontRight,botbackRight).setEmisson(cBacket).setMaterial(new Material().setkD(new Double3(0.5)).setkS(new Double3(0.5)).setnShininess(300)),
            new Polygon(botfrontRight,botbackLeft,botfrontLeft,botbackRight).setEmisson(cBacket).setMaterial(new Material().setkD(new Double3(0.5)).setkS(new Double3(0.5)).setnShininess(300)),
            new Polygon(botbackLeft,botbackRight,topbackRight,topbackLeft).setEmisson(cBacket).setMaterial(new Material().setkD(new Double3(0.5)).setkS(new Double3(0.5)).setnShininess(300)),
            new Polygon(botfrontLeft,botfrontRight,topfrontRight,topfrontLeft).setEmisson(cBacket).setMaterial(new Material().setkD(new Double3(0.5)).setkS(new Double3(0.5)).setnShininess(300)) ,
            new Cylinder(new Ray(topbackLeft.middle(downCenter, 13, 1), topbackRight.subtract(topbackLeft)),ballonSize/15 , topbackLeft.distance(topbackRight)).setEmisson(cTanks).setMaterial(new Material().setkD(new Double3(0.5)).setkS(new Double3(0.5)).setnShininess(300)),
            new Cylinder(new Ray(topfrontLeft.middle(downCenter, 13, 1), topfrontRight.subtract(topfrontLeft)),ballonSize/15 , topfrontLeft.distance(topfrontRight)).setEmisson(cTanks).setMaterial(new Material().setkD(new Double3(0.5)).setkS(new Double3(0.5)).setnShininess(300)),
            new Cylinder(new Ray(topbackLeft.middle(downCenter, 13, 1), topfrontLeft.subtract(topbackLeft)),ballonSize/15, topfrontLeft.distance(topbackLeft)).setEmisson(cTanks).setMaterial(new Material().setkD(new Double3(0.5)).setkS(new Double3(0.5)).setnShininess(300)),   
            new  Cylinder(new Ray(topfrontRight.middle(downCenter, 13, 1), topbackRight.subtract(topfrontRight)),ballonSize/15 , topfrontRight.distance(topbackRight)).setEmisson(cTanks).setMaterial(new Material().setkD(new Double3(0.5)).setkS(new Double3(0.5)).setnShininess(300))
        } ;  
        
        



        this.add(backet);
    }
    
}
