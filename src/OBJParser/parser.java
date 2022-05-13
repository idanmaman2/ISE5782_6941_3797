package OBJParser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import com.mokiat.data.front.parser.*;
import primitives.Vector;


public class parser {
    final IOBJParser parser ;
    final OBJModel model ;
    final List<OBJVertex> vert  ; 
    public parser(String objName) throws FileNotFoundException, IOException{
        try (InputStream in = new FileInputStream(objName)) {
            parser = new OBJParser();
            model  = parser.parse(in); 
            vert = model.getVertices() ;
        }
    }
    public parserModel getFaces (){
        List<List<Vector>> lstPackage  = new LinkedList<>(); 
        for (OBJObject object : model.getObjects()) {
            for (OBJMesh mesh : object.getMeshes()) {
                for (OBJFace face : mesh.getFaces()) {
                    List<OBJDataReference>   x = face.getReferences();
                    List<OBJVertex>  verts  = x.stream().map(( ele )-> vert.get(ele.vertexIndex)).toList();
                    OBJVertex v1 = verts.get(0);
                    Vector  p1 = new Vector(v1.x , v1.y,v1.z);
                    OBJVertex v2 = verts.get(1);
                    Vector p2 = new Vector (v2.x , v2.y,v2.z);
                    OBJVertex v3 = verts.get(2);
                    Vector p3 = new Vector(v3.x , v3.y,v3.z);
                    lstPackage.add(List.of(p1,p2,p3));
                }
            }
        }
        return new parserModel(lstPackage) ;
    }

    



}
