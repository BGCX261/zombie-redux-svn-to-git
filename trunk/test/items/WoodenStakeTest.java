package items;

import static org.junit.Assert.*;
import graphics.Field;
import graphics.Location;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import characters.*;
import characters.Character;


public class WoodenStakeTest {

    WoodenStake h;
    Character c;
    Field f;
    Location l;

    @Before
    public void setUp() throws Exception {
        h = new WoodenStake();
        f = new Field(10, 10);
        l = new Location(3,3);
        c = new Vampire("Alexandre", f, l);
    }

    @After
    public void tearDown() throws Exception {
        h = null;
        f = null;
        l = null;
    }

    @Test
    public void testWoodenStake() {
        assertNotNull(h);
    }

    @Test
    public void testUse() {
        assertTrue(h.use(c));
        assertTrue(h.jammed());
        assertEquals(0, c.getHealthPoints());    
    }

    @Test
    public void testJammed() {
        assertTrue(h.use(c));
        assertFalse(h.use(c));
        assertTrue(h.jammed());
    }

    @Test
    public void testGetType() {
        assertEquals(TypeOfItem.WOODEN_STAKE, h.getType());
    }

}
