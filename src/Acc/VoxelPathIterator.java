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
        Vector dir = ray.getDir();
        double dirX = ray.getDir().getX() , dirY = dir.getY() , dirZ = dir.getZ() ;  
        this.tx = dirX < 0 ?  startIndex.d1 * grid.getLength() -rayOrigin.getX() 
         :  (startIndex.d1+1) * grid.getLength() -rayOrigin.getX() ;
         this.ty = dirY < 0 ?  startIndex.d2 * grid.getLength() -rayOrigin.getY() 
         :  (startIndex.d2+1) * grid.getLength() -rayOrigin.getY() ;
         this.tz = dirZ < 0 ?  startIndex.d3 * grid.getLength() -rayOrigin.getZ() 
         :  (startIndex.d3+1) * grid.getLength() -rayOrigin.getZ() ;
        this.tx = tx / dirX ; 
        this.ty = ty / dirY ; 
        this.tz = tz / dirZ  ;   



    }


    private Voxel UpdateCurrent(double currentIndexXtmp , double currentIndexYtmp, double currentIndexZtmp ){
        Double3 inx = grid.toIndex(this.currentIndex); 
        if(inx == null){
            return null ;
        }
        Voxel vx = grid.getVoxel(inx) ;
         currentIndex = new Point(currentIndexXtmp,currentIndexYtmp,currentIndexZtmp);
   
        return vx;
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
        currentIndex.getZ()  > max.getZ() || 
        currentIndex.getY() < min.getY() || 
        currentIndex.getY() > max.getY() );
    }

    @Override
    public Voxel next() {
        Point min = grid.getMin() , max = grid.getMax() ; 
        double currentIndexXtmp = currentIndex.getX(),currentIndexYtmp=currentIndex.getY() ,currentIndexZtmp = currentIndex.getZ() ;
			
        if (tx < ty) {
				if (tx < tz) {
					tx += delta.d1;
					currentIndexXtmp += ray.getDir().getX() < 0 ? -1 : 1;
					if (currentIndexXtmp < min.getX() || currentIndexXtmp > max.getX() ){
                        return UpdateCurrent(currentIndexXtmp, currentIndexYtmp, currentIndexZtmp);
                    }
				} else {
					tz += delta.d3;
					currentIndexZtmp += ray.getDir().getZ() < 0 ? -1 : 1;
					if (currentIndexZtmp < min.getZ() || currentIndexZtmp > max.getZ()){
                        return UpdateCurrent(currentIndexXtmp, currentIndexYtmp, currentIndexZtmp);
                    }
				}
			} 
            
        else {
			if (ty< tz) {
				ty += delta.d2;
				currentIndexYtmp += ray.getDir().getY() < 0 ? -1 : 1;
				if (currentIndexYtmp < min.getY() || currentIndexYtmp > max.getY()){
                    return UpdateCurrent(currentIndexXtmp, currentIndexYtmp, currentIndexZtmp);
                }
			} 
            else {
				tz  += delta.d3;
				currentIndexZtmp+= ray.getDir().getZ() < 0 ? -1 : 1;
                if (currentIndexZtmp < min.getZ() || currentIndexZtmp > max.getZ()){
                    return UpdateCurrent(currentIndexXtmp, currentIndexYtmp, currentIndexZtmp);
                }
			}
		}

        return UpdateCurrent(currentIndexXtmp, currentIndexYtmp, currentIndexZtmp );

      
    }



    
}
