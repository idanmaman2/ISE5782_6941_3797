package unittests.renderer;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import renderer.*;
import primitives.*;


/**
 * Testing Camera Class
 * 
 * @author Dan
 *
 */
class ImageWriterTest {
	static final Point ZERO_POINT = new Point(0, 0, 0);




	/**
	 * Test method for
	 * {@link elements.Camera#constructRay(int, int, int, int)}.
	 */
	@Test
	void ImageWriterTest() {
        
        Camera camera = new Camera(new Point(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0))
        .setVPSize(16, 10)
        .setVPDistance(10);
ImageWriter imageWriter = new ImageWriter("Test32", 800, 500);
Color background = new Color(255,255,0);
Color grid = Color.BLACK;
double squreLength = imageWriter.getNx()/16;
for (int i = 0; i < imageWriter.getNy(); i++) {
    for (int j = 0; j < imageWriter.getNx(); j++) {
        if (j % squreLength < 2 ||
                i % squreLength < 2)
            imageWriter.writePixel(j, i, grid);
        else
            imageWriter.writePixel(j, i, background);
    }
}
imageWriter.writeToImage();
}

}
