/**
 * 
 */
package unittests.renderer;

import org.junit.jupiter.api.Test;

import static java.awt.Color.*;

import renderer.ImageWriter;
import lightning.*;
import geometries.*;
import primitives.*;
import renderer.*;
import Scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 * 
 * @author dzilb
 */
public class ReflectionRefractionTests {
	private Scene scene = new Scene("Test scene");

	/**
	 * Produce a picture of a sphere lighted by a spot light
	 */
	@Test
	public void twoSpheres() {
		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(150, 150).setVPDistance(1000);

		scene.add( //
				new Sphere(new Point(0, 0, -50), 50d).setEmisson(new Color(BLUE)) //
						.setMaterial(new Material().setkD(new Double3(0.4)).setkS(new Double3(0.3)).setnShininess(100).setKT(new Double3(0.3))),
				new Sphere(new Point(0, 0, -50), 25d).setEmisson(new Color(RED)) //
						.setMaterial(new Material().setkD(new Double3(0.5)).setkS(new Double3(0.5)).setnShininess(100)));
		scene.lights.add( //
				new SpotLight(new Vector(-1, -1, -2), new Point(-100, -100, 500), new Color(1000, 600, 0)) //
						.setKL(0.0004).setKQ(0.0000006));

		camera.setWriter(new ImageWriter("refractionTwoSpheres", 500, 500)).
				setRayTrace(new RayTracerBasic(scene,true).setSize(10)) //
				.renderImage() //
				.writeToImage();
	}

	/**
	 * Produce a picture of a sphere lighted by a spot light
	 */
	@Test
	public void twoSpheresOnMirrors() {
		Camera camera = new Camera(new Point(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(2500, 2500).setVPDistance(10000); //

		scene.setAl(new AmbientLight(new Color(255, 255, 255), new Double3(0.1)));

		scene.geometries.add( //
				new Sphere(new Point(-950, -900, -1000), 400d).setEmisson(new Color(0, 0, 100)) //
						.setMaterial(new Material().setkD(new Double3(0.25)).setkS(new Double3(0.25)).setnShininess(20).setKT(new Double3(0.5))),
				new Sphere(new Point(-950, -900, -1000), 200d).setEmisson(new Color(100, 20, 20)) //
						.setMaterial(new Material().setkD(new Double3(0.25)).setkS(new Double3(0.25)).setnShininess(20)),
				new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500), new Point(670, 670, 3000)) //
						.setEmisson(new Color(20, 20, 20)) //
						.setMaterial(new Material().setKR(new Double3(1))),
				new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
						new Point(-1500, -1500, -2000)) //
						.setEmisson(new Color(20, 20, 20)) //
						.setMaterial(new Material().setKR(new Double3(0.5))));

		scene.lights.add(new SpotLight(new Vector(-1, -1, -4), new Point(-750, -750, -150), new Color(1020, 400, 400)) //
				.setKL(0.00001).setKQ(0.000005));

		ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
		camera.setWriter(imageWriter) //
				.setRayTrace(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage();
	}

	/**
	 * Produce a picture of a two triangles lighted by a spot light with a partially
	 * transparent Sphere producing partial shadow
	 */
	@Test
	public void trianglesTransparentSphere() {
		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(200, 200).setVPDistance(1000);

		scene.setAl(new AmbientLight(new Color(WHITE), new Double3(0.15)));

		scene.geometries.add( //
				new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150)) //
						.setMaterial(new Material().setkD(new Double3(0.5)).setkS(new Double3(0.5)).setnShininess(60)), //
				new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
						.setMaterial(new Material().setkD(new Double3(0.5)).setkS(new Double3(0.5)).setnShininess(60)), //
				new Sphere(new Point(60, 50, -50), 30d).setEmisson(new Color(BLUE)) //
						.setMaterial(new Material().setkD(new Double3(0.2)).setkS(new Double3(0.2)).setnShininess(30).setKT(new Double3(0.6))));

		scene.lights.add(new SpotLight(new Vector(0, 0, -1), new Point(60, 50, 0), new Color(700, 400, 400)) //
				.setKL(4E-5).setKQ(2E-7));

		ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
		camera.setWriter(imageWriter) //
				.setRayTrace(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage();
	}
}