package unittests.renderer;

import org.junit.jupiter.api.Test;

import lightning.*;
import geometries.*;
import primitives.*;
import renderer.*;
import Scene.Scene;
import Scene.TextureScene;

import static java.awt.Color.*;

/**
 * Test rendering a basic image
 * 
 * @author Dan
 */
public class LightsTests {
	private Scene scene1 = new Scene("Test scene");
	private Scene scene2 = new Scene("Test scene") //
			.setAl(new AmbientLight(new Color(255,0,0), new Double3(0.15)));
	private Camera camera1 = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
			.setVPSize(550, 550); //;
			private Camera camera22 = new Camera(new Point(-1000, -10000, 1000), new Vector(0, 0, -2), new Vector(0, -1, 0)) //
			.setVPSize(550, 550) //
			.setVPDistance(100);
	private Camera camera2 = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
			.setVPSize(200, 200) //
			.setVPDistance(1000);

	private Point[] p = { // The Triangles' vertices:
			new Point(-110, -110, -150), // the shared left-bottom
			new Point(80, 100, -150), // the shared right-top
			new Point(110, -110, -150), // the right-bottom
			new Point(-75, 85, 0) }; // the left-top
	private Point trPL = new Point(50, 30, -100); // Triangles test Position of Light
	private Point spPL = new Point(-50, -50, 25); // Sphere test Position of Light
	private Color trCL = new Color(800, 500, 250); // Triangles test Color of Light
	private Color spCL = new Color(800, 500, 0); // Sphere test Color of Light
	private Vector trDL = new Vector(-2, -2, -2); // Triangles test Direction of Light
	private Material material = new Material().setkD(new Double3(0.5)).setkS(new Double3(0.5)).setnShininess(300);
	private Geometry triangle1 = new Triangle(p[0], p[1], p[2]).setMaterial(material);
	private Geometry triangle2 = new Triangle(p[0], p[1], p[3]).setMaterial(material);
	private Geometry sphere = new Sphere(new Point(0, 50000, -50), 500d) //
			.setEmisson(new Color(BLUE).reduce(2)) //
			.setMaterial(new Material().setkD(new Double3(0.5)).setkS(new Double3(0.5)).setnShininess(300));
	Texture tx = new Texture("tx6.jpg");
	Texture tx2 = new Texture("tx12.jpg");
	Texture tx3 = new Texture("tx12.jpg");
	Texture bg = new Texture("tx12.jpg");
	private Scene scene3 = new TextureScene("Test scene",bg) //
	.setAl(new AmbientLight(new Color(255,0,0), new Double3(0.15)));

	private Geometry Tsphere = new TSphere(new Point(2000000, -180000000, -200000), 1500d,tx) //
	.setEmisson(new Color(BLUE).reduce(2)) //
	.setMaterial(new Material().setkD(new Double3(0.5)).setkS(new Double3(0.5)).setnShininess(30));
	private Geometry Tsphere2 = new TSphere(new Point(-1500, 42000, -42000), 5000d,tx3) //
	.setEmisson(new Color(BLUE).reduce(2)) //
	.setMaterial(new Material().setkD(new Double3(0.5)).setkS(new Double3(0.5)).setnShininess(30));
	
Geometry tplane= new TPlane(new Point(5500, 0, 0) , new Vector(1,-7,1),tx2).setEmisson(new Color(255,255,0));
	

	/**
	 * Produce a picture of a sphere lighted by a directional light
	 */
	@Test
	public void sphereDirectional() {
		scene1.geometries.add(sphere);
		scene1.lights.add(new DirectionalLight(spCL, new Vector(1, 1, -0.5)));

		ImageWriter imageWriter = new ImageWriter("lightSphereDirectional", 5500, 5500);
		camera1.setWriter(imageWriter) //
				.setRayTrace(new RayTracerBasic(scene1)) //
				.renderImage() //
				.writeToImage(); //
	}

