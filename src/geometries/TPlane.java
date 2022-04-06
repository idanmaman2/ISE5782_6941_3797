package geometries;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import primitives.*;

/**
 *Plane
 *
 * @author Idan and Eliyahu
 */
public class TPlane extends Plane {

    Texture tx ; 

    public TPlane(Point q1 , Point q2 , Point q3 , Texture x  ){ //constructor
        super(q1,q2,q3);
this.tx = x ;
    }
    

    public TPlane(Point q0, Vector normal,Texture x) { //simple constructor
        super(q0,normal);
        this.tx = x ;
    }
@Override
public Color getEmisson(GeoPoint x){
    return tx.getColor(x,super.getEmisson(x)); 
}




    }

