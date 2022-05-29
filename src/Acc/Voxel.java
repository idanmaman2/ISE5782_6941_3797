package Acc;
import java.util.LinkedList;
import java.util.List;

import geometries.*;
import geometries.Intersectable.GeoPoint;
import primitives.Point;
import primitives.Ray;
import primitives.Util;
public class Voxel {
    List<Voxelable> objects = null ;
    final Point vMin ; 
    final Point vMax;
    public Voxel (Point vMin , Point vMax ){
        this.vMin = vMin ; 
        this.vMax = vMax ; 

    }
  
    public boolean collision(Ray ray){
        double tmin = Util.alignZero((vMin.getX() - ray.getP0().getX()) / ray.getDir().getX()); 
        double tmax =Util.alignZero( (vMax.getX() - ray.getP0().getX()) / ray.getDir().getX()); 
        if (tmin > tmax){
            double tmp = tmin ; 
            tmin = tmax ; 
            tmax = tmp ; 
        }
        double  tymin = Util.alignZero((vMin.getY() - ray.getP0().getY()) / ray.getDir().getY()); 
        double  tymax = Util.alignZero((vMax.getY() - ray.getP0().getY()) / ray.getDir().getY()); 
     
        if (tymin > tymax){
            double tmp = tymin ; 
            tymin = tymax ; 
            tymax = tmp ; 
        }
     
        if ((tmin > tymax) || (tymin > tmax)) 
            return false; 
     
        if (tymin > tmin) 
            tmin = tymin; 
     
        if (tymax < tmax) 
            tmax = tymax; 
     
        double  tzmin = Util.alignZero((vMin.getZ() - ray.getP0().getZ()) / ray.getDir().getZ()); 
        double  tzmax = Util.alignZero((vMax.getZ() - ray.getP0().getZ()) / ray.getDir().getZ()); 
     
        if (tzmin > tzmax) {
        double tmp = tzmin ; 
        tzmin = tzmax ; 
        tzmax = tmp ; 
    }
     
        if ((tmin > tzmax) || (tzmin > tmax)) 
            return false; 
     
        if (tzmin > tmin) 
            tmin = tzmin; 
     
        if (tzmax < tmax) 
            tmax = tzmax; 
     
        return true; 
    }

    public void add(Voxelable ... params){
        if(objects == null){
            objects = new LinkedList<>(); 
        }
        objects.addAll(List.of(params));
    }
   
    public List<GeoPoint> collisoned(Ray ray){
        if(objects == null){
            return null ; 
        }
        LinkedList<GeoPoint> points = null   ; 
        for(Voxelable vox : objects){
            List<GeoPoint> colls = vox.findGeoIntersections(ray); 
            if(colls != null){
                if(points==null){
                    points = new LinkedList<>(); 
                }
                points.addAll(colls);
            }
        }
        return points ; 
    }

    public Point getvMin(){
        return this.vMin;
    }

    public Point getvMax(){
        return this.vMax ; 
    }

}
