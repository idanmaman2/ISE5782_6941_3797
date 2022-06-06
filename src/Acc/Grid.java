package Acc;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.management.ObjectName;

import Acc.Voxelable.MaxMin;
import geometries.Geometries;
import geometries.Intersectable;
import geometries.Plane;
import geometries.Polygon;
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
    final Polygon [] planes   ; 
   


    //TESTED 
    public Grid(List<Voxelable> objects ,int size ){
        MaxMin minMaxTotal =null  ; 
        double maxXT = 0  , maxYT =0  , maxZT =0  , minXT =0  , minYT =0 , minZT = 0 ; 

        for(Voxelable object : objects){
                MaxMin  minMaxLocal = object.getMaxMin() ; 
                if(minMaxTotal == null){   
                    Point min = minMaxLocal.min , max = minMaxLocal.max ;
                    minXT = min.getX() ; 
                    minYT = min.getY() ; 
                    minZT = min.getZ();
                    maxXT = max.getX() ; 
                    maxYT = max.getY() ; 
                    maxZT = max.getZ() ;  
                    continue ;
                }
                else{
                   
                  
                    if(minMaxLocal.max.getX() > maxXT){
                        maxXT = minMaxLocal.max.getX() ;   
                    }
                    if(minMaxLocal.max.getY() > maxYT){
                        maxYT = minMaxLocal.max.getY() ;     
                    }
                    if(minMaxLocal.max.getZ() >maxZT){
                        maxZT = minMaxLocal.max.getZ() ;  
                    }
                    if(minMaxLocal.min.getX() < minXT){
                        minXT = minMaxLocal.min.getX() ;    
                    }
                    if(minMaxLocal.min.getY() < minYT){
                        minYT = minMaxLocal.min.getY() ;   
                    }
                    if(minMaxLocal.min.getZ() <minZT){
                        minZT= minMaxLocal.min.getZ() ; 
                    }
           
                }
            }
    minMaxTotal = new MaxMin(minXT ,minYT ,minZT ,maxXT, maxYT , maxZT );
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
                       for(Voxelable object : objects){
                               Voxelable objectVox = object; 
                               if(objectVox.colllisionWithVoxel(voxel)){
                                   voxel.add(objectVox);
                           }
                       }
                       row.add(voxel);

                    }
                    column.add(row);
                }
                voxels.add(column);
            }
            Point max = getMax(); 
            planes  = new Polygon [] { 
                new Polygon(stratingPoint , stratingPoint.add(Vector.X.scale(size*length)),stratingPoint.add(Vector.X.scale(size*length).add(Vector.Y.scale(size*length))),stratingPoint.add(Vector.Y.scale(size*length))), //left
				new Polygon(stratingPoint , stratingPoint.add(Vector.Z.scale(size*length)),stratingPoint.add(Vector.Z.scale(size*length).add(Vector.Y.scale(size*length))),stratingPoint.add(Vector.Y.scale(size*length))), //front 
				new Polygon(stratingPoint , stratingPoint.add(Vector.Z.scale(size*length)),stratingPoint.add(Vector.Z.scale(size*length).add(Vector.X.scale(size*length))),stratingPoint.add(Vector.X.scale(size*length))), // bot   
				new Polygon(max , max.add(Vector.Y.scale(size*length).scale(-1)),max.add(Vector.Y.scale(size*length).scale(-1)).add(Vector.Z.scale(size*length).scale(-1)),max.add(Vector.Z.scale(size*length).scale(-1))) , //back 
				new Polygon(max , max.add(Vector.X.scale(size*length).scale(-1)),max.add(Vector.X.scale(size*length).scale(-1)).add(Vector.Z.scale(size*length).scale(-1)),max.add(Vector.Z.scale(size*length).scale(-1))) , // top  
				new Polygon(max , max.add(Vector.Y.scale(size*length).scale(-1)),max.add(Vector.Y.scale(size*length).scale(-1)).add(Vector.X.scale(size*length).scale(-1)),max.add(Vector.X.scale(size*length).scale(-1))) // right 
            };
    }
        


    }
            
    //TESTED
    public Voxel getVoxel(int i , int j , int k ){
        return voxels.get(i).get(j).get(k);
    }

    //TESTED
    public Voxel getVoxel(Double3 indexes){
        return getVoxel((int)indexes.d1,(int)indexes.d2, (int)indexes.d3);
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

    public VoxelPath getPath(Ray ray){
        return new VoxelPath(this, ray);
    }
    //TESTED 
    public int getNumOfVoxels(){
        return size*size*size;
    }

    //TESTED 
    public Point getMin(){
        return this.stratingPoint ; 
    }

    //TESTED 
    public Point getMax(){
        return stratingPoint.add(Vector.X.scale(size*length)).add(Vector.Y.scale(size*length)).add(Vector.Z.scale(size*length));
    }

    //TESTED 
    public boolean collision(Ray ray){
          //the ray's head and direction points
          Point dir = ray.getDir();
          Point point = ray.getP0();
        Point max = getMax(); 
        Point min = getMin() ;
         double minX = min.getX() , minY = min.getY() , minZ =min.getZ() , maxX = max.getX() , maxY = max.getY() , maxZ = max.getZ() ; 
          double xMax, yMax, zMax, xMin, yMin, zMin;
  
          //if the vector's x coordinate is zero
          if (dir.getZ() == 0 ) {
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
          if (dir.getY() ==0 ) {
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
          if (dir.getZ()== 0 ) {
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


    Double3 toIndex(Point closet){
        Point min = getMin();
        double  closetX = Math.floor((closet.getX() - min.getX()) / length) , 
        closetY = Math.floor((closet.getY() - min.getY()) / length),
        closetZ = Math.floor((closet.getZ() - min.getZ()) / length) ;
        if(closetX == size ){
            closetX -- ;
        }
        if(closetY == size ){
            closetY -- ;
        }
        if(closetZ == size ){
            closetZ -- ;
        }
        return new Double3(closetX ,closetY ,closetZ);

    }
   
    Point fromIndex(Double3 index){
        Point vMin = stratingPoint;
        if(index.d1!=0 ){
            vMin = vMin.add(Vector.X.scale(index.d1*length));
        }
        if(index.d2!=0){
            vMin = vMin.add(Vector.Y.scale(index.d2*length));
        }
        if(index.d3!=0){
            vMin = vMin.add(Vector.Z.scale(index.d3*length));
        }
        
        return vMin;

    }

    // TO DO : TEST 
    public Double3 findFirstVoxel(Ray ray){
        Point strat = ray.getP0() ,
         min = getMin(), 
         max  = getMax() ;  
        Point closet = null ; 
        //X_min <= X <= X_max and Y_min <= Y <= Y_max  and Z_min <= Z <= Z_max
        // ray starts inside the grid - kt/kr - not original ray . 
         if(strat.getX()  >= min.getX() && strat.getY() >= min.getY() && strat.getZ() >= min.getZ() && strat.getX() <= max.getX() && strat.getY() <= max.getY() && strat.getZ() <= max.getZ()){
            closet = strat ; 
        }
        if(collision(ray)){
            for(Polygon quadanle : planes ){
                List<Point> pointsTemp = quadanle.findIntsersections(ray);
                if(pointsTemp == null){
                    continue ; 
                }
                Point inter  = pointsTemp.get(0);
                if(closet == null || closet.distanceSquared(strat) > inter.distanceSquared(strat)){
                    closet = inter ; 
                }
             }
        }
        if(closet == null ){ // if there is closet must be farest... - Logic 
            return null ; 
        } 
        return toIndex(closet);
       
             

    }

   
}
