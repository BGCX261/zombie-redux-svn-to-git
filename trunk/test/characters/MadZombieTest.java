package characters;

import static org.junit.Assert.*;

import java.util.ArrayList;

import graphics.Field;
import graphics.Location;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MadZombieTest {

    MadZombie m;
    Location location;
    Field field;

    @Before
    public void setUp() throws Exception {
        location = new Location(5,5);
        field = new Field(10,10);
        m = new MadZombie("MadZombieTest", field, location);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testFindHuman() {
        Human h = new Human("test", field, new Location(5,6));

        m.findHuman(m.getLocation(), new ArrayList());
        try {

            java.lang.reflect.Field attribut = Human.class.getDeclaredField("HP_HUMANS");
            attribut.setAccessible(true);
            int humanHP = (Integer)attribut.get(Human.class);

            assertEquals(humanHP-40, h.getHealthPoints());

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
