/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.*;

/**
 * Testing Polygons
 * 
 * @author Idan ane Eliot 
 *
 */
public class TriangleTests {

	/**
	 * Test method for {@link geometries.Polygon#Polygon(primitives.Point...)}.
	 */
	@Test
	public void testConstructor() {
		// ============ Equivalence Partitions Tests ==============

		// TC01: Correct concave quadrangular with vertices in correct order
		try {
			new Triangle(new Point(0, 0, 1), new Point(1, 0, 0), new Point(-1, 1, 1));
		} catch (IllegalArgumentException e) {
			fail("Failed constructing a correct polygon");
		}

		// TC02: Wrong vertices order
		//assertThrows(IllegalArgumentException.class, //
			//	() -> new Triangle(new Point(0, 0, 1), new Point(0, 1, 0), new Point(0, -1, 1)), //
		//		"Constructed a Triangle with wrong order of vertices");



		// =============== Boundary Values Tests ==================

		// TC10: Vertex on a side of a quadrangular
		assertThrows(IllegalArgumentException.class, //
				() -> new Triangle(new Point(0, 0, 1),  new Point(0, 1, 0), new Point(0, 0.5, 0.5)),
				"Constructed a Triangle with vertix on a side");

		// TC11: Last point = first point
		assertThrows(IllegalArgumentException.class, //
				() -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 0, 1)),
				"Constructed a Triangle with vertice on a side");


	}

	/**
	 * Test method for {@link geometries.Polygon#getNormal(primitives.Point)}.
	 */
	@Test
	public void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: There is a simple single test here
		Polygon pl = new Triangle(new Point(0, 0, 0), new Point(1, 0, 0), new Point(0, 1, 0));
		assertEquals(new Vector(0,0 , 1), pl.getNormal(new Point(0, 1, 0)), "Bad normal to trinagle");//checks on the triangle 
	}
}
