package characters;

import static org.junit.Assert.*;
import graphics.Field;
import graphics.Location;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CharacterTest {

    Human h;
    Location location;
    Field field;

    @Before
    public void setUp() throws Exception {
        field = new Field(10,10);
        location = new Location(3,3);        
        h = new Human("Test", field, location);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetName() {
        assertEquals("Test", h.getName());
    }

    @Test
    public void testGetHealthPoints() {
        assertEquals(100, h.getHealthPoints());
    }

    @Test
    public void testGetLocation() {
        assertEquals(location, h.getLocation());
        assertEquals(3, h.getLocation().getCol());
        assertEquals(3, h.getLocation().getRow());
    }

    @Test
    public void testGetField() {
        assertEquals(field, h.getField());
    }

    @Test
    public void testSetLocation() {
        h.setLocation(new Location(3,4));
        assertEquals(3, h.getLocation().getRow());
        assertEquals(4, h.getLocation().getCol());
    }

    @Test
    public void testIsAlive() {
        assertTrue(h.isAlive());
        h.setDead();
        assertFalse(h.isAlive());
    }

    @Test
    public void testSetDead() {
        h.setDead();
        assertEquals(0, h.getHealthPoints());
        assertEquals(null, h.getLocation());
        assertEquals(null, h.getField());
    }

    @Test
    public void testReduceHealthPoints() {
        h.reduceHealthPoints(10);
        assertEquals(90, h.getHealthPoints());
    }


    @Test
    public void testThisIsHero() {
        assertTrue(h.thisIsHero());
        Vampire v = h.turnIntoVampire();
        assertFalse(v.thisIsHero());        
    }

}
