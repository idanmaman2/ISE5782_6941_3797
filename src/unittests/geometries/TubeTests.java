/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.*;

/**
 * Testing Tubes
 * 
 * @author Idan ane Eliot 
 *
 */
public class TubeTests {

	/**
	 * Test method for {@link geometries.Polygon#Polygon(primitives.Point...)}.
	 */
	@Test
	public void testConstructor() {
		// ============ Equivalence Partitions Tests ==============

		// TC01: Correct concave quadrangular with vertices in correct order
		try {
			new Tube (new Ray(new Point(0,0,0),new Vector(1,0,0)),3);
			
		} catch (IllegalArgumentException e) {
			fail("Failed constructing a correct Tube");
		}

		

		// =============== Boundary Values Tests ==================


	}

	/**
	 * Test method for {@link geometries.Tube#getNormal(primitives.Point)}.
	 */
	@Test
	public void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: There is a simple single test here
		Tube pl = new Tube(new Ray(new Point(0,0,0),new Vector(0,0,1)),1);
		assertEquals(new Vector(0,1,0), pl.getNormal(new Point(0, 1,1)), "Bad normal to Tube"); //checks for point on the tube 
	    // =============== Boundary Values Tests ==================
		assertEquals(new Vector(0,1,0), pl.getNormal(new Point(0, 1,0)), "Bad normal to Tube"); //checks for point on the tube 
	
	
	}
}
