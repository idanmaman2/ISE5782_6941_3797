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
    private List<Intersectable> items ; 
    public Geometries(Intersectable... geometries){
        items =new LinkedList<Intersectable>(List.of(geometries));
    }
    public void add(Intersectable... geometries){
        items.addAll(List.of(geometries));
    }



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



    @Override 
    public List<Point> findIntsersections(Ray ray){
        List<Point> it = new LinkedList<Point>();
        for(Intersectable element : this.items){
            List<Point> x =element.findIntsersections(ray);
            if(x!= null ){
                it.addAll(x);
            }
           
        }
        return it.size() == 0 ? null :  it ;
    }




    }

