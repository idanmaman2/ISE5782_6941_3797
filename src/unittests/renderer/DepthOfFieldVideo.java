package unittests.renderer;

import org.junit.jupiter.api.Test;

import OBJParser.ObjParser;
import lightning.AmbientLight;
import lightning.LightSource;
import lightning.PointLight;
import lightning.SpotLight;
import geometries.*;
import primitives.*;
import primitives.Texture.ImageCords;
import renderer.*;
import Scene.Scene;
import Scene.TextureScene;

import static java.awt.Color.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Test rendering a basic image
 * 
 * @author Idan and Elliot
 */

/**
 * basis of different textures of solid, light changes by the texture
 */
public class DepthOfFieldVideo {
	private Scene scene1 = new Scene("Test scene");
	
	private Camera camera1 = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
			.setVPSize(150, 150) //
			.setVPDistance(1000);
	private Camera camera2 = new Camera(new Point(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
			.setVPSize(200, 200) //
			.setVPDistance(1000);
			Texture tx3 = new Texture("tx2.jpeg");
	private Point[] p = { // The Triangles' vertices:
			new Point(-500, -110, -150), // the shared left-bottom
			new Point(80, 100, -150), // the shared right-top
			new Point(110, -110, -150), // the right-bottom
			new Point(-75, 85, 0) }; // the left-top
	private Point trPL = new Point(50, 30, -100); // Triangles test Position of Li[](../../../images/WoodTexture.png)ght
	private Point spPL = new Point(-50, -50, 25); // Sphere test Position of Light
	private Color trCL = new Color(800, 500, 250); // Triangles test Color of Light
	private Color spCL = new Color(800, 500, 0); // Sphere test Color of Light
	private Vector trDL = new Vector(-2, -2, -2); // Triangles test Direction of Light
	private Material material = new Material().setkD(new Double3(0.5)).setkS(new Double3(0.5)).setnShininess(300);
	private Geometry triangle1 = new TTriangle(p[0], p[1], p[2],tx3).setMaterial(material);
	private Geometry triangle2 = new TTriangle(p[0], p[1], p[3],tx3).setMaterial(material);
	private Geometry sphere = new Sphere(new Point(0, 0, -50), 50d) //
			.setEmisson(new Color(BLUE).reduce(2)) //
			.setMaterial(new Material().setkD(new Double3(0.5)).setkS(new Double3(0.5)).setnShininess(300));


	/**
	 * basic plane texture
	 * wood, tx3 and others
	 */
    @Test
	public void DepthOfFieldVideoTest() throws FileNotFoundException, IOException {
        Texture tx = new Texture("wood.jpeg");
		Texture tx2 = new Texture("tx3.jpeg");
	for(int i=1;i<61;i++){
         Scene scene2 = new Scene("Test scene") //
        .setAl(new AmbientLight(new Color(WHITE), new Double3(0.15)));
     
		scene2.geometries.add(new TPlane(new Point(800,-100,-2500),new Vector(0,1,0),tx).SetAngle(30).setEmisson(new Color(BLUE).reduce(2)) //
		.setMaterial(new Material().setkD(new Double3(0.5)).setkS(new Double3(0.5)).setnShininess(300).setKR(new Double3(0.07)).setKT(new Double3(0.0015))));
		scene2.geometries.add(new TPlane(new Point(800,-100,-25000),new Vector(0,0,1),tx2).setEmisson(new Color(BLUE).reduce(2)));
		
		scene2.geometries.add(new TSphere(new Point(100,200,7000), 150, tx2).setMaterial(new Material().setkD(new Double3(0.5)).setkS(new Double3(0.5)).setnShininess(200).setKR(new Double3(0.015)).setKT(new Double3(0.25))));
		scene2.geometries.add(new TSphere(new Point(-50,100,5000), 150, tx2).setMaterial(new Material().setkD(new Double3(0.5)).setkS(new Double3(0.5)).setnShininess(100).setKR(new Double3(0.015)).setKT(new Double3(0.25))));
		scene2.geometries.add(new TSphere(new Point(50,0,-5000), 150, tx2).setMaterial(new Material().setkD(new Double3(0.5)).setkS(new Double3(0.5)).setnShininess(100).setKR(new Double3(0.015)).setKT(new Double3(0.25))));
		
		List<LightSource> x = List.of(new PointLight(spPL,spCL).setKL(0.001).setKQ(0.0002),
		new PointLight(spPL.add(new Vector(300,200,5050)),new Color(255,0,255)).setKL(0.001).setKQ(0.0002), 
		new PointLight(spPL.add(new Vector(300,200,5050)),new Color(255,255,0)).setKL(0.001).setKQ(0.0002),
		new PointLight(spPL.add(new Vector(300,200,5050)),new Color(255,255,255)).setKL(0.001).setKQ(0.0002),
		new SpotLight( new Vector(1, 1, -0.5), spPL.add(new Vector(86,0,100)),new Color(255,655,255)).setKL(0.001).setKQ(0.0001), 
		new SpotLight( new Vector(1, 1, -0.5), spPL.add(new Vector(86,100,0)),new Color(0,655,255)).setKL(0.001).setKQ(0.0001), 
		new SpotLight( new Vector(1, 1, -0.5), spPL.add(new Vector(86,100,100)),new Color(1255,655,255)).setKL(0.001).setKQ(0.0001), 
		new SpotLight( new Vector(1, 1, -0.5), spPL.add(new Vector(86,120,10)),new Color(10000,655,255)).setKL(0.001).setKQ(0.0001)); 
	/*	{
			Point pt = new Point(-100,-100,8000);
			for(int t=0 ;t<500 ; t++){
				Point ptTmp = pt.add(new Vector(50*Math.cos(Math.toRadians(t)),t/2,50*Math.sin(Math.toRadians(t))));
				scene2.geometries.add(new TSphere(ptTmp, 4, tx2).setMaterial(new Material().setkD(new Double3(0.5)).setkS(new Double3(0.5)).setnShininess(300).setKR(new Double3(0.015)).setKT(new Double3(0.25))));
			}
		} */
		
		

		ImageWriter imageWriter = new ImageWriter("DepthOFieldPart"+i, 1000, 1000);	
		scene2.lights.addAll(x);
		
		
		camera2.setFocalLength(5500).setFocalSize(i/1.5d);
		camera2.setWriter(imageWriter) //
				.setRayTrace(new RayTracerBasic(scene2)) //
				.depthRenderImage() //
				.writeToImage(String.format("apt length :5100 , apt size: %f ",i/1.5d), new ImageCords(50,50)) ; //


    }
		
	}

	
}