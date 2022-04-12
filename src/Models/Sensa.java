package Models;



import geometries.Cylinder;
import geometries.Elepsoaide;
import geometries.Geometries;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Color;
import primitives.Double3;
import primitives.Material;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Sensa extends Geometries {
    Point center ; 
    double size  ; 
    public Sensa(Point center ,double size){
        this.size =size ;
        this.center =center;
        Point middle = center.add(new Vector(-1,0,0).scale(size/2 + size/8));
        Point botmiddle =   middle
        .add(new Vector(0,-1,0).scale(size/14));
        Point end  = center.add(new Vector(-1,0,0).scale(size));
        Point endOfNoise = end.add(new Vector(-1,0,0).scale(size/1.5));
        this.add(new Cylinder(new Ray(center,new Vector(-1,0,0) ),size/7 , size).setMaterial(new Material().setkD(new Double3(0.5)).setkS(new Double3(0.5)).setnShininess(300)));
        
        
        
         this.add(new Elepsoaide(end,size/1.5, size/(7*1.01),size/7).setEmisson(new Color(255,0,0)).setMaterial(new Material().setkD(new Double3(0.5)).setkS(new Double3(0.5)).setnShininess(300)));
        this.add(createRectangleX(endOfNoise,size*0.8*0.45, size/50).setEmisson(new Color(255,0,0)));
        this.add(createRectangleX(endOfNoise,size/50, size*0.8*0.45).setEmisson(new Color(255,0,0)));
    
    Point start  =center.add(new Vector(0,1,0).scale(size/7));

      this.add(new Triangle(start,start.add(new Vector(0,1,0).scale(size/5)) ,start.add(new Vector(-1,0,0).scale(size/5))).setEmisson(new Color(255,0,0)).setMaterial(new Material().setkD(new Double3(0.5)).setkS(new Double3(0.5)).setnShininess(300)));
      this.add(new Triangle(center, center.add(new Vector(0,0,1).scale(size*7)),
       end).setEmisson(new Color(255,0,0)).setMaterial(new Material().setkD(new Double3(0.5)).setkS(new Double3(0.5)).setnShininess(300)));
       this.add(new Triangle(center, center.add(new Vector(0,0,-1).scale(size*7)),
       end).setEmisson(new Color(255,0,0)).setMaterial(new Material().setkD(new Double3(0.5)).setkS(new Double3(0.5)).setnShininess(300)));
    }
    private Sensa(Point center ,double size , double angle ){
        this.center = center ; 
        this.size =size ;

        Point middle = center.add(new Vector(-1,0,0).scale(size/2 + size/8));
        Point botmiddle =   middle
        .add(new Vector(0,-1,0).scale(size/14));
        Point end  = center.add(new Vector(-1,0,0).scale(size));
        Point endOfNoise = end.add(new Vector(-1,0,0).scale(size/1.5));
        this.add(new Cylinder(new Ray(center,new Vector(-1,0,0) ),size/7 , size).setMaterial(new Material().setkD(new Double3(0.5)).setkS(new Double3(0.5)).setnShininess(300)));
        
        
        
         this.add(new Elepsoaide(end,size/1.5, size/(7*1.01),size/7).setEmisson(new Color(255,0,0)).setMaterial(new Material().setkD(new Double3(0.5)).setkS(new Double3(0.5)).setnShininess(300)));
         this.add(createRectangleXRotate(endOfNoise,size*0.8*0.45, size/50,angle).setEmisson(new Color(255,0,0)));
         this.add(createRectangleXRotate(endOfNoise,size/50, size*0.8*0.45,angle).setEmisson(new Color(255,0,0)));
    
    Point start  =center.add(new Vector(0,1,0).scale(size/7));

      this.add(new Triangle(start,start.add(new Vector(0,1,0).scale(size/5)) ,start.add(new Vector(-1,0,0).scale(size/5))).setEmisson(new Color(255,0,0)).setMaterial(new Material().setkD(new Double3(0.5)).setkS(new Double3(0.5)).setnShininess(300)));
      this.add(new Triangle(center, center.add(new Vector(0,0,1).scale(size*7)),
       end).setEmisson(new Color(255,0,0)).setMaterial(new Material().setkD(new Double3(0.5)).setkS(new Double3(0.5)).setnShininess(300)));
       this.add(new Triangle(center, center.add(new Vector(0,0,-1).scale(size*7)),
       end).setEmisson(new Color(255,0,0)).setMaterial(new Material().setkD(new Double3(0.5)).setkS(new Double3(0.5)).setnShininess(300)));
    }


    Polygon createRectangleY(Point canter, double width, double height) {
        return new Polygon(new Point[] {
            canter,
            canter.add(new Vector(height/2, 0, 0)),
            canter.add(new Vector(height/2, 0, width/2)),
            canter.add(new Vector(0, 0, width/2)),
        });
    } 
    Polygon createRectangleX(Point canter, double width, double height) {
        return new Polygon(new Point[] {
            canter.add(new Vector(0, height/2, width/2)),
            canter.add(new Vector(0, -height/2, width/2)),
            canter.add(new Vector(0, -height/2, -width/2)),
            canter.add(new Vector(0, height/2, -width/2)),
        });
    }
    Polygon createRectangleXRotate(Point canter, double width, double height,double angle) {
        return new Polygon(new Point[] {
            canter.add(new Vector(0, height/2, width/2).Roatate(angle, new Vector(1,0,0))),
            canter.add(new Vector(0, -height/2, width/2).Roatate(angle, new Vector(1,0,0))),
            canter.add(new Vector(0, -height/2, -width/2).Roatate(angle, new Vector(1,0,0))),
            canter.add(new Vector(0, height/2, -width/2).Roatate(angle, new Vector(1,0,0))),
        });
    }

    public Sensa rotate(double angle){
        return new Sensa(center, size,angle );
    }


}
