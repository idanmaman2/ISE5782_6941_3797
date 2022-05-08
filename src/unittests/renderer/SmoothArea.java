package unittests.renderer;

import org.junit.jupiter.api.Test;

import Models.TAirBallon;
import Physics.Physics;
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
public class SmoothArea {

    private Scene scene1 = new Scene("Test scene");

	private Camera camera1 = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
			.setVPSize(150, 150) //
			.setVPDistance(1000);


	private Point spPL = new Point(-50, -50, 25); // Sphere test Position of Light
	private Color spCL = new Color(800, 500, 0); // Sphere test Color of Light
	private Geometry sphere = new Sphere(new Point(0, 0, -50), 50d) //
			.setEmisson(new Color(255,255,0).reduce(2)) //
			.setMaterial(new Material().setkD(new Double3(0.5)).setkS(new Double3(0.5)).setnShininess(300));


private Geometry sphere2 = new Sphere(new Point(0, 0, -5), 10d) //
			.setEmisson(new Color(blue).reduce(2)) //
			.setMaterial(new Material().setkD(new Double3(0.5)).setkS(new Double3(0.5)).setnShininess(300));        

	@Test
	public void BeforChanges() {
		scene1.geometries.add(sphere,sphere2);
		scene1.lights.add(new SpotLight( new Vector(1, 1, -0.5), spPL,spCL).setKL(0.001).setKQ(0.0001));

		ImageWriter imageWriter = new ImageWriter("NotSmooth", 1500, 1500);
		camera1.setWriter(imageWriter) //
				.setRayTrace(new RayTracerBasic(scene1)) //
				.renderImage() //
				.writeToImage(); //
	}
    @Test
	public void AfterChanges() {
        MinGeometries mg = new MinGeometries();
        mg.add(sphere,sphere2);
		scene1.geometries.add(mg);
		scene1.lights.add(new SpotLight( new Vector(1, 1, -0.5), spPL,spCL).setKL(0.001).setKQ(0.0001));

		ImageWriter imageWriter = new ImageWriter("HopefullySmooth", 1500, 1500);
		camera1.setWriter(imageWriter) //
				.setRayTrace(new RayTracerBasic(scene1)) //
				.renderImage() //
				.writeToImage(); //
	}

}