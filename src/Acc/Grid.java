package Acc;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.management.ObjectName;

import Acc.Voxelable.MaxMin;
import geometries.Geometries;
import geometries.Intersectable;
import geometries.Plane;
import geometries.Intersectable.GeoPoint;
import primitives.Double3;
import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

public class Grid {
    int size ; 
    double length ; 
    Point stratingPoint ; 
    final List<List<List<Voxel>>> voxels; // 3d grid voxels 
    final Plane [] planes   ; 
    
    //TESTED 
    public Grid(List<Voxelable> objects ,int size ){
        MaxMin minMaxTotal =null  ; 
        for(Voxelable object : objects){
                MaxMin  minMaxLocal = object.getMaxMin() ; 
                if(minMaxTotal == null){   
                    minMaxTotal = minMaxLocal ; 
                }
                else{
                    double minx =minMaxTotal.min.getX()  , miny = minMaxTotal.min.getY() ,minz = minMaxTotal.min.getZ() ,
                     maxx = minMaxTotal.max.getX() , maxy = minMaxTotal.max.getY() , maxz = minMaxTotal.max.getZ() ; 
                    boolean changed =false ;  
                    if(minMaxLocal.max.getX() > minMaxTotal.max.getX()){
                        maxx = minMaxLocal.max.getX() ; 
                        changed = true ;    
                    }
                    if(minMaxLocal.max.getY() > minMaxTotal.max.getY()){
                        maxy = minMaxLocal.max.getY() ; 
                        changed = true ;    
                    }
                    if(minMaxLocal.max.getZ() > minMaxTotal.max.getZ()){
                        maxx = minMaxLocal.max.getZ() ; 
                        changed = true ;    
                    }
                    if(minMaxLocal.min.getX() < minMaxTotal.min.getX()){
                        minx = minMaxLocal.min.getX() ; 
                        changed = true ;    
                    }
                    if(minMaxLocal.min.getY() < minMaxTotal.min.getY()){
                        miny = minMaxLocal.min.getY() ; 
                        changed = true ;    
                    }
                    if(minMaxLocal.min.getZ() < minMaxTotal.min.getZ()){
                        minx = minMaxLocal.min.getZ() ; 
                        changed = true ;    
                    }
                    if(changed){
                        minMaxTotal = new MaxMin(minx ,miny ,minz ,maxx, maxy , maxz );
                    }
                }
            }
         
        if(minMaxTotal == null){
            throw new IllegalAccessError("cant create grid");
    
            
        }
        else{
            this.stratingPoint = minMaxTotal.min ; 
            this.length = Math.max(minMaxTotal.max.getX() - minMaxTotal.min.getX(), Math.max(minMaxTotal.max.getY() - minMaxTotal.min.getY(),minMaxTotal.max.getZ() - minMaxTotal.min.getZ()))/ size ;
            this.size = size ; 
            this.voxels  = new ArrayList<List<List<Voxel>>>(size);
            for(int i = 0 ; i<size ; i++){
                List<List<Voxel>> column = new ArrayList<List<Voxel>>(size);
                for(int j=0;j<size ; j++){
                    List<Voxel> row = new ArrayList<Voxel>(size);
                    for(int k = 0 ; k < size ; k++){
                        Point vMin = stratingPoint;
                        if(i!=0 ){
                            vMin = vMin.add(Vector.X.scale(i*length));
                        }
                        if(j!=0){
                            vMin = vMin.add(Vector.Y.scale(j*length));
                        }
                        if(k!=0){
                            vMin = vMin.add(Vector.Z.scale(k*length));
                        }
                        Point vMax  = vMin.add(Vector.X.scale(length)).add(Vector.Y.scale(length)).add(Vector.Z.scale(length));
                       Voxel voxel = new Voxel(vMin, vMax);
                       for(Intersectable object : objects){
                           if(object instanceof Voxelable){
                               Voxelable objectVox = (Voxelable)object; 
                               if(objectVox.colllisionWithVoxel(voxel)){
                                   voxel.add(objectVox);
                               }
                           }
                       }
                       row.add(voxel);

                    }
                    column.add(row);
                }
                voxels.add(column);
            }
            Point max = getMax(); 
            planes  = new Plane [] {   new Plane(stratingPoint , new Vector(1,0,0)), //front*
                new Plane(max , new Vector(1,0,0)) , //back -
                 new Plane(max, new Vector(0,1,0)) , // top - 
                 new Plane(stratingPoint , new Vector(0,1,0)), // bot  * 
                new Plane(stratingPoint , new Vector(0,0,1)) , // right *
               new Plane(max , new Vector(0,0,1)) };
    }
        
    }
            
    //TESTED
    public Voxel getVoxel(int i , int j , int k ){
        return voxels.get(i).get(j).get(k);
    }

    //TESTED
    public Voxel getVoxel(Double3 indexes){
        return voxels.get((int)indexes.d1).get((int)indexes.d2).get((int)indexes.d3);
    }

    //TESTED
    public Point getStartingPoint(){
        return this.stratingPoint;
    }

    //TESTED
    public double getLength(){
        return this.length;
    }

