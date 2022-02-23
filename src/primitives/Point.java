package primitives;

public class Point {
    protected final Double3 xyz;

    public Point(double d1 , double d2 , double d3 ) {
        this.xyz = new Double3(d1,d2,d3);
    }
    protected Point(Double3 xyz){this.xyz = xyz ; }
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Point) && (((Point)obj).xyz.equals(this.xyz) );
    }
    public double distanceSquared(Point pt){
        return (pt.xyz.d1 - this.xyz.d1)*(pt.xyz.d1 - this.xyz.d1) +
                (pt.xyz.d2 - this.xyz.d2)*(pt.xyz.d2 - this.xyz.d2) +
                (pt.xyz.d3 - this.xyz.d3)*(pt.xyz.d3 - this.xyz.d3);
    }
    public double distance(Point pt){
        return Math.sqrt(this.distanceSquared(pt));
    }
    @Override
    public String toString() {
        return "Point{" +
                "xyz=" + xyz +
                '}';
    }

    public Point add (Vector v) {
        return new Point (v.xyz.d1+ this.xyz.d1 ,
                v.xyz.d2 + this.xyz.d2 ,
                v.xyz.d3 + this.xyz.d3);
    }

    public Vector subtract (Point v) {
        return new Vector (this.xyz.d1 - v.xyz.d1 ,
                this.xyz.d2  - v.xyz.d2 ,
                this.xyz.d3  - v.xyz.d3);
    }

}
