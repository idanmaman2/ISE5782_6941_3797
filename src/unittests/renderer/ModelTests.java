package unittests.renderer;

import org.junit.jupiter.api.Test;
import org.junit.platform.console.shadow.picocli.CommandLine.HelpCommand;

import Models.AirBallon;
import Models.Sensa;
import Models.StickMen;
import Models.TAirBallon;
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
    AirBallon airballon = new AirBallon(new Point(20, -20, 20), 30d);
    Texture tx = new Texture("tx1.jpeg");
    TAirBallon Tairballon = new TAirBallon(new Point(20, -20, 20), 30d,tx);
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
    @Test
	public void SimpleTAirBallonPhoto() {
		scene1.geometries.add(Tairballon);
        scene1.lights.add(new DirectionalLight(spCL, new Vector(1, 1, -0.5)));
		ImageWriter imageWriter = new ImageWriter("TAirBallon", 1500, 1500);
		camera1.setAngle(0, new Vector(0,0,1)).setWriter(imageWriter) //
				.setRayTrace(new RayTracerBasic(scene1)) //
				.renderImage() //
				.writeToImage(); //
	}
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
            AirBallon airballon = new AirBallon(p, 30d);
            scene1.geometries.add(airballon);
            scene1.lights.add(new DirectionalLight(spCL, new Vector(1, 1, -0.5)));
            ImageWriter imageWriter = new ImageWriter("AirBallonVideo"+i, 1500, 1500);
            camera1.setAngle(0, new Vector(0,0,1)).setWriter(imageWriter) //
                    .setRayTrace(new RayTracerBasic(scene1)) //
                    .renderImage() //
                    .writeToImage(); //jpeg
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
	public void SimpleStickMenPhoto() {
        StickMen men = new StickMen(new Point(0,0, 0), 7);
		scene1.geometries.add(men);
        scene1.lights.add(new PointLight(new Point(20, -30, 20),new Color(555,555,0)).setKL(0.001).setKQ(0.0002));
        scene1.lights.add(new DirectionalLight(spCL, new Vector(1, -4, -0.5)));
		ImageWriter imageWriter = new ImageWriter("StickMen", 1500, 1500);
        Camera camera = new Camera(new Point(1000,1000,1000), new Vector(-1,-1,-1), new Vector(-1,1,0))
			.setVPSize(150, 150) //
			.setVPDistance(1000);
		camera.setWriter(imageWriter) //
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
	public void SimpleElepsoaide() {
        Camera camera1 = new Camera(new Point(50, -50, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
			.setVPSize(150, 150) //
			.setVPDistance(1000);
        for( int i =0 ; i < 360 ; i++)  {
    Scene scene1 = new Scene("Test scene");
        Geometry ele = new Elepsoaide(new Point(60, -20, 20), 25d,50d,25d).rotate(new Vector(0,0,1), i).setEmisson(new Color(BLUE).reduce(2)) //
        .setMaterial(new Material().setkD(new Double3(0.5)).setkS(new Double3(0.5)).setnShininess(300));
        scene1.geometries.add(ele);
        scene1.lights.add(new DirectionalLight(spCL, new Vector(1, 1, -0.5)));
        ImageWriter imageWriter = new ImageWriter("Elepsoaide"+i, 1500, 1500);
        camera1.setWriter(imageWriter) //
                    .setRayTrace(new RayTracerBasic(scene1)) //
                    .renderImage() //
                    .writeToImage(); //
        }
    }
        @Test
        public void SimpleSesnaPhoto() {



            Camera camera1 = new Camera(new Point(50, -50, 2000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
			.setVPSize(150, 150) //
			.setVPDistance(1000);
            Geometries ele = new Sensa(new Point(50, -20, 20), 100d) ;//
            scene1.geometries.add(ele);
            scene1.lights.add(new DirectionalLight(spCL, new Vector(1, 1, -0.5)));
            ImageWriter imageWriter = new ImageWriter("Sesna" , 1500, 1500);
            camera1.setAngle(3.5 ,new Vector(0,1,3)).setWriter(imageWriter) //
                        .setRayTrace(new RayTracerBasic(scene1)) //
                        .renderImage() //
                        .writeToImage(); //
        
    

}
@Test
public void SimpleSesnaVideo() {
  
    for(int i =0 ;i < 360*60 ; i+= 360/6){
        Camera camera1 = new Camera(new Point(-100, -50, 2000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
        .setVPSize(150, 150) //
        .setVPDistance(1000);
        Scene scene12 = new Scene("Test scene"+i);
         Sensa sen = new Sensa(new Point(50, -20, 20), 100d).rotate(i);
        scene12.geometries.add(sen);
        scene12.lights.add(new PointLight(new Point(20, -30, 20),new Color(555,555,0)).setKL(0.001).setKQ(0.0002));
        scene12.lights.add(new DirectionalLight(spCL, new Vector(1, -4, -0.5)));
        scene12.lights.add(new DirectionalLight(spCL, new Vector(1, 1, -0.5)));
        ImageWriter imageWriter = new ImageWriter("sesna"+i/(360/6), 1500, 1500);
        camera1.setAngle(0 ,new Vector(1,0,0)).setWriter(imageWriter) //
        .setRayTrace(new RayTracerBasic(scene12)) //
        .renderImage() //
        .writeToImage(); //

    }
  
}



}



