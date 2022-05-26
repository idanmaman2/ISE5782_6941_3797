package OBJParser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import com.mokiat.data.front.parser.*;

import primitives.Texture;
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
        List<List<Texture.ImageCords>> lstTet= new LinkedList<>();
        for (OBJObject object : model.getObjects()) {
            for (OBJMesh mesh : object.getMeshes()) {
                for (OBJFace face : mesh.getFaces()) {
                    List<Vector> lstPackageHelp  = new ArrayList<Vector>(3); 
                    List<Texture.ImageCords> lstTetHelp=  new ArrayList<Texture.ImageCords>(3);
                    for (OBJDataReference reference : face.getReferences()) {                 
                        final OBJVertex vertex = model.getVertex(reference);
                        lstPackageHelp.add(new Vector(vertex.x + EPS ,vertex.y + EPS ,vertex.z + EPS));
                        if (reference.hasTexCoordIndex()) {
                            final OBJTexCoord texCoord = model.getTexCoord(reference);
                            lstTetHelp.add(new Texture.ImageCords(texCoord.u, texCoord.v));
                        }
                    }
                    lstPackage.add(lstPackageHelp);
                    lstTet.add(lstTetHelp);

                }
            }
        }

        return new ObjParserModel(lstPackage,lstTet) ;
    }

    



}