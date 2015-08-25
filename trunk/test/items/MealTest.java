package items;
import graphics.*;
import characters.*;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MealTest {

    Item i;
    Human c;
    Field f;
    Location l;
    int current_healthPoints;

    @Before
    public void setUp() throws Exception {
        i = new Meal();
        f = new Field(10, 10);
        l = new Location(3,3);
        c = new Human("Alexandre", f, l);
        current_healthPoints = c.getHealthPoints();
    }

    @After
    public void tearDown() throws Exception {
        i = null;
        f = null;
        l = null;
        current_healthPoints = 0;
    }

    @Test
    public void testMeal() {
        assertNotNull(i);
    }

    @Test
    public void testUse() {

        for(int i=1; i<=3; i++)
        {
            c.endOfTurn();
            assertEquals(current_healthPoints, c.getHealthPoints());
            assertEquals(i, c.getTurnsSinceLastMeal());
        }

        c.endOfTurn();
        assertEquals(current_healthPoints-5, c.getHealthPoints());

        assertEquals(4, c.getTurnsSinceLastMeal());
        assertTrue(i.use(c));
        assertTrue(i.jammed());
        assertEquals(0, c.getTurnsSinceLastMeal());
    }

    @Test
    public void testJammed() {
        assertTrue(i.use(c));
        assertFalse(i.use(c));
        assertTrue(i.jammed());
    }

    @Test
    public void testGetType() {
        assertEquals(TypeOfItem.MEAL, i.getType());
    }

}
