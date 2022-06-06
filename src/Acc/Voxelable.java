package Acc;
import geometries.*;
import primitives.Point;
import primitives.Util;
public abstract  class Voxelable extends Geometry {

  //TESTED 
    public boolean colllisionWithVoxel(Voxel voxel){
        MaxMin minmax = this.getMaxMin();
        double minX = minmax.min.getX() , minY = minmax.min.getY() , minZ =  minmax.min.getZ() , 
        maxX = minmax.max.getX() , maxY =minmax.max.getY() , maxZ = minmax.max.getZ() ; 
        Point min  = voxel.getvMin() , max = voxel.getvMax() ; 
        double bminX = min.getX() , bminY = min.getY() , bminZ = min.getZ(), bmaxX = max.getX(), bmaxY= max.getY(), bmaxZ = max.getZ() ;
  
        return  
       minX <=  bmaxX  && maxX >= bminX &&
      minZ <= bmaxZ  && maxZ >= bminZ ||
        bminX <= maxX  &&bmaxX >= minX &&
       bminY <= maxY &&bmaxY >= minY &&
     bminZ<= maxZ  &&bmaxZ >= minZ ;
    }

    //TESTED 
    public abstract MaxMin  getMaxMin();
   
    //NO NEED TO BE TESETD 
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
