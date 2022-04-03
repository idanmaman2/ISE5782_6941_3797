package Scene;

import geometries.*;
import lightning.AmbientLight;
import primitives.*;

public class Scene {
    public String senceName ; 
    public Color bg ;

    public Scene setBg(Color bg) {
        this.bg = bg;
        return this;
    }

    public Scene setAl(AmbientLight al) {
        this.al = al;
        return this;
    }


    public void setGeometries(Geometries geometries) {
        this.geometries = geometries;
    }
    public AmbientLight al ;
    public Geometries geometries;
    public Scene (String senceName){
        this.senceName = senceName; 
        this.bg = Color.BLACK;
        this.al = new AmbientLight();
        this.geometries = new Geometries();
    }
    public Scene Builder () {
        return this;
    } ; 
}