    //TESTED
    public int getSize(){
        return size;
    }

    //TESTED 
    public int getNumOfVoxels(){
        return size*size*size;
    }

    //TESTED 
    public boolean isEmpty(){
        return voxels == null || length == 0 || size ==0 ;

    }

    //TESTED 
    public Point getMin(){
        return this.stratingPoint ; 
    }

    //TESTED 
    public Point getMax(){
        return stratingPoint.add(Vector.X.scale(size*length)).add(Vector.Y.scale(size*length)).add(Vector.Z.scale(size*length));
    }

    //TO DO : TEST 
    public boolean collision(Ray ray){
          //the ray's head and direction points
          Point dir = ray.getDir();
          Point point = ray.getP0();
        Point max = getMax(); 
        Point min = getMin() ;
         double minX = min.getX() , minY = min.getY() , minZ =min.getZ() , maxX = max.getX() , maxY = max.getY() , maxZ = max.getZ() ; 
          double xMax, yMax, zMax, xMin, yMin, zMin;
  
          //if the vector's x coordinate is zero
          if (Util.isZero(dir.getZ())) {
              //if the point's x value is in the box,
              if (maxX >= point.getX() && minX <= point.getX()) {
                  xMax = Double.MAX_VALUE;
                  xMin = Double.MIN_VALUE;
              } else
                  return false;
          }
  
          //if the vector's x coordinate is not zero, we need to check if we have values
          //where (MaxX - pointX) / dirX > (MinX - pointX) / dirX
          else {
              double t1 = (maxX - point.getX()) / dir.getX();
              double t2 = (minX - point.getX()) / dir.getX();
              xMin = Math.min(t1, t2);
              xMax = Math.max(t1, t2);
  
          }
  
          //if the vector's y coordinate is zero
          if (Util.isZero(dir.getY())) {
              //if the point's y value is in the box,
              if (maxX >= point.getY() && minY <= point.getY()) {
                  yMax = Double.MAX_VALUE;
                  yMin = Double.MIN_VALUE;
              } else
                  return false;
          }
  
          //if the vector's y coordinate is not zero, we need to check if we have values
          //where (MaxY - pointY) / dirY > (MinY - pointY) / dirY
          else {
              double t1 = (maxY - point.getY()) / dir.getY();
              double t2 = (minY - point.getY()) / dir.getY();
              yMin = Math.min(t1, t2);
              yMax = Math.max(t1, t2);
          }
  
          //if the vector's z coordinate is zero
          if (Util.isZero(dir.getZ())) {
              //if the point's z value is in the box,
              if (maxZ >= point.getZ() && minZ <= point.getZ()) {
                  zMax = Double.MAX_VALUE;
                  zMin = Double.MIN_VALUE;
              } else
                  return false ;
  
          }
  
          //if the vector's z coordinate is not zero, we need to check if we have values
          //where (MaxZ - pointZ) / dirZ > (MinZ - pointZ) / dirZ
          else {
              double t1 = (maxZ - point.getZ()) / dir.getZ();
              double t2 = (minZ - point.getZ()) / dir.getZ();
              zMin = Math.min(t1, t2);
              zMax = Math.max(t1, t2);
          }
  
          //check if such a point exists.
          if (xMin > yMax || xMin > zMax ||
                  yMin > xMax || yMin > zMax ||
                  zMin > yMax || zMin > xMax)
              return false;//if not return null
  
              //if they do, return all the intersection points of the contents of the box
     return true;
    }

    // TO DO : TEST 
    public List<Double3> findFirstAndLastVoxel(Ray ray){
        Point strat = ray.getP0() ,
         min = getMin(), 
         max  = getMax() ;  
        Point closet = null ; 
        Point farest = null ; 
         if(strat.getX()  >= min.getX() && strat.getY() >= min.getY() && strat.getZ() >= min.getZ() && strat.getX() <= max.getX() && strat.getY() < max.getX() && strat.getZ() < max.getZ()){
            closet = strat ; 
        }
        if(collision(ray)){
            for(Plane plane : planes ){
                List<GeoPoint> pointsTemp = plane.findGeoIntersections(ray);
                if(pointsTemp == null){
                  continue ; 
                }
                Point inter  = pointsTemp.get(0).point;
                if(closet == null || closet.distanceSquared(strat) > inter.distanceSquared(strat)){
                    closet = inter ; 
                }
                if(farest == null || farest.distanceSquared(strat) < inter.distanceSquared(strat)){
                    farest = inter ; 
                }
            }
        }
        if(closet == null ){
            return null ; 
        }
        Vector startinOfGridMinusInter = closet.subtract(min); 
        Vector farestMinysInter = farest.subtract(min);
        return List.of(
            new Double3(Math.floor(startinOfGridMinusInter.getX() / length) , Math.floor(startinOfGridMinusInter.getY() / length), Math.floor(startinOfGridMinusInter.getZ() / length)), 
            new Double3(Math.floor(farestMinysInter.getX() / length) , Math.floor(farestMinysInter.getY() / length), Math.floor(farestMinysInter.getZ() / length)),
             closet.xyz , farest.xyz
            ); 

    }

   
}
