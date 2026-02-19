import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bordomey.lab3.beans.Coordinates;
import com.bordomey.lab3.utils.AreaCheck;

import static org.junit.jupiter.api.Assertions.*;

public class AreaCheckTest {
    private AreaCheck areaCheck;
    private Coordinates coordinates;

    @BeforeEach
    public void setUp() {
        areaCheck = new AreaCheck();
        coordinates = new Coordinates();
    }

    @AfterEach
    public void tearDown() {
        areaCheck = null;
        coordinates = null;
    }

    @Test
    public void testCircleHitPositive() {
        coordinates.setX(0.5);
        coordinates.setY(0.5);
        coordinates.setR(3);

        assertTrue(areaCheck.isHit(coordinates));
        assertTrue(areaCheck.isHit(0.5, 0.5, 3));
    }

    @Test
    public void testCircleHitNegative() {
        coordinates.setX(2);
        coordinates.setY(2);
        coordinates.setR(3);

        assertFalse(areaCheck.isHit(coordinates));
        assertFalse(areaCheck.isHit(2, 2, 3));
    }

    @Test
    public void testRectangleHitPositive() {
        coordinates.setX(1);
        coordinates.setY(-1);
        coordinates.setR(4);

        assertTrue(areaCheck.isHit(coordinates));
        assertTrue(areaCheck.isHit(1, -1, 4));
    }

    @Test
    public void testRectangleHitNegative() {
        coordinates.setX(3);
        coordinates.setY(-1);
        coordinates.setR(4);

        assertFalse(areaCheck.isHit(coordinates));
        assertFalse(areaCheck.isHit(3, -1, 4));
    }

    @Test
    public void testTriangleHitPositive() {
        coordinates.setX(-1);
        coordinates.setY(-1);
        coordinates.setR(3);

        assertTrue(areaCheck.isHit(coordinates));
        assertTrue(areaCheck.isHit(-1, -1, 3));
    }

    @Test
    public void testTriangleHitNegative() {
        coordinates.setX(-3);
        coordinates.setY(-3);
        coordinates.setR(3);

        assertFalse(areaCheck.isHit(coordinates));
        assertFalse(areaCheck.isHit(-3, -3, 3));
    }

    @Test
    public void testBorderCasesCircle() {
        // Point on the circle border
        coordinates.setX(Math.sqrt(2.25/2)); // sqrt((r/2)^2/2) when r=3
        coordinates.setY(Math.sqrt(2.25/2));
        coordinates.setR(3);

        assertTrue(areaCheck.isHit(coordinates));
    }

    @Test
    public void testBorderCasesRectangle() {
        // Point on the rectangle border
        coordinates.setX(1.5); // r/2 when r=3
        coordinates.setY(-3); // -r when r=3
        coordinates.setR(3);

        assertTrue(areaCheck.isHit(coordinates));
    }

    @Test
    public void testBorderCasesTriangle() {
        // Point on the triangle border
        coordinates.setX(-1);
        coordinates.setY(-2); // x + y = -3 = -r when r=3
        coordinates.setR(3);

        assertTrue(areaCheck.isHit(coordinates));
    }

    @Test
    public void testOrigin() {
        coordinates.setX(0);
        coordinates.setY(0);
        coordinates.setR(1);

        assertTrue(areaCheck.isHit(coordinates));
        assertTrue(areaCheck.isHit(0, 0, 1));
    }

    @Test
    public void testNullCoordinates() {
        assertFalse(areaCheck.isHit((Coordinates) null));
    }

    @Test
    public void testOutsideAllAreas() {
        coordinates.setX(5);
        coordinates.setY(5);
        coordinates.setR(1);

        assertFalse(areaCheck.isHit(coordinates));
        assertFalse(areaCheck.isHit(5, 5, 1));
    }

    @Test
    public void testMultiplePointsInCircleArea() {
        double r = 2;
        // Test various points in circle area
        assertTrue(areaCheck.isHit(0.5, 0.5, r));
        assertTrue(areaCheck.isHit(0.8, 0.3, r));
        assertTrue(areaCheck.isHit(0.1, 0.9, r));
        
        // Test points just outside circle area
        assertFalse(areaCheck.isHit(1.1, 0.5, r)); // Outside circle area
    }

    @Test
    public void testMultiplePointsInRectangleArea() {
        double r = 4;
        // Test various points in rectangle area
        assertTrue(areaCheck.isHit(1, -1, r));
        assertTrue(areaCheck.isHit(1.5, -2, r));
        assertTrue(areaCheck.isHit(0.5, -3, r));
        
        // Test points just outside rectangle area
        assertFalse(areaCheck.isHit(2.1, -1, r)); // Outside rectangle width
        assertFalse(areaCheck.isHit(1, -4.1, r)); // Below rectangle
    }

    @Test
    public void testMultiplePointsInTriangleArea() {
        double r = 3;
        // Test various points in triangle area
        assertTrue(areaCheck.isHit(-0.5, -1, r)); // x + y = -1.5 >= -3
        assertTrue(areaCheck.isHit(-1, -1, r));   // x + y = -2 >= -3
        assertTrue(areaCheck.isHit(-2, -0.5, r)); // x + y = -2.5 >= -3
        
        // Test points just outside triangle area
        assertFalse(areaCheck.isHit(-1, -2.5, r)); // x + y = -3.5 < -3
        assertFalse(areaCheck.isHit(-2.5, -1, r)); // x + y = -3.5 < -3
    }
}