package Acc;
import geometries.*;
import primitives.Point;
public abstract  class Voxelable extends Geometry {

    public boolean colllisionWithVoxel(Voxel voxel){
        MaxMin minmax = this.getMaxMin();
        double minX = minmax.min.getX() , minY = minmax.min.getY() , minZ =  minmax.min.getZ() , 
        maxX = minmax.max.getX() , maxY =minmax.max.getY() , maxZ = minmax.max.getZ() ; 
        Point min  = voxel.getvMin() , max = voxel.getvMax() ; 
        if(minX  >= min.getX() && minY >= min.getY() &&minZ >= min.getZ() && minX <= max.getX() && minY <= max.getX() && minZ <= max.getZ() ){
          return true ; 
        }
  
        if(minX  <= min.getX() && minY <=  min.getY() &&minZ  <= min.getZ() && maxX >= min.getX() && maxY >= min.getX() && maxZ >=  min.getZ() ){
          return true ; 
        }
  
        if(maxX  >= min.getX() && maxY >= min.getY() &&maxZ >= min.getZ() && maxX <= max.getX() && maxY <= max.getX() && maxZ <= max.getZ() ){
          return true ; 
        }
  
    
        if(minX  <= max.getX() && minY <=  max.getY() &&minZ  <= max.getZ() && maxX >= max.getX() && maxY >= max.getX() && maxZ >=  max.getZ() ){
          return true ; 
        }
  
        return false;
    }

    public abstract MaxMin  getMaxMin();
   
    public static class MaxMin { //PDS - Passive Data Structre 
        public final Point min ; 
        public final Point max ; 
        public MaxMin(double minX , double minY , double minZ , double maxX , double maxY, double maxZ){
            this(new Point(minX ,minY,minZ),new Point(maxX , maxY , maxZ));
        }
        public MaxMin(Point min , Point max){
            this.min = min ; 
            this.max = max; 
        }
    }
}
