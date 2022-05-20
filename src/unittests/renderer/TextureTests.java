package unittests.renderer;

import org.junit.jupiter.api.Test;

import lightning.AmbientLight;
import lightning.SpotLight;
import geometries.*;
import primitives.*;
import renderer.*;
import Scene.Scene;
import static java.awt.Color.*;

/**
 * Test rendering a basic image
 * 
 * @author Dan
 */
public class TextureTests {
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

    @Test
	public void basicTexturedPlane() {
		Texture tx = new Texture("wood.jpg");
		Texture tx2 = new Texture("tx3.jpeg");
		scene2.geometries.add(new TPlane(new Point(800,-100,-2500),new Vector(0,1,0),tx).setEmisson(new Color(BLUE).reduce(2)) //
		.setMaterial(new Material().setkD(new Double3(0.5)).setkS(new Double3(0.5)).setnShininess(300)));
		scene2.geometries.add(new TPlane(new Point(800,-100,-25000),new Vector(0,0,1),tx2).setEmisson(new Color(BLUE).reduce(2)) //
		.setMaterial(new Material().setkD(new Double3(0.5)).setkS(new Double3(0.5)).setnShininess(300)));
		scene2.lights.add(new SpotLight(trDL,trPL,trCL).setKL(0.001).setKQ(0.0001));
		scene2.geometries.add(new TSphere(new Point(50,0,-300), 30, tx2));
		scene2.geometries.add(new TSphere(new Point(10,20,-300), 60, tx2));
		scene2.geometries.add(new TSphere(new Point(80,-20,-300), 30, tx2));
		ImageWriter imageWriter = new ImageWriter("WoodTexture", 1500, 1500);
		camera2.setWriter(imageWriter) //
				.setRayTrace(new RayTracerBasic(scene2)) //
				.renderImage() //
				.writeToImage(); //


	}

	
}
