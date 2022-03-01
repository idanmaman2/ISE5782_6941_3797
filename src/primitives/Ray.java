package primitives;

public class Ray {
    private Point p0;
    private Vector dir;

    public Point getP0() {
        return this.p0;
    }

    public void setP0(Point p0) {
        this.p0 = p0;
    }

    public Vector getDir() {
        return this.dir;
    }

    public void setDir(Vector dir) {
        this.dir = dir;
    }

    public Ray(Point p0, Vector dir) { //simple constructor
        this.p0 = p0;
        this.dir = dir;
    }

    @Override
    public boolean equals(Object obj) { // checks if the two equal
        return (obj instanceof Ray) && (((Ray)obj).p0.equals(this.p0) && ((Ray)obj).dir.equals(this.dir));
    }

    @Override
    public String toString() {//simple command line
        return "Ray{" +
                "p0=" + p0 +
                ", dir=" + dir +
                '}';
    }
}