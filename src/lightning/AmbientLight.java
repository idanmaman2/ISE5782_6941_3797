package lightning;

import primitives.*;

/**
 *AmbientLight
 *
 * @author Idan and Eliyahu
 */
public class AmbientLight {
    private Color intensity;
    public AmbientLight(Color rgb , Double3 hanchata){
        this.intensity = rgb.scale(hanchata);

    }
    public AmbientLight(){
        this.intensity =Color.BLACK;
    }
    public Color getIntensity(){
        return intensity;
    }
    
}