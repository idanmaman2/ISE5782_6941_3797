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
    
    //TESTED
    public Voxel (Point vMin , Point vMax ){
        this.vMin = vMin ; 
        this.vMax = vMax ; 

    }
  
    //TESTED
    public void add(Voxelable ... params){
       
        if(params == null || params.length == 0 ){
            return ; 
        }
       
        if(objects == null ){
            objects = new LinkedList<>(); 
        }
       
        objects.addAll(List.of(params));
    }
   
    //TESTED
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

    //TESTED 
    public Point getvMin(){
        return this.vMin;
    }

    //TESTED 
    public Point getvMax(){
        return this.vMax ; 
    }

}
