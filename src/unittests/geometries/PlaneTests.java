/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.*;

/**
 * Testing Planes
 * 
 * @author Idan ane Eliot 
 *
 */
public class PlaneTests {

	/**
	 * Test method for {@link geometries.Polygon#Polygon(primitives.Point...)}.
	 */
	@Test
	public void testConstructor() {
		// ============ Equivalence Partitions Tests ==============

		// TC01: Correct concave quadrangular with vertices in correct order
		try {
			new Plane(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));
		} catch (IllegalArgumentException e) {
			fail("Failed constructing a correct polygon");
		}
		// =============== Boundary Values Tests ==================
			// TC02: two Points are the same 
			assertThrows(IllegalArgumentException.class, //
			() -> new Polygon(new Point(0, 0, 1), new Point(0, 0, 1), new Point(0, 1,0), new Point(0, 0.5, 0.5)),
			"Constructed a plane that 2 of his points are the same ");	
		// TC03: Vertex that creates colinear vectors 
		assertThrows(IllegalArgumentException.class, //
				() -> new Polygon(new Point(0, 0, 1), new Point(0, 0, 0), new Point(0, 0,2), new Point(0, 0.5, 0.5)),
				"Constructed a plane that his base vectors are colinear");	

	}

	/**
	 * Test method for {@link geometries.Polygon#getNormal(primitives.Point)}.
	 * 1.checks if it is on the plane 
	 */
	@Test
	public void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: There is a simple single test here
		Plane pl = new Plane(new Point(0, 0, 0), new Point(1, 0, 0), new Point(0, 1, 0));
		assertThrows(IllegalArgumentException.class, 
		() -> new Vector(0, 0, 1).crossProduct(pl.getNormal(new Point(0, 1, 0))),
		"Bad normal to Plane");	 

	
	
	
	}
}
