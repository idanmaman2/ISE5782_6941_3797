package Acc;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.management.ObjectName;

import Acc.Voxelable.MaxMin;
import geometries.Geometries;
import geometries.Intersectable;
import primitives.Point;
import primitives.Vector;

public class Grid {
    int size ; 
    double length ; 
    Point stratingPoint ; 
    final List<List<List<Voxel>>> voxels; // 3d grid voxels 
    
    public Grid(List<Intersectable> objects ,int size ){
        MaxMin minMaxTotal =null  ; 
        for(Intersectable object : objects){
            if(object instanceof Voxelable){
                MaxMin  minMaxLocal = ((Voxelable)object).getMaxMin() ; 
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
                    if(minMaxLocal.min.getX() > minMaxTotal.min.getX()){
                        minx = minMaxLocal.min.getX() ; 
                        changed = true ;    
                    }
                    if(minMaxLocal.min.getY() > minMaxTotal.min.getY()){
                        miny = minMaxLocal.min.getY() ; 
                        changed = true ;    
                    }
                    if(minMaxLocal.min.getZ() > minMaxTotal.min.getZ()){
                        minx = minMaxLocal.min.getZ() ; 
                        changed = true ;    
                    }
                    if(changed){
                        minMaxTotal = new MaxMin(minx ,miny ,minz ,maxx, maxy , maxz );
                    }
                }
            }
         
        }
        if(minMaxTotal == null){
            this.length = 0 ; 
            this.size = 0 ; 
            this.stratingPoint = null ; 
            this.voxels = null ;
            
        }
        else{
            this.stratingPoint = minMaxTotal.min ; 
            this.length = Math.max(minMaxTotal.max.getX() - minMaxTotal.min.getX(), Math.max(minMaxTotal.max.getY() - minMaxTotal.min.getY(),minMaxTotal.max.getZ() - minMaxTotal.min.getZ()));
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
    }
    }
    
    public Grid(Geometries objects , int size ){
        this(objects.getItems(),size);
    }
    
    public Voxel getVoxel(int i , int j , int k ){
        return voxels.get(i).get(j).get(k);
    }

    public Point getStartingPoint(){
        return this.stratingPoint;
    }

    public double getLength(){
        return this.length;
    }

    public int getSize(){
        return size;
    }

    public int getNumOfVoxels(){
        return size*size*size;
    }

    public boolean isEmpty(){
        return voxels == null || length == 0 || size ==0 ;

    }
}