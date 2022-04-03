/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.*;

/**
 * Testing Cylinders
 * 
 * @author Idan ane Elliot
 *
 */
public class CylinderTests {

	/**
	 * Test method for {@link geometries.Polygon#Polygon(primitives.Point...)}.
	 */
	@Test
	public void testConstructor() {
		// ============ Equivalence Partitions Tests ==============

		// TC01: Correct concave quadrangular with vertices in correct order
		try {
			new Cylinder(new Ray(new Point(0, 0,0), new Vector(0,0,1)),1,1);
		} catch (IllegalArgumentException e) {
			fail("Failed constructing a correct polygon");
		}



		// =============== Boundary Values Tests ==================


	}

	/**
	 * Test method for {@link geometries.Polygon#getNormal(primitives.Point)}.
	 */
	@Test
	public void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: There is a simple single test here
		Cylinder pl =new Cylinder(new Ray(new Point(0,0,0),new Vector(0,0,1)), 1, 2 ) ;
//checks if this is in the side
        assertEquals(new Vector(0, 0, 1), pl.getNormal(new Point(0, 1, 0)), "Bad normal to Cylinder");
//checks if this is in the down base
        assertEquals(new Vector(0, 0, 1), pl.getNormal(new Point(0, 1, 2)), "Bad normal to Cylinder");

//checks if this is in the up base
        assertEquals(new Vector(0, 1, 0), pl.getNormal(new Point(0, 1, 1)), "Bad normal to Cylinder");


		// =============== Boundary Values Tests ==================
//checks if this is in the down base center
        assertEquals(new Vector(0, 0, 1), pl.getNormal(new Point(0, 0, 0)), "Bad normal to Cylinder");
		//checks if this is in the up base center
		assertEquals(new Vector(0, 0, 1), pl.getNormal(new Point(0, 0, 2)), "Bad normal to Cylinder");
        
	}
}