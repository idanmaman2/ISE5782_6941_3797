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
    public abstract List<Point> findIntsersections(Ray ray);

    public  List<GeoPoint> findGeoIntersections (Ray ray){
        return findGeoIntersectionsHelper(ray);
    }
    protected abstract List<GeoPoint> findGeoIntersectionsHelper (Ray ray);

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
