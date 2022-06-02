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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
	private Material material = new Material().setkD(new Double3(0.5)).setkS(new Double3(0.5)).setnShininess(300).setKT(new Double3(0.999999999));
	private Geometry triangle1 = new Triangle(p[0], p[1], p[2]).setMaterial(material);
	private Geometry triangle2 = new Triangle(p[0], p[1], p[3]).setMaterial(material);

	
	@Test
	public void VisualGridTest() throws FileNotFoundException, IOException {
		
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
      
	@Test
	public void PlaneTest(){
		for(int i=0 ; i<6 ; i ++){
			Scene scene1 = new Scene("Test scene 1 ");
			Camera camera1 = new Camera(new Point(50, -50, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
					.setVPSize(150, 150) //
					.setVPDistance(350);
			scene1.lights.add(new PointLight(new Point(20, -30, 20),new Color(555,555,0)).setKL(0.001).setKQ(0.0002));
			ImageWriter imageWriter = new ImageWriter("Plane" + i , 1000, 1000);
			scene1.add(triangle1);
			RayTracerBasic trc = new RayTracerBasic(scene1,false).setSize(10); 
			System.out.println("try 1: ");
			System.out.println(trc.getGrid().getMin());
			System.out.println(trc.getGrid().getMax());
			System.out.println(trc.getGrid().getLength());
			System.out.println(trc.getGrid().getSize());
			double length = trc.getGrid().getLength() ; 
			double size =trc.getGrid().getSize();
			Point stratingPoint = trc.getGrid().getMin();
			Point max = trc.getGrid().getMax();
			scene1.lights.add(new PointLight(new Point(20, -30, 20),new Color(555,555,0)).setKL(0.001).setKQ(0.0002));
			Geometry [] planes  = new Geometry [] {   
				new Polygon(stratingPoint , stratingPoint.add(Vector.X.scale(size*length)),stratingPoint.add(Vector.X.scale(size*length).add(Vector.Y.scale(size*length))),stratingPoint.add(Vector.Y.scale(size*length))).setMaterial(material).setEmisson(new Color(BLUE)), //left*
				new Polygon(stratingPoint , stratingPoint.add(Vector.Z.scale(size*length)),stratingPoint.add(Vector.Z.scale(size*length).add(Vector.Y.scale(size*length))),stratingPoint.add(Vector.Y.scale(size*length))).setMaterial(material).setEmisson(new Color(BLACK)), //front 
				new Polygon(stratingPoint , stratingPoint.add(Vector.Z.scale(size*length)),stratingPoint.add(Vector.Z.scale(size*length).add(Vector.X.scale(size*length))),stratingPoint.add(Vector.X.scale(size*length))).setMaterial(material).setEmisson(new Color(PINK)), // bot  * 
				new Polygon(max , max.add(Vector.Y.scale(size*length).scale(-1)),max.add(Vector.Y.scale(size*length).scale(-1)).add(Vector.Z.scale(size*length).scale(-1)),max.add(Vector.Z.scale(size*length).scale(-1))).setMaterial(material).setEmisson(new Color(GREEN)) , //back -
				new Polygon(max , max.add(Vector.X.scale(size*length).scale(-1)),max.add(Vector.X.scale(size*length).scale(-1)).add(Vector.Z.scale(size*length).scale(-1)),max.add(Vector.Z.scale(size*length).scale(-1))).setMaterial(material).setEmisson(new Color(GRAY)) , // top - 
				new Polygon(max , max.add(Vector.Y.scale(size*length).scale(-1)),max.add(Vector.Y.scale(size*length).scale(-1)).add(Vector.X.scale(size*length).scale(-1)),max.add(Vector.X.scale(size*length).scale(-1))).setMaterial(material).setEmisson(new Color(YELLOW)) // right *
			  
			};
			planes[i].setEmisson(new Color(RED)).setMaterial(new Material().setkS(new Double3(0)));
			scene1.add(planes[i]); 
			camera1.setWriter(imageWriter) //
			.setRayTrace(trc) //
			.renderImage() //
			.writeToImage(); 
		}
		

		
	}




	@Test
	public void GridCollisionTest(){

		Scene scene1 = new Scene("Test scene 1 ");
		Camera camera1 = new Camera(new Point(50, -50, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(150, 150) //
				.setVPDistance(350);
		scene1.lights.add(new PointLight(new Point(20, -30, 20),new Color(555,555,0)).setKL(0.001).setKQ(0.0002));
		ImageWriter imageWriter = new ImageWriter("CollisionTest", 1000, 1000);
		scene1.add(triangle1);
		RayTracerBasic trc = new RayTracerBasic(scene1,false).setSize(10); 
		System.out.println("try 1: ");
		System.out.println(trc.getGrid().getMin());
		System.out.println(trc.getGrid().getMax());
		System.out.println(trc.getGrid().getLength());
		System.out.println(trc.getGrid().getSize());
		Point stratingPoint = trc.getGrid().getMin();
		Point max = trc.getGrid().getMax();
		double length = trc.getGrid().getLength() ; 
		double size =trc.getGrid().getSize();
		scene1.lights.add(new PointLight(new Point(20, -30, 20),new Color(555,555,0)).setKL(0.001).setKQ(0.0002));
		Geometry [] planes  = new Geometry [] {   
			new Polygon(stratingPoint , stratingPoint.add(Vector.X.scale(size*length)),stratingPoint.add(Vector.X.scale(size*length).add(Vector.Y.scale(size*length))),stratingPoint.add(Vector.Y.scale(size*length))).setMaterial(material).setEmisson(new Color(BLUE)), //left*
			new Polygon(stratingPoint , stratingPoint.add(Vector.Z.scale(size*length)),stratingPoint.add(Vector.Z.scale(size*length).add(Vector.Y.scale(size*length))),stratingPoint.add(Vector.Y.scale(size*length))).setMaterial(material).setEmisson(new Color(BLACK)), //front 
			new Polygon(stratingPoint , stratingPoint.add(Vector.Z.scale(size*length)),stratingPoint.add(Vector.Z.scale(size*length).add(Vector.X.scale(size*length))),stratingPoint.add(Vector.X.scale(size*length))).setMaterial(material).setEmisson(new Color(PINK)), // bot  * 
			new Polygon(max , max.add(Vector.Y.scale(size*length).scale(-1)),max.add(Vector.Y.scale(size*length).scale(-1)).add(Vector.Z.scale(size*length).scale(-1)),max.add(Vector.Z.scale(size*length).scale(-1))).setMaterial(material).setEmisson(new Color(GREEN)) , //back -
			new Polygon(max , max.add(Vector.X.scale(size*length).scale(-1)),max.add(Vector.X.scale(size*length).scale(-1)).add(Vector.Z.scale(size*length).scale(-1)),max.add(Vector.Z.scale(size*length).scale(-1))).setMaterial(material).setEmisson(new Color(GRAY)) , // top - 
			new Polygon(max , max.add(Vector.Y.scale(size*length).scale(-1)),max.add(Vector.Y.scale(size*length).scale(-1)).add(Vector.X.scale(size*length).scale(-1)),max.add(Vector.X.scale(size*length).scale(-1))).setMaterial(material).setEmisson(new Color(YELLOW)) // right *
		  
		};
		scene1.add(planes); 
		Point Max = new Point(100.0,100.0,50.0);
		Point Min = new Point(-100.0,-100.0,-150.0);
		Vector dir = Max.subtract(Min);
		Ray ray = new Ray(Min.add(dir.scale(-1)),dir);
		//Point{xyz=(-100.0,-100.0,-150.0)}
		//Point{xyz=(100.0,100.0,50.0)}
		//20.0
		//10
		scene1.add(new Tube(ray, 3).setMaterial(material).setEmisson(new Color(ORANGE)));
		System.out.println(trc.getGrid().collision(ray));
		assertTrue(trc.getGrid().collision(ray));
		System.out.println(trc.getGrid().findFirstAndLastVoxel(ray));
		//assertEquals(trc.getGrid().findFirstAndLastVoxel(ray), List.of(new Double3(0,0,0) ,new Double3(10,10,10) ,Min.xyz,Max.xyz),"Voxels didnt found");
		//camera1.setWriter(imageWriter) //
		//.setRayTrace(trc) //
		//.renderImage() //
		//.writeToImage(); 

		scene1 = new Scene("Test scene 2 ");
		scene1.lights.add(new PointLight(new Point(20, -30, 20),new Color(555,555,0)).setKL(0.001).setKQ(0.0002));
		 imageWriter = new ImageWriter("CollisionTest 2 ", 1000, 1000);
		scene1.add(triangle1);
		trc = new RayTracerBasic(scene1,false).setSize(10); 
		System.out.println("try 1: ");
		System.out.println(trc.getGrid().getMin());
		System.out.println(trc.getGrid().getMax());
		System.out.println(trc.getGrid().getLength());
		System.out.println(trc.getGrid().getSize());
		 length = trc.getGrid().getLength() ; 
		 size =trc.getGrid().getSize();
		stratingPoint = trc.getGrid().getMin();
		max = trc.getGrid().getMax();
		scene1.lights.add(new PointLight(new Point(20, -30, 20),new Color(555,555,0)).setKL(0.001).setKQ(0.0002));
		planes  = new Geometry [] {   
			new Polygon(stratingPoint , stratingPoint.add(Vector.X.scale(size*length)),stratingPoint.add(Vector.X.scale(size*length).add(Vector.Y.scale(size*length))),stratingPoint.add(Vector.Y.scale(size*length))).setMaterial(material).setEmisson(new Color(BLUE)), //left*
			new Polygon(stratingPoint , stratingPoint.add(Vector.Z.scale(size*length)),stratingPoint.add(Vector.Z.scale(size*length).add(Vector.Y.scale(size*length))),stratingPoint.add(Vector.Y.scale(size*length))).setMaterial(material).setEmisson(new Color(BLACK)), //front 
			new Polygon(stratingPoint , stratingPoint.add(Vector.Z.scale(size*length)),stratingPoint.add(Vector.Z.scale(size*length).add(Vector.X.scale(size*length))),stratingPoint.add(Vector.X.scale(size*length))).setMaterial(material).setEmisson(new Color(PINK)), // bot  * 
			new Polygon(max , max.add(Vector.Y.scale(size*length).scale(-1)),max.add(Vector.Y.scale(size*length).scale(-1)).add(Vector.Z.scale(size*length).scale(-1)),max.add(Vector.Z.scale(size*length).scale(-1))).setMaterial(material).setEmisson(new Color(GREEN)) , //back -
			new Polygon(max , max.add(Vector.X.scale(size*length).scale(-1)),max.add(Vector.X.scale(size*length).scale(-1)).add(Vector.Z.scale(size*length).scale(-1)),max.add(Vector.Z.scale(size*length).scale(-1))).setMaterial(material).setEmisson(new Color(GRAY)) , // top - 
			new Polygon(max , max.add(Vector.Y.scale(size*length).scale(-1)),max.add(Vector.Y.scale(size*length).scale(-1)).add(Vector.X.scale(size*length).scale(-1)),max.add(Vector.X.scale(size*length).scale(-1))).setMaterial(material).setEmisson(new Color(YELLOW)) // right *
		  
		};
		scene1.add(planes); 
		 Max = new Point(100.0,100.0,50.0);
		 Min = new Point(-100.0,-100.0,-150.0).add(new Vector(40,190,150));
		 dir = Max.subtract(Min);
		 ray = new Ray(Min.add(dir.scale(-1)),dir);
		//Point{xyz=(-100.0,-100.0,-150.0)}
		//Point{xyz=(100.0,100.0,50.0)}
		//20.0
		//10
		scene1.add(new Tube(ray, 3).setMaterial(material).setEmisson(new Color(ORANGE)));
		System.out.println(trc.getGrid().collision(ray));
		assertTrue(trc.getGrid().collision(ray));
		System.out.println(trc.getGrid().findFirstAndLastVoxel(ray));
		
		// 150 + - is 7 * 20 voxels on z  + half - so 7 voxels on z 
		// 190 - is 20 * 9- so 9.5 - so 9  voxels on y
		// 40 - is 2 * 10  - so 2 voxels on x
		//assertEquals(trc.getGrid().findFirstAndLastVoxel(ray), List.of(new Double3(2,9,7) ,new Double3(10,10,10) ,Min.xyz,Max.xyz),"Voxels didnt found");
		camera1.setWriter(imageWriter) //
		.setRayTrace(trc) //
		.renderImage() //
		.writeToImage(); 

		/**
		try 1: 
		Point{xyz=(-100.0,-100.0,-150.0)}
		Point{xyz=(100.0,100.0,50.0)}
		20.0
		10
		true
		[(-1.0,-1.0,-1.0), (10.0,10.0,10.0), (-116.21621621621622,-116.21621621621622,-150.00000000000003), (100.00000000000006,100.00000000000006,50.0)]
		 */

	
		scene1 = new Scene("Test scene 3 ");
		scene1.lights.add(new PointLight(new Point(20, -30, 20),new Color(555,555,0)).setKL(0.001).setKQ(0.0002));
		 imageWriter = new ImageWriter("CollisionTest 3 ", 1000, 1000);
		scene1.add(triangle1);
		trc = new RayTracerBasic(scene1,false).setSize(10); 
		System.out.println("try 1: ");
		System.out.println(trc.getGrid().getMin());
		System.out.println(trc.getGrid().getMax());
		System.out.println(trc.getGrid().getLength());
		System.out.println(trc.getGrid().getSize());
		stratingPoint = trc.getGrid().getMin();
		 length = trc.getGrid().getLength() ; 
		 size =trc.getGrid().getSize();
		max = trc.getGrid().getMax();
		scene1.lights.add(new PointLight(new Point(20, -30, 20),new Color(555,555,0)).setKL(0.001).setKQ(0.0002));
	 planes  = new Geometry [] {   
			new Polygon(stratingPoint , stratingPoint.add(Vector.X.scale(size*length)),stratingPoint.add(Vector.X.scale(size*length).add(Vector.Y.scale(size*length))),stratingPoint.add(Vector.Y.scale(size*length))).setMaterial(material).setEmisson(new Color(BLUE)), //left*
			new Polygon(stratingPoint , stratingPoint.add(Vector.Z.scale(size*length)),stratingPoint.add(Vector.Z.scale(size*length).add(Vector.Y.scale(size*length))),stratingPoint.add(Vector.Y.scale(size*length))).setMaterial(material).setEmisson(new Color(BLACK)), //front 
			new Polygon(stratingPoint , stratingPoint.add(Vector.Z.scale(size*length)),stratingPoint.add(Vector.Z.scale(size*length).add(Vector.X.scale(size*length))),stratingPoint.add(Vector.X.scale(size*length))).setMaterial(material).setEmisson(new Color(PINK)), // bot  * 
			new Polygon(max , max.add(Vector.Y.scale(size*length).scale(-1)),max.add(Vector.Y.scale(size*length).scale(-1)).add(Vector.Z.scale(size*length).scale(-1)),max.add(Vector.Z.scale(size*length).scale(-1))).setMaterial(material).setEmisson(new Color(GREEN)) , //back -
			new Polygon(max , max.add(Vector.X.scale(size*length).scale(-1)),max.add(Vector.X.scale(size*length).scale(-1)).add(Vector.Z.scale(size*length).scale(-1)),max.add(Vector.Z.scale(size*length).scale(-1))).setMaterial(material).setEmisson(new Color(GRAY)) , // top - 
			new Polygon(max , max.add(Vector.Y.scale(size*length).scale(-1)),max.add(Vector.Y.scale(size*length).scale(-1)).add(Vector.X.scale(size*length).scale(-1)),max.add(Vector.X.scale(size*length).scale(-1))).setMaterial(material).setEmisson(new Color(YELLOW)) // right *
		  
		};
		scene1.add(planes); 
		 Max = new Point(100.0,100.0,50.0).add(new Vector(289,210,267));
		 Min = new Point(-100.0,-100.0,-150.0).add(new Vector(255,235,211));
		 dir = Max.subtract(Min);
		 ray = new Ray(Min,new Vector(0,1,0));
		//Point{xyz=(-100.0,-100.0,-150.0)}
		//Point{xyz=(100.0,100.0,50.0)}
		//20.0
		//10
		scene1.add(new Tube(ray, 3).setMaterial(material).setEmisson(new Color(ORANGE)));
		System.out.println(trc.getGrid().collision(ray));
		//assertTrue(!trc.getGrid().collision(ray));
		System.out.println(trc.getGrid().findFirstAndLastVoxel(ray));
		scene1.add(new Sphere(Min, 50).setEmisson(new Color(YELLOW)).setMaterial(material));
			scene1.add(new Sphere(Max, 50).setEmisson(new Color(YELLOW)).setMaterial(material));
		camera1.setWriter(imageWriter) //
		.setRayTrace(trc) //
		.renderImage() //
		.writeToImage(); 


	}



	

}