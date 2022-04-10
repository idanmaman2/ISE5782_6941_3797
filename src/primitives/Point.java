package primitives;
/**
 *represnts linear point in the real numbers world
 *
 * @author Idan and Eliyahu
 */
public class Point {
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

    public class LongLat{
        public double lat; 
        public double lon; 
        LongLat(double x1, double x2){
            lat = x1 ;
            lon = x2 ; 
        }

    }

     final   double E = 0.0000000848191908426;
     final  double D2R = Math.PI / 180;
     final double PiDiv4 = Math.PI / 4;
   final double HalfPi = Math.PI / 2;
   private final double C1 = 0.00335655146887969;
   private final double C2 = 0.00000657187271079536;
   private final double C3 = 0.00000001764564338702;
   private final double C4 = 0.00000000005328478445;
    public  LongLat ToLonLat( double Radius)
    {
        double x =this.xyz.d1 ; 
         double y = this.xyz.d3 ;
        double g = HalfPi - 2 * Math.atan(1 / Math.exp(y / Radius));
        double latRadians = g + C1 * Math.sin(2 * g) + C2 * Math.sin(4 * g) + C3 * Math.sin(6 * g) + C4 * Math.sin(8 * g);

        double lonRadians = x / Radius;

        double lon = lonRadians / D2R ;
        double lat = latRadians / D2R ;

        return new LongLat(lon,lat);
    }


}
