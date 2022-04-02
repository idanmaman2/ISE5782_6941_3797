package primitives;
/**
 *represnts linear ray in the real numbers world
 *that contains point of start and dir - linear line !
 *
 * @author Idan and Eliyahu
 */
public class Ray {

    private final  Point p0;
    private final Vector dir;

    public Point getP0() {

        return this.p0;
    }

    public Vector getDir() {
        return this.dir;
    }

    public Ray(Point p0, Vector dir) { 
        this.p0 = p0;
        this.dir = dir;
    }

    @Override
    /**
    *
    *checks if the two equal
    *
    * @author Idan and Eliyahu
    */
    public boolean equals(Object obj) { 
        return (obj instanceof Ray) && (((Ray)obj).p0.equals(this.p0) && ((Ray)obj).dir.equals(this.dir));
    }

    @Override
    /**
    *converts ray obj to String 
    *
    * @author Idan and Eliyahu
    */
    public String toString() {
        return "Ray{" +
                "p0=" + p0 +
                ", dir=" + dir +
                '}';
    }
    public Point getPoint(double t){
        return this.p0.add(this.dir.scale(t));
        }
}