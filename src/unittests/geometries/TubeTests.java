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
	@Test
	void findIntersections() {
		Tube tube = new Tube(new Ray(new Point(0,0,-2), new Vector(0,0,4)), 4d);

		// ============ Equivalence Partitions Tests ==============

		// TC01: Ray's line is outside the tube (0 points)
		assertNull(tube.findIntersections(new Ray(new Point(6, 4, 5), new Vector(2,-6,-5))),
				"Ray's line out of tube");

		// TC02: Ray starts before and crosses the tube (2 points)
		Point p1 = new Point(-3.8971531066245366, 0.9012200971609129, 0.45061004858045645);
		Point p2 = new Point(2.035084141107294, 3.4436074890459833, 1.7218037445229917);
		List<Point> result = tube.findIntersections(new Ray(new Point(-6, 0, 0), new Vector(14, 6, 3)));
		assertEquals(2, result.size(), "Wrong number of points");
		if (result.get(0).getX() > result.get(1).getX())
			result = List.of(result.get(1), result.get(0));
		assertEquals(List.of(p1, p2), result, "Ray crosses tube");

		// TC03: Ray starts inside the tube (1 point)
		p1 = new Point(2.9883515824043925, 2.6589010549362615, 1.3294505274681307);
		result = tube.findIntersections(new Ray(new Point(-1,0,0), new Vector(9,6,3)));
		assertEquals(result.size(), 1, "Wrong number of points");
		assertEquals(List.of(p1), result, "Ray crosses sphere");

		// TC04: Ray starts after the tube (0 points)
		assertNull(tube.findIntersections(new Ray(new Point(4, 0, 0), new Vector(4,-2,0))),
				"Ray's line out of tube");

		// =============== Boundary Values Tests ==================

		// **** Group: Ray's line crosses the tube (but not the center)
		// TC11: Ray starts at sphere and goes inside (1 points)
		p1 = new Point(3.7169390722305886, 1.477959381487059, 0.0);
		result = tube.findIntersections(new Ray(new Point(3,1,0), new Vector(3,2,0)));
		assertEquals(result.size(), 1, "Wrong number of points");
		assertEquals(List.of(p1), result, "Ray crosses tube");

		// TC12: Ray starts at sphere and goes outside (0 points)
		assertNull(tube.findIntersections(new Ray(new Point(6,3,0), new Vector(3,3,1))),
				"Ray's line out of tube");

		// **** Group: Ray's line goes through the center
		// TC13: Ray starts before the tube (2 points)
		p1 = new Point(-4, 0, 0);
		p2 = new Point(4, 0, 0);
		result = tube.findIntersections(new Ray(new Point(-6,0,0), new Vector(14,0,0)));
		assertEquals(result.size(), 2, "Wrong number of points");
		if (result.get(0).getX() > result.get(1).getX())
			result = List.of(result.get(1), result.get(0));
		assertEquals(List.of(p1, p2), result, "Ray crosses tube");

		// TC14: Ray starts at tube and goes inside (1 points)
		p1 = new Point(4, 0.0, 0.0);
		result = tube.findIntersections(new Ray(new Point(-2,0,0), new Vector(8,0,0)));
		assertEquals(result.size(), 1, "Wrong number of points");
		assertEquals(List.of(p1), result, "Ray crosses tube");

		// TC15: Ray starts inside (1 points)
		p1 = new Point(4, 0, 0.0);
		result = tube.findIntersections(new Ray(new Point(2,0,0), new Vector(6,0,0)));
		assertEquals(result.size(), 1, "Wrong number of points");
		assertEquals(List.of(p1), result, "Ray crosses tube");

		// TC16: Ray starts at the center (1 points)
		p1 = new Point(4, 0.0, 0.0);
		result = tube.findIntersections(new Ray(new Point(0,0,0), new Vector(8,0,0)));
		assertEquals(result.size(), 1, "Wrong number of points");
		assertEquals(List.of(p1), result, "Ray crosses sphere");

		// TC17: Ray starts at sphere and goes outside (0 points)
		assertNull(tube.findIntersections(new Ray(new Point(4,0,0), new Vector(4,0,0))),
				"Ray's line out of tube");

		// TC18: Ray starts after sphere (0 points)
		assertNull(tube.findIntersections(new Ray(new Point(6,0,0), new Vector(2,0,0))),
				"Ray's line out of tube");

		// **** Group: Ray's line is tangent to the tube (all tests 0 points)
		// TC19: Ray starts before the tangent point
		assertNull(tube.findIntersections(new Ray(new Point(4,-5,0), new Vector(0,3,0))),
				"Ray's line out of tube");

		// TC20: Ray starts at the tangent point
		assertNull(tube.findIntersections(new Ray(new Point(4,0,0), new Vector(0,3,0))),
				"Ray's line out of tube");

		// TC21: Ray starts after the tangent point
		assertNull(tube.findIntersections(new Ray(new Point(4,1,0), new Vector(0,3,0))),
				"Ray's line out of tube");

		// **** Group: Special cases
		// TC19: Ray's line is outside, ray is orthogonal to ray start to tube's center line
		assertNull(tube.findIntersections(new Ray(new Point(6,1,0), new Vector(0,2,0))),
				"Ray's line out of tube");
	}
}
