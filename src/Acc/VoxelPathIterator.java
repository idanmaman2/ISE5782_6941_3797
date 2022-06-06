package Acc;

import java.util.Iterator;
import java.util.List;

import primitives.Double3;
import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

public class VoxelPathIterator implements Iterator<Voxel>{    
    Grid grid ; 
    Ray ray ; 
    double tx , ty , tz ; 
    Double3 delta  ; 
    boolean haveFirst = false ;
    boolean first = true ;  
    int i , j , k ; 
    VoxelPathIterator(Grid grid , Ray ray ){ //set up ;)
        this.grid = grid ; 
        this.ray = ray ; 
        Point rayOrigin  = ray.getP0().subtract(grid.getMin());
        this.delta = new Double3(
            Math.abs(grid.getLength() / ray.getDir().getX())
            ,Math.abs(grid.getLength() / ray.getDir().getY())
            , Math.abs(grid.getLength() / ray.getDir().getZ())
            );
        Double3 startIndex = grid.findFirstVoxel(ray);
        if(startIndex == null ){
            return ; 
        }
        this.haveFirst = true ;
        i = (int)startIndex.d1 ;
        j = (int)startIndex.d2 ;
        k=(int)startIndex.d3 ; 

        Vector dir = ray.getDir();
        double dirX = ray.getDir().getX() , dirY = dir.getY() , dirZ = dir.getZ() ;  
        this.tx = dirX == 0 ? Double.POSITIVE_INFINITY  :
        dirX < 0 ?  startIndex.d1 * grid.getLength() -rayOrigin.getX() 
         :  (startIndex.d1+1) * grid.getLength() -rayOrigin.getX() ;
         this.ty = dirY == 0 ? Double.POSITIVE_INFINITY :  
         dirY < 0 ?  startIndex.d2 * grid.getLength() -rayOrigin.getY() 
         :  (startIndex.d2+1) * grid.getLength() -rayOrigin.getY() ;
         this.tz =dirZ == 0 ?  Double.POSITIVE_INFINITY :
          dirZ < 0 ?  startIndex.d3 * grid.getLength() -rayOrigin.getZ() 
         :  (startIndex.d3+1) * grid.getLength() -rayOrigin.getZ() ;
        
         this.tx = tx / dirX ; 
        this.ty = ty / dirY ; 
        this.tz = tz / dirZ  ;   
        



    }

    @Override
    public boolean hasNext() {
        if(!this.haveFirst){
            return false ;
        }
        int size = grid.getSize() ; 
        return !(
            k < 0 || k >= size || 
            i < 0 || i >= size ||
            j< 0 || j >= size );
    }

    @Override
    public Voxel next() {
        if(first){
            first = false ;
            return grid.getVoxel(i,j,k);
        }
		int size = grid.getSize() ; 
        if (tx < ty) {
				if (tx < tz) {
                    int stepX =  ray.getDir().getX() < 0 ? -1 : 1;
                    this.i  +=stepX ; 
                    if(i < 0 || i >= size ){
                        return null ;
                    }
                    tx += delta.d1;
				} else {
                    int stepZ =  ray.getDir().getZ() < 0 ? -1 : 1;
                    k += stepZ ; 
                    if(k < 0 || k >= size ){
                        return null ;
                    }
					tz += delta.d3;
				}
			} 
            
        else {
			if (ty< tz) {
                int stepY = ray.getDir().getY() < 0 ? -1 : 1;
                j += stepY ; 
				if(j< 0 || j >= size ){
                    return null;
                }
                ty += delta.d2;
			} 
            else {
                int stepZ =  ray.getDir().getZ() < 0 ? -1 : 1;
                k += stepZ ; 
                if(k < 0 || k >= size ){
                    return null;
                }
                tz += delta.d3;
			}
		}

        return grid.getVoxel(i,j,k) ;

      
    }



    
}
