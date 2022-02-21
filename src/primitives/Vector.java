package primitives;

import java.awt.*;

public class Vector extends Point {

    public Vector(double d1, double d2, double d3) {
        super(d1, d2, d3);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "Vector{" +
                "xyz=" + xyz +
                '}';
    }
    public Vector add (Vector vc ){
        return new Vector(this.xyz.d1 + vc.xyz.d1 ,
                            this.xyz.d2 + vc.xyz.d2 ,
                            this.xyz.d3 + vc.xyz.d3);

    }
}
