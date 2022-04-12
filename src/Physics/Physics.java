package Physics;

import primitives.*;

public class Physics {
    
    public Point moveWithAccaliration(Point xyz0 , Double3 a , Double3  v0 , double t ){
        double t2 = t*t ; 
        return new Point(
        xyz0.getX() + a.d1 * t2 * 0.5 + v0.d1 * t , 
        xyz0.getY() + a.d2 * t2 * 0.5 + v0.d2 * t , 
        xyz0.getZ() + a.d3 * t2 * 0.5 + v0.d3 * t 
        );
    }



}
