package items;
import graphics.*;
import characters.*;
import characters.Character;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HealthPackTest {

    HealthPack h;
    Character c;
    Field f;
    Location l;
    int current_healthPoints;

    @Before
    public void setUp() throws Exception {
        h = new HealthPack();
        f = new Field(10, 10);
        l = new Location(3,3);
        c = new Human("Alexandre", f, l);
        current_healthPoints = c.getHealthPoints();
    }

    @After
    public void tearDown() throws Exception {
        h = null;
        f = null;
        l = null;
        current_healthPoints = 0;
    }

    @Test
    public void testHealthPack() {
        assertNotNull(h);
    }

    @Test
    public void testUse() {
        assertTrue(h.use(c));
        assertTrue(h.jammed());
        assertEquals(current_healthPoints+ HealthPack.getHp(), c.getHealthPoints());
    }

    @Test
    public void testJammed() {
        assertTrue(h.use(c));
        assertFalse(h.use(c));
        assertTrue(h.jammed());
    }

    @Test
    public void testGetType() {
        assertEquals(TypeOfItem.HEALTH_PACK, h.getType());
    }

}
