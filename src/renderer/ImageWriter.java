package renderer;

import primitives.Color;
import primitives.Texture.ImageCords;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.*;

/**
 * Image writer class combines accumulation of pixel color matrix and finally
 * producing a non-optimized jpeg image from this matrix. The class although is
 * responsible of holding image related parameters of View Plane - pixel matrix
 * size and resolution
 * 
 * @author Dan
 */
public class ImageWriter {
	private int nX;
	private int nY;


	private static final String FOLDER_PATH = "/Users/idang/Desktop/ISE5782_6941_3797/images";

	private BufferedImage image;
	private String imageName;
	
	private Logger logger = Logger.getLogger("ImageWriter");

	// ***************** Constructors ********************** //
	/**
	 * Image Writer constructor accepting image name and View Plane parameters,
	 * @param imageName the name of jpeg file
	 * @param nX        amount of pixels by Width
	 * @param nY        amount of pixels by height
	 */
	public ImageWriter(String imageName, int nX, int nY) {
		this.imageName = imageName;
		this.nX = nX;
		this.nY = nY;

		image = new BufferedImage(nX, nY, BufferedImage.TYPE_INT_RGB);
	}

	// ***************** Getters/Setters ********************** //
	/**
	 * View Plane Y axis resolution
	 * 
	 * @return the amount of vertical pixels
	 */
	public int getNy() {
		return nY;
	}

	/**
	 * View Plane X axis resolution
	 * 
	 * @return the amount of horizontal pixels
	 */
	public int getNx() {
		return nX;
	}

	// ***************** Operations ******************** //

	/**
	 * Function writeToImage produces unoptimized png file of the image according to
	 * pixel color matrix in the directory of the project
	 */
	public void writeToImage() {
		try {
			File file = new File(FOLDER_PATH + '/' + imageName + ".png");
			ImageIO.write(image, "png", file);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "I/O error", e);
			throw new IllegalStateException("I/O error - may be missing directory " + FOLDER_PATH, e);
		}
	}
	public void writeToImage(String text , ImageCords cords ) {
		try {
			File file = new File(FOLDER_PATH + '/' + imageName + ".png");
			//get the Graphics object
			Graphics g = image.getGraphics();
			//set font
			g.setFont(g.getFont().deriveFont(25f));
			g.drawString(text, (int)cords.getX(),(int)cords.getY());
			g.dispose();
			//write the image
			
			ImageIO.write(image, "png", file);

		}
		
		catch (IOException e) {
			logger.log(Level.SEVERE, "I/O error", e);
			throw new IllegalStateException("I/O error - may be missing directory " + FOLDER_PATH, e);
		}
	}

	/**
	 * The function writePixel writes a color of a specific pixel into pixel color
	 * matrix
	 * 
	 * @param xIndex X axis index of the pixel
	 * @param yIndex Y axis index of the pixel
	 * @param color  final color of the pixel
	 */
	public void writePixel(int xIndex, int yIndex, Color color) {
		image.setRGB(xIndex, yIndex, color.getColor().getRGB());
	}

}
