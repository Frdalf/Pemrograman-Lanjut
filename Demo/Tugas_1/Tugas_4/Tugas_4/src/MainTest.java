import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    // Skenario 1
    @Test
    public void testScenario1() {
        assertEquals(3, Main.findMin(1, 2, 3));
    }

    // Skenario 2
    @Test
    public void testScenario2() {
        assertEquals(-1, Main.findMin(-1, -2, -3));
    }

    // Skenario 3
    @Test
    public void testScenario3() {
        assertEquals(0, Main.findMin(0, 0, 1));
    }
}