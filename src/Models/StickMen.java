package Models;

import java.util.List;


import geometries.*;
import primitives.*;

public class StickMen extends Geometries {

    public  StickMen(Point center ,double size){

        double raiudsCylinder = size / 2 ; 
        double radiusSphere = size ; 



        Cylinder mainBody = new Cylinder(new Ray (center.add(new Vector(0,radiusSphere,0)),new Vector(0,1,0)) , raiudsCylinder*3, size * 18 );
        Elepsoaide face = new Elepsoaide(center.add(new Vector(0, size * 18 +radiusSphere*5 ,0  )), size*2 , size * 5 , size *3);
        Sphere shoulderLeft = new Sphere( center.add(new Vector(3*raiudsCylinder, size * 18  ,0  )), radiusSphere);
        Sphere shoulderRight = new Sphere( center.add(new Vector(-3*raiudsCylinder, size * 18  ,0  )), radiusSphere);
        Cylinder leftHand = new Cylinder(new Ray (center.add(new Vector(3*raiudsCylinder +radiusSphere  ,size * 18 ,0)),new Vector(0,-1,0)) , raiudsCylinder, size * 15);
        Cylinder rightHand = new Cylinder(new Ray (center.add(new Vector(-3*raiudsCylinder -radiusSphere ,size * 18 ,0)),new Vector(0,-1,0)) , raiudsCylinder, size * 15 );
        add(mainBody,face,shoulderLeft,shoulderRight,leftHand,rightHand);
        
        mainBody.setEmisson(Color.getRandomEmission());
        face.setEmisson(Color.getRandomEmission());
        shoulderLeft.setEmisson(Color.getRandomEmission());
        shoulderRight.setEmisson(Color.getRandomEmission());
        leftHand.setEmisson(Color.getRandomEmission());
        rightHand.setEmisson(Color.getRandomEmission());




        Sphere centerAxis = new Sphere(center, raiudsCylinder*3);
        Cylinder rightHip = new Cylinder(new Ray(center, new Vector(0, -2, -1)), raiudsCylinder, size*10);
        Cylinder leftHip = new Cylinder(new Ray(center, new Vector(0, -2, 1)), raiudsCylinder, size*10);
        Sphere rightLegAxis = new Sphere(center.add(new Vector(0, -2, -1).normalize().scale(rightHip.getHeight())), radiusSphere);
        Sphere leftLegAxis = new Sphere(center.add(new Vector(0, -2, 1).normalize().scale(leftHip.getHeight())), radiusSphere);
        Cylinder rightLeg = new Cylinder(new Ray(rightLegAxis.getCenter(), new Vector(0, -1, 0)), raiudsCylinder, size*10);
        Cylinder leftLeg = new Cylinder(new Ray(leftLegAxis.getCenter(), new Vector(0, -1, 0)), raiudsCylinder, size*10);
        add(centerAxis, rightHip, leftHip, rightLegAxis, leftLegAxis, rightLeg, leftLeg);



        centerAxis.setEmisson(Color.getRandomEmission());
        rightHip.setEmisson(Color.getRandomEmission());
        leftHip.setEmisson(Color.getRandomEmission());
        rightLegAxis.setEmisson(Color.getRandomEmission());
        leftLegAxis.setEmisson(Color.getRandomEmission());
        rightLeg.setEmisson(Color.getRandomEmission());
        leftLeg.setEmisson(Color.getRandomEmission());
    }







}
