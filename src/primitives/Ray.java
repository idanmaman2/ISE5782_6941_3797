package primitives;

public class Ray {
    private Point p0;
    private Vector dir;

    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Ray) && (((Ray)obj).p0.equals(this.p0) && ((Ray)obj).dir.equals(this.dir));
    }

    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0 +
                ", dir=" + dir +
                '}';
    }
}