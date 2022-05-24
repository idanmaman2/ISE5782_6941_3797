package OBJParser;
import java.util.LinkedList;
import java.util.List;
import geometries.Intersectable;
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
    final double EPS = 0.0000001;
    final Vector VEPS = new Vector(EPS,EPS,EPS); 
    ObjParserModel(List<List<Vector>> lst){
        this.lst = lst ; 
        start = new Point(0,0,0); 
    }
    ObjParserModel(List<List<Vector>> lst,Point start ){
        this.lst = lst ; 
        this.start = start; 
    }
    public ObjParserModel scale(double scaleSize){
        ObjParserModel grounded = this.changeStartingPoint(new Point(0,0,0)); 
        ObjParserModel scaled = new ObjParserModel(grounded.lst.stream().map((i)->i.stream().map((j)->j.scale(scaleSize)).toList()
        ).toList());
        return scaled.changeStartingPoint(start);
    }
    public ObjParserModel rotate(double angle,Vector axsis){
        ObjParserModel grounded = this.changeStartingPoint(new Point(0,0,0)); 
        ObjParserModel rotated = new  ObjParserModel(grounded.lst.stream().map((i)->i.stream()
            .map((j)->j.Roatate(angle,axsis)).toList()).toList());
        return rotated.changeStartingPoint(start);
    }
    public ObjParserModel changeStartingPoint(Point newStart){
        return new ObjParserModel(lst.stream().map((i)->i.stream().map((j)-> j.add(new Vector(EPS + start.getX(),EPS + start.getY(),EPS + start.getZ()).add(VEPS).scale(-1)).add(new Vector(EPS + newStart.getX(),EPS  + newStart.getY(),EPS + newStart.getZ()))).toList()).toList(),newStart);
    }
    public List<Triangle> getTriangles(){
        List<Triangle> newLst = new LinkedList<Triangle>();
        for(var i :lst){
            try{
                newLst.add(new Triangle(i.get(0), i.get(1), i.get(2)));
            } 
            catch(Exception e) {
                //I dont realy care about that 
              }
       
        }
        return newLst ; 
    } 
    public Intersectable []  getColoredTriangles(Double3 kD , Double3 kS , Double3 kR , Double3 kT ,Color color  , int shine ){

        return this.getTriangles().stream().map((e)->(Intersectable)e.setEmisson(color) //
        .setMaterial(new Material().setkD(kD).setkS(kS).setnShininess(shine))).toArray(Intersectable[]::new);
    }

    public Intersectable []  getRandomColoredTriangles(Double3 kD , Double3 kS , Double3 kR , Double3 kT  , int shine ){

        return this.getTriangles().stream().map((e)->(Intersectable)e.setEmisson(Color.getRandomEmission()) //
        .setMaterial(new Material().setkD(kD).setkS(kS).setnShininess(shine))).toArray(Intersectable[]::new);
    }

    public Intersectable []  getTexturedTriangles(Double3 kD , Double3 kS , Double3 kR , Double3 kT ,Texture tx  , int shine ){

        return this.getTriangles().stream().map((e)->(Intersectable)new TTriangle(e,tx) //
        .setMaterial(new Material().setkD(kD).setkS(kS).setnShininess(shine))).toArray(Intersectable[]::new);
    }


    

    
}
