package Scene;

import primitives.Color;
import primitives.Texture;

public class TextureScene extends Scene {
    Texture tx ; 
    public TextureScene (String senceName,Texture x){
        super(senceName);
        tx = x ; 
        
    }
    @Override
    public Color getBg(int i , int j , int px, int py ){
        return tx.getColor(i,j,px,py) ;
    }

    
}
