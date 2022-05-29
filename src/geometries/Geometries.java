package geometries;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import primitives.*;

/**
 *Plane
 *
 * @author Idan and Eliyahu
 */
public class Geometries extends Intersectable {
    protected List<Intersectable> items ; 

    public Geometries(Intersectable... geometries){
        items =new LinkedList<Intersectable>(List.of(geometries));
    }
    public void add(Intersectable... geometries){
        items.addAll(List.of(geometries));
    }

    public List<Intersectable> getItems(){
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