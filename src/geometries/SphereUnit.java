package geometries;

import java.util.LinkedList;
import java.util.List;


import primitives.*;


public class SphereUnit extends Geometries {
    public SphereUnit(Sphere ... geometries){
        super.add(geometries);
    }


    @Override

    public void add(Intersectable ... geometries){
        for(var i : geometries){
            if( ! (i instanceof Sphere)){
                throw new IllegalArgumentException("that add gets only spheres");
            }
        }
       this.add(geometries);
    }
 



}
