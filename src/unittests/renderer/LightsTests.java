package unittests.renderer;

import org.junit.jupiter.api.Test;

import lightning.*;
import geometries.*;
import primitives.*;
import primitives.Texture.ImageCords;
import renderer.*;
import Scene.Scene;
import static java.awt.Color.*;

import java.util.List;

/**
 * Test rendering a basic image
 * 
 * @author Dan
 */
public class LightsTests {
	private Scene scene1 = new Scene("Test scene");
	private Scene scene2 = new Scene("Test scene") //
			.setAl(new AmbientLight(new Color(WHITE), new Double3(0.15)));
	private Camera camera1 = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
			.setVPSize(150, 150) //
			.setVPDistance(1000);
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
	private Geometry sphere = new Sphere(new Point(0, 0, -50), 50d) //
			.setEmisson(new Color(BLUE).reduce(2)) //
			.setMaterial(new Material().setkD(new Double3(0.5)).setkS(new Double3(0.5)).setnShininess(300));

	/**
	 * Produce a picture of a sphere lighted by a directional light
	 */
	@Test
	public void sphereDirectional() {
		scene1.geometries.add(sphere);
		scene1.lights.add(new DirectionalLight(spCL, new Vector(1, 1, -0.5)));

		ImageWriter imageWriter = new ImageWriter("lightSphereDirectional", 1500, 1500);
		camera1.setWriter(imageWriter) //
				.setRayTrace(new RayTracerBasic(scene1)) //
				.renderImage() //
				.writeToImage(); //
	}

	/**
	 * Produce a picture of a sphere lighted by a point light
	 */
	@Test
	public void spherePoint() {
		scene1.add(sphere);
		scene1.lights.add(new PointLight(spPL,spCL).setKL(0.001).setKQ(0.0002));

		ImageWriter imageWriter = new ImageWriter("lightSpherePoint", 1500, 1500);
		camera1.setWriter(imageWriter) //
				.setRayTrace(new RayTracerBasic(scene1,true).setSize(4)) //
				.renderImage() //
				.writeToImage(); //
	}
	@Test
	public void TonsOfLights() {
		scene1.geometries.add(sphere);
		List<LightSource> x = List.of(new PointLight(spPL,spCL).setKL(0.001).setKQ(0.0002),
		new PointLight(spPL.add(new Vector(0,0,86)),new Color(255,0,255)).setKL(0.001).setKQ(0.0002), 
		new PointLight(spPL.add(new Vector(0,86,0)),new Color(255,255,0)).setKL(0.001).setKQ(0.0002),
		new PointLight(spPL.add(new Vector(86,0,0)),new Color(255,255,255)).setKL(0.001).setKQ(0.0002),
		new SpotLight( new Vector(1, 1, -0.5), spPL.add(new Vector(86,0,100)),new Color(255,655,255)).setKL(0.001).setKQ(0.0001), 
		new SpotLight( new Vector(1, 1, -0.5), spPL.add(new Vector(86,100,0)),new Color(0,655,255)).setKL(0.001).setKQ(0.0001), 
		new SpotLight( new Vector(1, 1, -0.5), spPL.add(new Vector(86,100,100)),new Color(1255,655,255)).setKL(0.001).setKQ(0.0001), 
		new SpotLight( new Vector(1, 1, -0.5), spPL.add(new Vector(86,120,10)),new Color(10000,655,255)).setKL(0.001).setKQ(0.0001) 
		
		
		
		);
		scene1.lights.addAll(x);
	

		ImageWriter imageWriter = new ImageWriter("TonsOfLights", 1500, 1500);
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
		for(int i=1 ;i<61;i++){
			scene1.geometries.add(sphere);
			scene1.lights.add(new SpotLight( new Vector(1, 1, -0.5), spPL,spCL).setKL(0.001).setKQ(0.0001));
			ImageWriter imageWriter = new ImageWriter("lightSphereSpotTest"+i, 1000, 1000);
			camera1.setFocalLength(100*i ).setFocalSize( 0.001);
			camera1.setWriter(imageWriter) //
					.setRayTrace(new RayTracerBasic(scene1)) //
					.depthRenderImage() .writeToImage(String.format("Focal Length : %d , apt size : 0.001 ", 100*i), new ImageCords(50, 50)); 

		}

	
	}

	/**
	 * Produce a picture of a two triangles lighted by a directional light
	 */
	@Test
	public void trianglesDirectional() {
		scene2.add(triangle1, triangle2);
		scene2.lights.add(new DirectionalLight(trCL, trDL));

		ImageWriter imageWriter = new ImageWriter("lightTrianglesDirectional", 1500, 1500);
		camera2.setWriter(imageWriter) //
				.setRayTrace(new RayTracerBasic(scene2,true).setSize(4)) //
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