package geometries;
import java.util.List;
import primitives.*;

public interface Intersectable {
    /**
     * to find intersections with shape(3d)
     * 
     * 
     * @param ray
     * @return
     */
    public List<Point> findIntsersections(Ray ray);

}