	@Test
	public void sphereDirectionalTEXTURE2() {
		scene3.geometries.add(Tsphere2);
		scene3.geometries.add(sphere,Tsphere,tplane,Tsphere2);
		scene3.lights.add(new PointLight(new Point(9500, 5000, 0),new Color(255,0,0)).setKL(0.9).setKQ(0.9));
		scene3.lights.add(new SpotLight(new Vector(-1,7,-1), new Point(3500, 0, 0),new Color(255,0,0)).setKL(0.001).setKQ(0.0001));
		ImageWriter imageWriter = new ImageWriter("lightSphereDirectionalTEXTURE2", 1000, 1000);
		camera22.setWriter(imageWriter) //
				.setRayTrace(new RayTracerBasic(scene3)) //
				.renderImage() //
				.writeToImage(); //
	}
	@Test
	public void sphereDirectionalTEXTURE() {

		scene3.geometries.add(sphere,Tsphere,tplane);
		scene3.lights.add(new PointLight(new Point(1500, 5000, 0),new Color(12,0,0)).setKL(0.0009).setKQ(0.0009));
		for(int i=0 ; i< 24 ;i++){
			ImageWriter imageWriter = new ImageWriter(String.format("%dlightSphereDirectionalTEXTURE3",i), 1500, 1500);
			camera22.setAngle(30+i*0.5, new Vector(0,0,1)).setWriter(imageWriter) //
					.setRayTrace(new RayTracerBasic(scene3)) //
					.renderImage() //
					.writeToImage(); //
		
		}
		for(int i=0 ; i< 240 ;i++){
			ImageWriter imageWriter = new ImageWriter(String.format("%dlightSphereDirectionalTEXTURE3",i), 1500, 1500);
			camera22.setAngle(i, new Vector(0,0,0.0025)).setWriter(imageWriter) //
					.setRayTrace(new RayTracerBasic(scene3)) //
					.renderImage() //
					.writeToImage(); //
		
		}
		for(int i=240 ; i< 580 ;i++){
			ImageWriter imageWriter = new ImageWriter(String.format("%dlightSphereDirectionalTEXTURE3",i), 1500, 1500);
			camera22.setAngle(i, new Vector(0.0025,0,0)).setWriter(imageWriter) //
					.setRayTrace(new RayTracerBasic(scene3)) //
					.renderImage() //
					.writeToImage(); //
		
		}
		for(int i=580 ; i< 720 ;i++){
			ImageWriter imageWriter = new ImageWriter(String.format("%dlightSphereDirectionalTEXTURE3",i), 1500, 1500);
			camera22.setAngle(i, new Vector(0.0025,0.0025,0)).setWriter(imageWriter) //
					.setRayTrace(new RayTracerBasic(scene3)) //
					.renderImage() //
					.writeToImage(); //
		
		}

	}





	/**
	 * Produce a picture of a sphere lighted by a point light
	 */
	@Test
	public void spherePoint() {
		scene1.geometries.add(sphere);
		scene1.lights.add(new PointLight(spPL,spCL).setKL(0.001).setKQ(0.0002));
		ImageWriter imageWriter = new ImageWriter("lightSpherePoint", 1500, 1500);
		camera1.setWriter(imageWriter) //
				.setRayTrace(new RayTracerBasic(scene1)) //
				.renderImage() //
				.writeToImage(); //
	}

	/**
	 * Produce a picture of a sphere lighted by a spot light
	 */
	@Test
	public void sphereSpot() {
		scene1.geometries.add(sphere);
		scene1.lights.add(new SpotLight( new Vector(1, 1, -0.5), spPL,spCL).setKL(0.001).setKQ(0.0001));

		ImageWriter imageWriter = new ImageWriter("lightSphereSpot", 1500, 1500);
		camera1.setWriter(imageWriter) //
				.setRayTrace(new RayTracerBasic(scene1)) //
				.renderImage() //
				.writeToImage(); //
	}

	/**
	 * Produce a picture of a two triangles lighted by a directional light
	 */
	@Test
	public void trianglesDirectional() {
		scene2.geometries.add(triangle1, triangle2);
		scene2.lights.add(new DirectionalLight(trCL, trDL));

		ImageWriter imageWriter = new ImageWriter("lightTrianglesDirectional", 1500, 1500);
		camera2.setWriter(imageWriter) //
				.setRayTrace(new RayTracerBasic(scene2)) //
				.renderImage() //
				.writeToImage(); //
	}

	/**
	 * Produce a picture of a two triangles lighted by a point light
	 */
	@Test
	public void trianglesPoint() {
		scene2.geometries.add(triangle1, triangle2);
		scene2.lights.add(new PointLight(trPL,trCL).setKL(0.001).setKQ(0.0002));

		ImageWriter imageWriter = new ImageWriter("lightTrianglesPoint", 1500, 1500);
		camera2.setWriter(imageWriter) //
				.setRayTrace(new RayTracerBasic(scene2)) //
				.renderImage() //
				.writeToImage(); //
	}

	/**
	 * Produce a picture of a two triangles lighted by a spot light
	 */
	@Test
	public void trianglesSpot() {
		scene2.geometries.add(triangle1, triangle2);
		scene2.lights.add(new SpotLight(trDL,trPL,trCL).setKL(0.001).setKQ(0.0001));

		ImageWriter imageWriter = new ImageWriter("lightTrianglesSpot", 1500, 1500);
		camera2.setWriter(imageWriter) //
				.setRayTrace(new RayTracerBasic(scene2)) //
				.renderImage() //
				.writeToImage(); //
	}

}