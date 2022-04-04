/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.*;

import java.util.List;

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
		assertThrows(IllegalArgumentException.class, 
		() -> new Vector(0, 0, 1).crossProduct(pl.getNormal(new Point(0, 1, 0))),
		"Bad normal to triangle");	
		assertEquals(1,pl.getNormal(new Point(0, 1, 0)).length(),0.00001,"the normal is not normlaize ");
		
	}

	@Test
	void findIntersections() {
		Triangle p1 = new Triangle(new Point(1, 2, 3), new Point(-1, -2, 4), new Point(4, 2, 1));

		// ============ Equivalence Partitions Tests ==============
		//TC01: Ray intersect inside Triangle
		Ray r1 = new Ray(new Point(-1, -1, 1), new Vector(1, 1, 1));
		assertEquals(List.of(new Point(0.9473684210526312, 0.9473684210526312, 2.947368421052631)), p1.findIntsersections(r1),
				"findIntersections() wrong result");

		//TC02: Ray intersect outside Triangle against edge
		Ray r2 = new Ray(new Point(-1, -1, 1), new Vector(1, 1, 4));
		assertNull(p1.findIntsersections(r2),
				"findIntersections() wrong result");

		//TC03: Ray intersect outside Triangle against vertex
		Ray r3 = new Ray(new Point(-1, -1, 1), new Vector(7, 4, -1));
		assertNull(p1.findIntsersections(r3),
				"findIntersections() wrong result");

		// =============== Boundary Values Tests ==================
		//TC11: Ray intersect on edge
		Ray r4 = new Ray(new Point(-1, -1, 1), new Vector(2.6, 3, 1.6));
		assertNull(p1.findIntsersections(r4),
				"findIntersections() Ray intersect on edge wrong result");

		//TC12: Ray intersect in vertex
		Ray r5 = new Ray(new Point(-1, -1, 1), new Vector(0, -1, 3));
		assertNull(p1.findIntsersections(r5),
				"findIntersections() Ray intersect in vertex wrong result");

		//TC13: Ray intersect on edge's continuation
		Ray r6 = new Ray(new Point(-1, -1, 1), new Vector(3, 5, 1.5));
		assertNull(p1.findIntsersections(r6),
				"findIntersections() Ray intersect on edge's continuation wrong result");
	}
}

