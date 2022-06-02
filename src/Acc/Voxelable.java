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
        Util.alignZero(minX - bmaxX) <= 0  && Util.alignZero(maxX - bminX) >=0 &&
        Util.alignZero(minY - bmaxY) <= 0  && Util.alignZero(maxY - bminY) >=0 &&
        Util.alignZero(minZ - bmaxZ) <= 0  && Util.alignZero(maxZ - bminZ) >= 0 ||
        Util.alignZero(bminX - maxX) <= 0  &&Util.alignZero(bmaxX - minX) >=0 &&
        Util.alignZero(bminY - maxY) <= 0  &&Util.alignZero(bmaxY - minY) >=0 &&
        Util.alignZero(bminZ - maxZ) <= 0  &&Util.alignZero(bmaxZ - minZ) >=0 ;
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
            this.min = min.alignZero() ; 
            this.max = max.alignZero(); 
        }
    }
}
