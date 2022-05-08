package geometries;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import primitives.Ray;

public class MinGeometries extends Geometries {

    @Override 
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double max){
        List<GeoPoint> it = new LinkedList<GeoPoint>();
        for(Intersectable element : super.items){
            List<GeoPoint> x  =element.findGeoIntersectionsHelper(ray, max);  
            if(x!= null ){
                Map<Double,List<GeoPoint>> filteredMin = x.stream().collect(
                    Collectors.groupingBy(
                        GeoPoint::getZ 
                    )   
                );
                for(Map.Entry<Double,List<GeoPoint>> entry : filteredMin.entrySet()){
                    List<GeoPoint> unfiltered = entry.getValue();

                    it.add(Collections.min(unfiltered));
                } 
            }
     
        }
        return it.size() == 0 ? null :  it ;
    }


    
}
