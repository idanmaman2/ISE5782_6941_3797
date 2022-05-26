package OBJParser;
import java.util.LinkedList;
import java.util.List;
import geometries.Intersectable;
import geometries.Polygon;
import geometries.TTriangle;
import geometries.Triangle;
import primitives.Color;
import primitives.Double3;
import primitives.Material;
import primitives.Point;
import primitives.Texture;
import primitives.Vector;

public class ObjParserModel {
    final private Point start ; 
    final private List<List<Vector>> lst  ;
    final private List<List<Texture.ImageCords>> lstTet ; 
    final double EPS = 0.0000001;
    final Vector VEPS = new Vector(EPS,EPS,EPS); 
    ObjParserModel(List<List<Vector>> lst ,List<List<Texture.ImageCords>> lstTet ){
        this.lst = lst ; 
        start = new Point(0,0,0); 
        this.lstTet = lstTet;
    }
    ObjParserModel(List<List<Vector>> lst, List<List<Texture.ImageCords>> lstTet, Point start ){
        this.lst = lst ; 
        this.start = start; 
        this.lstTet = lstTet;
    }
    public ObjParserModel scale(double scaleSize){
        ObjParserModel grounded = this.changeStartingPoint(new Point(0,0,0)); 
        ObjParserModel scaled = new ObjParserModel(grounded.lst.stream().map((i)->i.stream().map((j)->j.scale(scaleSize)).toList()
        ).toList(),lstTet);
        return scaled.changeStartingPoint(start);
    }
    
    public ObjParserModel rotate(double angle,Vector axsis){
        ObjParserModel grounded = this.changeStartingPoint(new Point(0,0,0)); 
        ObjParserModel rotated = new  ObjParserModel(grounded.lst.stream().map((i)->i.stream()
            .map((j)->j.Roatate(angle,axsis)).toList()).toList(),lstTet);
        return rotated.changeStartingPoint(start);
    }
    public ObjParserModel changeStartingPoint(Point newStart){
        return new ObjParserModel(lst.stream().map((i)->i.stream().map((j)-> j.add(new Vector(EPS + start.getX(),EPS + start.getY(),EPS + start.getZ()).add(VEPS).scale(-1)).add(new Vector(EPS + newStart.getX(),EPS  + newStart.getY(),EPS + newStart.getZ()))).toList()).toList(),lstTet,newStart);
    }
    public List<Polygon> getShapes(){
        List<Polygon> newLst = new LinkedList<Polygon>();
        for(var i :lst){
            try{
                if(i.size() < 3){
                    throw new IllegalArgumentException("cant get lines or single points");
                }
                if(i.size() == 3 ){
                    newLst.add(new Triangle(i.get(0),i.get(1),i.get(2)));
                }
                else{
                    newLst.add(new Polygon(i.toArray(Point[]::new)));
                }
               
            } 
            catch(Exception e) {
                //I dont realy care about that 
              }
       
        }
        return newLst ; 
    } 
    public List<Triangle> getTTriangles(Texture tx){
        List<Triangle> newLst = new LinkedList<Triangle>();
        for(int i =0 ; i< lst.size() ; i++){
            try{
                newLst.add(new TTriangle(lst.get(i).get(0), lst.get(i).get(1), lst.get(i).get(2),lstTet.get(i),tx));
            } 
            catch(Exception e) {
                //I dont realy care about that 
              }
       
        }
        return newLst ; 
    } 
    public Intersectable []  getColoredTriangles(Double3 kD , Double3 kS , Double3 kR , Double3 kT ,Color color  , int shine ){

        return this.getShapes().stream().map((e)->(Intersectable)e.setEmisson(color) //
        .setMaterial(new Material().setkD(kD).setkS(kS).setnShininess(shine))).toArray(Intersectable[]::new);
    }

    public Intersectable []  getRandomColoredTriangles(Double3 kD , Double3 kS , Double3 kR , Double3 kT  , int shine ){

        return this.getShapes().stream().map((e)->(Intersectable)e.setEmisson(Color.getRandomEmission()) //
        .setMaterial(new Material().setkD(kD).setkS(kS).setnShininess(shine))).toArray(Intersectable[]::new);
    }
    public Intersectable []  getTexturedTriangles(Double3 kD , Double3 kS , Double3 kR , Double3 kT ,Texture tx  , int shine ){

        return this.getTTriangles(tx).stream().map((e)->(Intersectable)e //
        .setMaterial(new Material().setkD(kD).setkS(kS).setnShininess(shine))).toArray(Intersectable[]::new);
    }

    

    
}
