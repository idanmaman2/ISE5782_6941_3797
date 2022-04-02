package unittests.primitives;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import primitives.Point;
import primitives.Vector;

  /*
  test point ops
 * @author Idan ane Eliot 
  */ 
      
public class PointTests {
        
    @Test
    public void  testDistanceSquared(){//returns the duplicates of the difference of the two points
    // ============ Equivalence Partitions Tests ==============
    Point p1 = new Point(1,1,1); 
    Point p2 = new Point(2,2,2); 
    assertEquals("distanceSquared() wrong result pos scalar", p1.distanceSquared(p2),3 , 0.00001 );
    assertEquals("distanceSquared() wrong result pos scalar", p2.distanceSquared(p1),3 , 0.00001 );

    assertEquals("distanceSquared() wrong result pos scalar", p1.distanceSquared(p1),0 , 0.00001 );
    // =============== Boundary Values Tests ==================
    
    }

    @Test
    public void testDistance(){ // square root
        // ============ Equivalence Partitions Tests ==============
        Point p1 = new Point(1,1,1); 
        Point p2 = new Point(2,2,2); 
        assertEquals("distance() wrong result pos scalar", p1.distance(p2),Math.sqrt(3) , 0.00001 );
        assertEquals("distance() wrong result pos scalar", p2.distance(p1),Math.sqrt(3) , 0.00001 ); 
    
        assertEquals("distance() wrong result pos scalar", p1.distance(p1),0 , 0.00001 );
        // =============== Boundary Values Tests ==================
    }
    @Test
    public void testAdd () { 
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1,0,0);
        Point p1 = new Point(1,1,1); 
        assertEquals("add() wrong result pos scalar", p1.add(v1),new Point(2,1,1));

    }

    @Test
    public void testSubtract () { // subtract the two
            // ============ Equivalence Partitions Tests ==============
        Point p1 = new Point(1,1,1); 
        Point p2 = new Point(1,0,0);
        assertEquals("subtract() wrong result pos scalar", p1.subtract(p2),new Vector(0,1,1));
             // =============== Boundary Values Tests ==================
        assertThrows("subtract() for parallel vectors does not throw an exception",
        IllegalArgumentException.class, () -> p1.subtract(p1));
       }


    
}
    