package Acc;

import java.util.Iterator;
import java.util.List;

import primitives.Double3;
import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

public class VoxelPathIterator implements Iterator<Voxel>{    Grid grid ; 
    Ray ray ; 
    double tx , ty , tz ; 
    Double3 delta  ; 
    Point currentIndex ; 
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
        this.currentIndex = grid.fromIndex(startIndex) ; 
        this.tx = ray.getDir().getX() < 0 ?  startIndex.d1 * grid.getLength() -rayOrigin.getX() 
         :  (startIndex.d1+1) * grid.getLength() -rayOrigin.getX() ;
         this.ty = ray.getDir().getY() < 0 ?  startIndex.d2 * grid.getLength() -rayOrigin.getY() 
         :  (startIndex.d2+1) * grid.getLength() -rayOrigin.getY() ;
         this.tz = ray.getDir().getZ() < 0 ?  startIndex.d3 * grid.getLength() -rayOrigin.getZ() 
         :  (startIndex.d3+1) * grid.getLength() -rayOrigin.getZ() ;
        this.tx = tx / ray.getDir().getX() ; 
        this.ty = ty / ray.getDir().getY() ; 
        this.tz = tz / ray.getDir().getZ() ;   



    }


    private Voxel UpdateCurrent(double currentIndexXtmp , double currentIndexYtmp, double currentIndexZtmp ){
        if(currentIndex == null){
            return null ; 
         }
         currentIndex = new Point(currentIndexXtmp,currentIndexYtmp,currentIndexZtmp);
         Double3 inx = grid.toIndex(this.currentIndex); 
         if(inx == null){
             return null ;
         }
        return grid.getVoxel(inx);
    }



    @Override
    public boolean hasNext() {
        if(currentIndex == null){
            return false ;
        } 
        Point min = grid.getMin() , max = grid.getMax() ; 
        return !(
        currentIndex.getX() < min.getX() || 
        currentIndex.getX() > max.getX() || 
        currentIndex.getZ()< min.getZ() || 
        currentIndex.getZ()  >max.getZ() || 
        currentIndex.getY() < min.getY() || 
        currentIndex.getY() >max.getY() );
    }

    @Override
    public Voxel next() {
        Point min = grid.getMin() , max = grid.getMax() ; 
        double currentIndexXtmp = currentIndex.getX(),currentIndexYtmp=currentIndex.getY() ,currentIndexZtmp = currentIndex.getZ() ;
			
        if (tx < ty) {
				if (tx < tz) {
					tx += delta.d1;
					currentIndexXtmp += (ray.getDir().getX() < 0) ? -1 : 1;
					if (currentIndexXtmp < min.getX() || currentIndexXtmp > max.getX() ){
                        return UpdateCurrent(currentIndexXtmp, currentIndexYtmp, currentIndexZtmp);
                    }
				} else {
					tz += delta.d3;
					currentIndexZtmp += (ray.getDir().getZ() < 0) ? -1 : 1;
					if (currentIndexZtmp < min.getZ() || currentIndexZtmp >max.getZ()){
                        return UpdateCurrent(currentIndexXtmp, currentIndexYtmp, currentIndexZtmp);
                    }
				}
			} 
            
        else {
			if (ty< tz) {
				ty += delta.d2;
				currentIndexYtmp += (ray.getDir().getY() < 0) ? -1 : 1;
				if (currentIndexYtmp < min.getY() || currentIndexYtmp >max.getY()){
                    return UpdateCurrent(currentIndexXtmp, currentIndexYtmp, currentIndexZtmp);
                }
				} 
                else {
					tz  += delta.d3;
					currentIndexZtmp+= (ray.getDir().getZ() < 0) ? -1 : 1;
                    if (currentIndexZtmp < min.getZ() || currentIndexZtmp >max.getZ()){
                        return UpdateCurrent(currentIndexXtmp, currentIndexYtmp, currentIndexZtmp);
                    }
				}
			}
            return UpdateCurrent(currentIndexXtmp, currentIndexYtmp, currentIndexZtmp);

      
    }



    
}
