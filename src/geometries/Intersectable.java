package geometries;
import java.util.List;
import primitives.*;

public abstract class Intersectable {
    /**
     * to find intersections with shape(3d)
     * 
     * 
     * @param ray
     * @return
     */


    public final List<Point> findIntsersections(Ray ray) {
        List<GeoPoint> inter = findGeoIntersections(ray, Double.POSITIVE_INFINITY);
        return inter == null ? null :  inter.stream().map((x)->x.point).toList();
    }
    
    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }

    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return findGeoIntersectionsHelper(ray, maxDistance);
    }
    
    protected abstract List<GeoPoint>findGeoIntersectionsHelper(Ray ray, double maxDistance);


  

    public static class GeoPoint {
        public final Geometry geometry;
        public final Point point;
        public GeoPoint(Point point , Geometry geometry){
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object x){
            return x instanceof GeoPoint 
            && ((GeoPoint)x).geometry.equals(geometry) &&
            ((GeoPoint)x).point.equals(point);
        }

    }
    

    
}
