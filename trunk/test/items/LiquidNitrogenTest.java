package items;
import graphics.*;
import characters.*;
import characters.Character;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LiquidNitrogenTest {

    LiquidNitrogen ln;
    Character c;
    Field f;
    Location l;
    int current_healthPoints;

    @Before
    public void setUp() throws Exception {
        ln = new LiquidNitrogen();
        f = new Field(10, 10);
        l = new Location(3,3);
        c = new Zombie("Alexandre", f, l);
    }

    @After
    public void tearDown() throws Exception {
        ln = null;
        f = null;
        l = null;
    }

    @Test
    public void testLiquidNitrogen() {
        assertNotNull(ln);
        assertTrue(ln.getPercentage_left() == 100);
    }

    @Test
    public void testUse() {
        assertTrue(ln.use(c));
        assertEquals(0, c.getHealthPoints());
    }

    @Test
    public void testJammed() {
        while(ln.getPercentage_left() >= 0.00)
        {
            ln.use(c);
        }
        assertTrue(ln.jammed());
    }

    @Test
    public void testGetPercentageLeft() {
        assertTrue(ln.use(c));
        assertTrue(ln.getPercentage_left() >= 70.00);
        assertTrue(ln.getPercentage_left() <= 100);
    }

    @Test
    public void testGetType() {
        assertEquals(TypeOfItem.LIQUID_NITROGEN, ln.getType());
    }

}
