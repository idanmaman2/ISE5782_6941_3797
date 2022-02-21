package primitives;

public class Point {
    protected final Double3 xyz;

    public Point(double d1 , double d2 , double d3 ) {
        this.xyz = new Double3(d1,d2,d3);
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Point) && (((Point)obj).xyz.equals(this.xyz) );
    }

    @Override
    public String toString() {
        return "Point{" +
                "xyz=" + xyz +
                '}';
    }

    public Point add (Vector v) {
        return new Point ()
    }

    public

}
