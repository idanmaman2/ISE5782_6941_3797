package geometries;

import primitives.Point;
/**
 *Triangle
 *
 * @author Idan and Eliyahu
 */
public class Triangle extends Polygon {
   public  Triangle(Point x1, Point x2, Point x3)
    {
        super(x1,x2,x3);

    }
    @Override
    public boolean equals(Object obj) {//checks if equals
        return (obj instanceof Triangle) && super.equals(obj);
    }
}
