package Scene;
import lightning.*;

import java.util.LinkedList;
import java.util.List;

import Acc.Voxelable;
import geometries.*;
import lightning.AmbientLight;
import primitives.*;

public class Scene {
    public String senceName ; 
    public Color bg ;
    public List<LightSource> lights ;
    int px , py ;


    public Color getBg(int i , int jy,int px, int py){
        return bg ;
    }


    public Scene setBg(Color bg) {
        this.bg = bg;
        return this;
    }

    public Scene setAl(AmbientLight al) {
        this.al = al;
        return this;
    }
    public Scene setAl(LightSource ... lights) {
        if(lights != null){
        this.lights.addAll(List.of(lights));
        }
        return this;
    }


    public void setGeometries(Geometries geometries) {
        this.geometries = geometries;
        for(Intersectable inter : geometries.getItems()){
            if(inter instanceof Voxelable){
                this.voxelableOnes.add((Voxelable)inter);
            }
            else{
                this.regularOnes.add(inter);
            }
        }
    }
    public AmbientLight al ;
    public Geometries geometries;
    public Geometries regularOnes ; 
    public VoxelGeometries voxelableOnes ;
    public Scene (String senceName){
        this.lights = new LinkedList<LightSource>();
        this.senceName = senceName; 
        this.bg = Color.BLACK;
        this.al = new AmbientLight();
        this.geometries = new Geometries();
    }
    public Scene Builder () {
        
        return this;
    } ; 
}
