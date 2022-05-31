package unittests.AccTests;

import org.junit.jupiter.api.Test;

import Acc.Voxel;
import OBJParser.ObjParser;
import OBJParser.ObjParserModel;
import lightning.*;
import geometries.*;
import primitives.*;
import primitives.Texture.ImageCords;
import renderer.*;
import Scene.Scene;
import static java.awt.Color.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.mokiat.data.front.parser.OBJModel;

/**
 * Test rendering a basic image
 * 
 * @author Dan
 */
public class AccTest {
	private Scene scene2 = new Scene("Test scene") //
			.setAl(new AmbientLight(new Color(WHITE), new Double3(0.15)));
	private Camera camera2 = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
			.setVPSize(200, 200) //
			.setVPDistance(1000);
	private Point[] p = { // The Triangles' vertices:
			new Point(-100, -100, -150), // the shared left-bottom
			new Point(50, 100, -150), // the shared right-top
			new Point(100, -100, -150), // the right-bottom
			new Point(-900, 900, 900) }; // the left-top
	private Point trPL = new Point(50, 30, -100); // Triangles test Position of Light
	private Color trCL = new Color(800, 500, 250); // Triangles test Color of Light
	private Vector trDL = new Vector(-2, -2, -2); // Triangles test Direction of Light
	private Material material = new Material().setkD(new Double3(0.5)).setkS(new Double3(0.5)).setnShininess(300).setKT(new Double3(0.5));
	private Geometry triangle1 = new Triangle(p[0], p[1], p[2]).setMaterial(material);
	private Geometry triangle2 = new Triangle(p[0], p[1], p[3]).setMaterial(material);

	
	@Test
	public void GridTest() throws FileNotFoundException, IOException {
		
			Scene scene1 = new Scene("Test scene");
			Camera camera1 = new Camera(new Point(50, -50, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
					.setVPSize(150, 150) //
					.setVPDistance(350);
			scene1.lights.add(new PointLight(new Point(20, -30, 20),new Color(555,555,0)).setKL(0.001).setKQ(0.0002));
			
	
			ImageWriter imageWriter = new ImageWriter("CUBE", 1000, 1000);
			ObjParser modelObjParser = new ObjParser("/Users/idang/Downloads/cube.obj") ;
			ObjParserModel obj = modelObjParser.getObjParserModel().scale(50).rotate(30, new Vector(1,1,1));
			for(List<Vector> v : obj.lst){
				for(Vector vv : v){
					System.out.println(vv);
				}
			}
		
	
			scene1.add(obj.getRandomColoredTriangles(new Double3(0.5), new Double3(0.5), new Double3(0.3),new Double3(0.9), 300));
			RayTracerBasic trc = new RayTracerBasic(scene1,false).setSize(10); 
			System.out.println("try 1: ");
			System.out.println(trc.getGrid().getMin());
			System.out.println(trc.getGrid().getMax());
			System.out.println(trc.getGrid().getLength());
			System.out.println(trc.getGrid().getSize());
			Point stratingPoint = trc.getGrid().getMin();
			Point max = trc.getGrid().getMax();
			Geometry [] planes  = new Geometry [] {   
				new Plane(stratingPoint , new Vector(1,0,0)).setMaterial(material).setEmisson(new Color(BLUE)), //front*
				new Plane(max , new Vector(1,0,0)).setMaterial(material).setEmisson(new Color(GREEN)) , //back -
				new Plane(max, new Vector(0,1,0)).setMaterial(material).setEmisson(new Color(GRAY)) , // top - 
				 new Plane(stratingPoint , new Vector(0,1,0)).setMaterial(material).setEmisson(new Color(PINK)), // bot  * 
				new Plane(stratingPoint , new Vector(0,0,1)).setMaterial(material).setEmisson(new Color(YELLOW)) , // right *
			   new Plane(max , new Vector(0,0,1)).setMaterial(material).setEmisson(new Color(BLACK)) //left 
			};
			scene1.add(planes);
			scene1.add(new Sphere(stratingPoint, 50).setEmisson(new Color(YELLOW)).setMaterial(material));
			scene1.add(new Sphere(max, 50).setEmisson(new Color(YELLOW)).setMaterial(material));
			for(int i = 0 ; i < 10 ; i ++ ){
				for(int j=0 ; j < 10 ; j ++){
					for(int k=0; k < 10 ; k++ ){
						Voxel vx = trc.getGrid().getVoxel(i, j, k);
						scene1.add(new Sphere(vx.getvMin(), 1).setEmisson(new Color(RED)).setMaterial(material));
					scene1.add(new Sphere(vx.getvMax(), 1).setEmisson(new Color(GREEN)).setMaterial(material));	
					}
				}
			}
			camera1.setWriter(imageWriter) //
			.setRayTrace(trc) //
			.renderImage() //
			.writeToImage(); 
			//Point{xyz=(-50.0000001,-50.0000001,-50.0000001)}
			//Point{xyz=(50.00004758371583,50.00004758371583,50.00004758371583)}
			//100.00004768371583
		}
      
	

}