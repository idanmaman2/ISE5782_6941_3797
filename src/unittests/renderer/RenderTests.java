package unittests.renderer;

import org.junit.jupiter.api.Test;

import lightning.AmbientLight;
import geometries.*;
import primitives.*;
import renderer.*;
import Scene.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;
/**
 * Test rendering a basic image
 * 
 * @author Dan
 */
public class RenderTests {

	/**
	 * Produce a scene with basic 3D model and render it into a png image with a
	 * grid
	 */
	@Test
	public void basicRenderTwoColorTest() {
		Scene scene = new Scene("Test scene")//
				.setAl(new AmbientLight(new Color(255, 191, 191), //
						                          new Double3(1,1,1))) //
				.setBg(new Color(75, 127, 90));

		scene.geometries.add(new Sphere( new Point(0, 0, -100),50),
				new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100), new Point(-100, 100, -100)), // up
																													// left
				new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100), new Point(-100, -100, -100)), // down
																														// left
				new Triangle(new Point(100, 0, -100), new Point(0, -100, -100), new Point(100, -100, -100))); // down
																													// right
		Camera camera = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPDistance(100) //
				.setVPSize(500, 500) //
				.setWriter(new ImageWriter("base render test", 1000, 1000))				
				.setRayTrace(new RayTracerBasic(scene));

		camera.renderImage();
		camera.printGrid(100, new Color(java.awt.Color.YELLOW));
		camera.writeToImage();
	}


	/**
	 * Test for XML based scene - for bonus
	 */
	@Test
	public void basicRenderXml() {
		xmlReader xml = new  xmlReader("basicRenderTestTwoColors");
		Scene scene = new Scene("XML Test scene").setAl(xml.getAmbient()).setBg(xml.getBG());
		// enter XML file name and parse from XML file into scene object
		// ...
		scene.geometries.add(xml.getGeometries());
		Camera camera = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPDistance(100) //
				.setVPSize(500, 500)
				.setWriter(new ImageWriter("xml render test", 1000, 1000))
				.setRayTrace(new RayTracerBasic(scene));
		camera.renderImage();
		camera.printGrid(100, new Color(java.awt.Color.YELLOW));
		camera.writeToImage();
	}
}


