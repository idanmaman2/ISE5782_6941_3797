package primitives;
/**
 *represnts linear point in the real numbers world
 *
 * @author Idan and Eliyahu
 */
public class Point implements Comparable<Point> {

    public static final Point ZERO =new Point(Double3.ZERO);
    public final Double3 xyz;
    public Point(double d1 , double d2 , double d3 ) {//simple constructor
        this.xyz = new Double3(d1,d2,d3);
    }

    public Point(Double3 xyz){
        this.xyz = xyz ; 
    }

    @Override
    public boolean equals(Object obj) {//checks if equals
        return (obj instanceof Point) && (((Point)obj).xyz.equals(this.xyz) );
    }

    /**
     *@return the duplicates of the difference of the two points
     *  
     */
    public double distanceSquared(Point pt){
        return (pt.xyz.d1 - this.xyz.d1)*(pt.xyz.d1 - this.xyz.d1) +
                (pt.xyz.d2 - this.xyz.d2)*(pt.xyz.d2 - this.xyz.d2) +
                (pt.xyz.d3 - this.xyz.d3)*(pt.xyz.d3 - this.xyz.d3);
    }

     /**
     *@return the duplicates of the difference of the two points -squared 
     *  
     */
    public double distance(Point pt){ 
        return Math.sqrt(this.distanceSquared(pt));
    }

    @Override
    public String toString() { 
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

    public double getX() {
        return xyz.d1;
    }
    public double getY() {
        return xyz.d2;
    }
    public double getZ() {
        return xyz.d3;
    }
    public Point middle(Point pt , double a , double b){
        return new Point(
            (this.xyz.d1 * a  + pt.xyz.d1 * b)/(a+b) ,
            (this.xyz.d2 * a + pt.xyz.d2 * b)/(a+b) ,
            (this.xyz.d3 * a + pt.xyz.d3 * b)/(a+b) 
        );

    }
    public class LongLat{
        public double lat; 
        public double lon; 
        LongLat(double x1, double x2){
            lat = x1 ;
            lon = x2 ; 
        }

    }


    @Override
    public int compareTo(Point o) {
        return ((Double)getZ()).compareTo(((Point) o).getZ()) ;
    }

    public double distanceFromLine(Ray x ){
        Point q = x.getP0().add(x.getDir());
        return this.subtract(x.getP0()).crossProduct(this.subtract(q)).length() / ((double)q.subtract(x.getP0()).length());
    }
}
