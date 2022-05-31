package geometries;

import java.util.LinkedList;
import java.util.List;

import Acc.Voxelable;
import geometries.Intersectable.GeoPoint;
import primitives.Ray;

public class VoxelGeometries extends Intersectable {

    protected List<Voxelable> items ; 

    public VoxelGeometries(Voxelable... geometries){
        items =new LinkedList<Voxelable>(List.of(geometries));
    }
    public void add(Voxelable... geometries){
        items.addAll(List.of(geometries));
    }

    public List<Voxelable> getItems(){
        return this.items ; 
    }

    /**
     * finds all the intersections
     */

    @Override 
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double max){
        List<GeoPoint> it = new LinkedList<GeoPoint>();
        for(Intersectable element : this.items){
            List<GeoPoint> x =element.findGeoIntersectionsHelper(ray, max);
            if(x!= null ){
                it.addAll(x);
            }
           
        }
        return it.size() == 0 ? null :  it ;
    }






    
}
