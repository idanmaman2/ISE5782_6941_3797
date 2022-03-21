package primitives;
/**
 *represnts linear point in the real numbers world
 *
 * @author Idan and Eliyahu
 */
public class Point {
    protected final Double3 xyz;
    public Point(double d1 , double d2 , double d3 ) {//simple constructor
        this.xyz = new Double3(d1,d2,d3);
    }
    protected Point(Double3 xyz){this.xyz = xyz ; }//simple constructor
    @Override
    public boolean equals(Object obj) {//checks if equals
        return (obj instanceof Point) && (((Point)obj).xyz.equals(this.xyz) );
    }
    public double distanceSquared(Point pt){//returns the duplicates of the difference of the two points
        return (pt.xyz.d1 - this.xyz.d1)*(pt.xyz.d1 - this.xyz.d1) +
                (pt.xyz.d2 - this.xyz.d2)*(pt.xyz.d2 - this.xyz.d2) +
                (pt.xyz.d3 - this.xyz.d3)*(pt.xyz.d3 - this.xyz.d3);
    }
    public double distance(Point pt){ // square root
        return Math.sqrt(this.distanceSquared(pt));
    }
    @Override
    public String toString() { // prints double3D
        return "Point{" +
                "xyz=" + xyz +
                '}';
    }

    public Point add (Vector v) { // adding two points
        return new Point (this.xyz.add(v.xyz));
    }

    public Vector subtract (Point v) { // subtract the two
        return new Vector (this.xyz.subtract (v.xyz));
    }

}
