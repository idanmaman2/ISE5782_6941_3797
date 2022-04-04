package Scene;
import lightning.*;

import java.util.LinkedList;
import java.util.List;

import geometries.*;
import lightning.AmbientLight;
import primitives.*;

public class Scene {
    public String senceName ; 
    public Color bg ;
    public List<LightSource> lights ;

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
    }
    public AmbientLight al ;
    public Geometries geometries;
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
