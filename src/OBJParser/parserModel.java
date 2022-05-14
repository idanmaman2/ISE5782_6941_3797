package OBJParser;

import java.util.List;

import geometries.Triangle;
import primitives.Point;
import primitives.Vector;

public class parserModel {
    final private Point start ; 
    final private List<List<Vector>> lst  ;
    parserModel(List<List<Vector>> lst){
        this.lst = lst ; 
        start = new Point(0,0,0); 
    }
    parserModel(List<List<Vector>> lst,Point start ){
        this.lst = lst ; 
        this.start = start; 
    }
    public parserModel scale(double scaleSize){
        return new parserModel(lst.stream().map((i)->i.stream().map((j)->j.scale(scaleSize)).toList()
        ).toList());
    }
    public parserModel rotate(double angle,Vector axsis){
        return new parserModel(lst.stream().map((i)->i.stream().map((j)->j.Roatate(angle,axsis)).toList()).toList());
    }
    public parserModel changeStartingPoint(Point newStart){
        return new parserModel(lst.stream().map((i)->i.stream().map((j)-> j.add(new Vector(start.getX(),start.getY(),start.getZ()).scale(-1)).add(new Vector(newStart.getX(),newStart.getY(),newStart.getZ()))).toList()).toList());
    }
    public List<Triangle> getTriangles(){
        return lst.stream().map((i)->new Triangle(i.get(0), i.get(1), i.get(2))).toList(); 
    } 
    
}
