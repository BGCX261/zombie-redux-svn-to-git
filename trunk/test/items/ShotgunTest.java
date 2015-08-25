package items;

import static org.junit.Assert.*;
import graphics.Field;
import graphics.Location;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import characters.Zombie;

public class ShotgunTest {

    Shotgun p;
    Zombie c;
    Field f;
    Location l;
    int current_healthPoints;
    int bullets;

    @Before
    public void setUp() throws Exception {
        p = new Shotgun();
        f = new Field(10, 10);
        l = new Location(3,3);
        c = new Zombie("Alexandre", f, l);
        current_healthPoints = c.getHealthPoints();
        bullets = p.getBullets();
    }

    @After
    public void tearDown() throws Exception {
        p = null;
        f = null;
        l = null;
        current_healthPoints = 0;
        bullets = 0;
    }

    @Test
    public void testShotgun() {
        assertNotNull(p);
    }

    @Test
    public void testUse() {
        assertTrue(p.use(c));
        assertEquals(bullets, p.getBullets()+1);
        assertTrue(c.getStunnedFor() >= 1);
        assertTrue(c.getStunnedFor() <= Shotgun.getMaxStun());
    }

    @Test
    public void testJammed() {
        while(p.getBullets() != 0)
        {
            p.use(c);
        }
        assertTrue(p.jammed());
    }

    @Test
    public void testGetType() {
        assertEquals(TypeOfItem.SHOTGUN, p.getType());
    }

}
