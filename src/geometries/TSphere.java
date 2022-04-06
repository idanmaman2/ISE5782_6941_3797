package geometries;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import primitives.*;

/**
 *Spgere
 *
 * @author Idan and Eliyahu
 */
public class TSphere extends Sphere{
    Point center;
    double radius;
    Texture tx ; 
    public TSphere(Point center, double radius ,Texture tx ) {//simple constructor
        super(center,radius);
        this.tx =tx ; 

    }
    @Override
    public Color getEmisson(GeoPoint x){
        return tx.getColor(x,super.getEmisson(x)); 
    }



}