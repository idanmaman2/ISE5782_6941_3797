package unittests.renderer;

import org.junit.jupiter.api.Test;

import Models.AirBallon;
import Models.hellicopter;
import Physics.Physics;
import lightning.*;
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
public class ModelTests {
	private Scene scene1 = new Scene("Test scene");
	private Camera camera1 = new Camera(new Point(50, -50, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
			.setVPSize(150, 150) //
			.setVPDistance(1000);
            private Color spCL = new Color(800, 500, 0); // Sphere test Color of Light

    private Point spPL = new Point(-50, -50, 25); 
    AirBallon airballon = new AirBallon(new Point(20, -20, 20), 30d, new Color(255,0,0),new Color(255,0,0),new Color(255,0,0),new Color(255,0,0));
    /**
     * Produce a picture of an AirBallon
     */
	@Test
	public void SimpleAirBallonPhoto() {
		scene1.geometries.add(airballon);
        scene1.lights.add(new DirectionalLight(spCL, new Vector(1, 1, -0.5)));
		ImageWriter imageWriter = new ImageWriter("AirBallon", 1500, 1500);
		camera1.setAngle(0, new Vector(0,0,1)).setWriter(imageWriter) //
				.setRayTrace(new RayTracerBasic(scene1)) //
				.renderImage() //
				.writeToImage(); //
	}
    /**
     * Produce a Video of an AirBallon simple animation
     */
    @Test
	public void SimpleAirBallonVideo() {
        Point p = new Point(100, -100, -100) ;
        Physics py = new Physics(); 
        for(int i = 0 ; i < 58 ; i++){
            Camera camera1 = new Camera(new Point(50, -50, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
			.setVPSize(150, 150).setAngle(30/58 * i, new Vector(0,-1,0))
			.setVPDistance(1000);
             Scene scene1 = new Scene("Test scene");
            p = new Physics().moveWithAccaliration(p,new Double3(0.2, -0.4, -1),new Double3(-1.5, 4.5, -5.0),i);
            AirBallon airballon = new AirBallon(p, 30d,new Color(255,0,0),new Color(255,0,0),new Color(255,0,0),new Color(255,0,0));
            scene1.geometries.add(airballon);
            scene1.lights.add(new DirectionalLight(spCL, new Vector(1, 1, -0.5)));
            ImageWriter imageWriter = new ImageWriter("AirBallonVideo"+i, 1500, 1500);
            camera1.setAngle(0, new Vector(0,0,1)).setWriter(imageWriter) //
                    .setRayTrace(new RayTracerBasic(scene1)) //
                    .renderImage() //
                    .writeToImage(); //
        }
      
	}
    /**
     * Produce a Video of an Elepsoaide simple animation
     */
    @Test
	public void SimpleElepsoaide() {
        Geometry ele = new Elepsoaide(new Point(20, -20, 20), 50d,30d,30d).setEmisson(new Color(BLUE).reduce(2)) //
        .setMaterial(new Material().setkD(new Double3(0.5)).setkS(new Double3(0.5)).setnShininess(300));
        scene1.geometries.add(ele);
        scene1.lights.add(new DirectionalLight(spCL, new Vector(0, 0, 1)));
        scene1.lights.add(new PointLight(new Point(20, -20, 80), spCL));
        ImageWriter imageWriter = new ImageWriter("Elepsoaide" , 1500, 1500);
        camera1.setWriter(imageWriter) //
                    .setRayTrace(new RayTracerBasic(scene1)) //
                    .renderImage() //
                    .writeToImage(); //
        }



        @Test
        public void SimpleHeliVideo() {
          
            Point x0 = new Point(0,0, 20);
            for(int i =0 ;i < 360*60 ; i+= 360/6){
                Camera camera1 = new Camera(new Point(50, -50, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(150, 150) //
                .setVPDistance(1000);
                Scene scene12 = new Scene("Test scene"+i);
                x0 = new Physics().moveWithAccaliration(x0, new Double3(-0.0002, 0, -0.00004), new Double3(-0.002, -0.002, -0.002), i/60);
                 hellicopter heli = new hellicopter(x0, 3d).rotateFrontRotor(i, i);
                scene12.geometries.add(heli);
                scene12.lights.add(new PointLight(new Point(20, -30, 20),new Color(555,555,0)).setKL(0.001).setKQ(0.0002));
                scene12.lights.add(new DirectionalLight(spCL, new Vector(1, -4, -0.5)));
                ImageWriter imageWriter = new ImageWriter("heli"+i/(360/6), 1500, 1500);
                camera1.setAngle(-20, new Vector(0,-0.2,1)).setWriter(imageWriter) //
                .setRayTrace(new RayTracerBasic(scene12)) //
                .renderImage() //
                .writeToImage(); //
    
            }
          
        }

        @Test
        public void SimpleHelihoto() {
            hellicopter heli = new hellicopter(new Point(0,0, 20), 3d).rotateFrontRotor(25, 45);
            scene1.geometries.add(heli);
            scene1.lights.add(new PointLight(new Point(20, -30, 20),new Color(555,555,0)).setKL(0.001).setKQ(0.0002));
            scene1.lights.add(new DirectionalLight(spCL, new Vector(1, -4, -0.5)));
            ImageWriter imageWriter = new ImageWriter("heli", 1500, 1500);
            camera1.setAngle(-20, new Vector(0,-0.2,1)).setWriter(imageWriter) //
                    .setRayTrace(new RayTracerBasic(scene1)) //
                    .renderImage() //
                    .writeToImage(); //
        }

        @Test
        public void BonusPhoto(){
            AirBallon airballon = new AirBallon(new Point(0, -10, 0), 10d,new Color(255,255,0),new Color(165,42,42),new Color(177,156,217),new Color(177,156,217));
            hellicopter heli = new hellicopter(new Point(0,-90, 20), 3d).rotateFrontRotor(25, 45);
            scene1.geometries.add(heli,airballon);
            scene1.lights.add(new PointLight(new Point(20, -30, 20),new Color(555,555,0)).setKL(0.001).setKQ(0.0002));
            scene1.lights.add(new PointLight(new Point(0,-150,0),new Color(555,555,0)).setKL(0.001).setKQ(0.0002));
            scene1.lights.add(new DirectionalLight(new Color(255,255,0), new Vector(1, -4, -0.5)));
            ImageWriter imageWriter = new ImageWriter("BonusP06", 1500, 1500);
            camera1.setWriter(imageWriter) //
                    .setRayTrace(new RayTracerBasic(scene1)) //
                    .renderImage() //
                    .writeToImage(); //


        }


      
}



