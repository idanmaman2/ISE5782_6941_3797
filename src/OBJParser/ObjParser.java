package OBJParser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import com.mokiat.data.front.parser.*;
import primitives.Vector;


public class ObjParser {
    final IOBJParser ObjParser2 ;
    final OBJModel model ;
    final List<OBJVertex> vert  ; 
    final double EPS = 0.0000001;
    public ObjParser(String objName) throws FileNotFoundException, IOException{
        try (InputStream in = new FileInputStream(objName)) {
            ObjParser2 = new OBJParser();
            model  = ObjParser2.parse(in); 
            vert = model.getVertices() ;
        }
    }
    public ObjParserModel getObjParserModel (){
        List<List<Vector>> lstPackage  = new LinkedList<>(); 
        for (OBJObject object : model.getObjects()) {
            for (OBJMesh mesh : object.getMeshes()) {
                for (OBJFace face : mesh.getFaces()) {
                    List<OBJDataReference>   x = face.getReferences();
                    List<OBJVertex>  verts  = x.stream().map(( ele )-> vert.get(ele.vertexIndex)).toList();
                    OBJVertex v1 = verts.get(0);
                    Vector  p1 = new Vector(v1.x+EPS , v1.y+EPS,v1.z+EPS);
                    OBJVertex v2 = verts.get(1);
                    Vector p2 = new Vector (v2.x+EPS , v2.y+EPS,v2.z+EPS);
                    OBJVertex v3 = verts.get(2);
                    Vector p3 = new Vector(v3.x+EPS , v3.y+EPS,v3.z+EPS);
                    lstPackage.add(List.of(p1,p2,p3));
                }
            }
        }
        return new ObjParserModel(lstPackage) ;
    }

    



}