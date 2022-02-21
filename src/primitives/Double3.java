package primitives;

public class Double3 {
    public double d1,d2,d3 ;

    public Double3(double d1, double d2, double d3) {
        this.d1 = d1;
        this.d2 = d2;
        this.d3 = d3;
    }

    protected Double3 ZERO() {
        return new Double3(0,0,0);
    }

    @Override
    public boolean equals(Object obj) {
        return this.d1 == ((Double3)obj).d1 &&
                this.d2 == ((Double3)obj).d2 &&
                this.d3 == ((Double3)obj).d3;
    }
}
