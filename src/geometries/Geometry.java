package geometries;


import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

public abstract class  Geometry extends Intersectable {
    
    protected Color emisson = Color.BLACK ; 
    private Material mat = new Material();
    
    public abstract Vector getNormal (Point point );
    
    public Color getEmisson(){
        return emisson;
    }

    public Geometry setEmisson(Color emisson) {
        this.emisson = emisson;
        return this; 

    }

    public Material getMaterial(){
        return mat;
    }

    public Geometry setMaterial(Material mat) {
        this.mat = mat;
        return this; 

    }








}

