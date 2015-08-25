package characters;

import static org.junit.Assert.*;

import items.Pistol;

import java.util.ArrayList;
import java.util.List;

import graphics.Field;
import graphics.Location;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ZombieTest {

    Zombie z;
    Location location;
    Field field;
    int zombieHP;

    @Before
    public void setUp() throws Exception {
        location = new Location(5,5);
        field = new Field(10,10);
        z = new Zombie("ZombieTest", field, location);

        java.lang.reflect.Field attribut = Zombie.class.getDeclaredField("HP_ZOMBIES");
        attribut.setAccessible(true);

        zombieHP = (Integer)attribut.get(Zombie.class);
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void testAct() {
        List<Character> newCharacters = new ArrayList();
        z.act(newCharacters);
        assertNotSame(new Location(5,5), z.getLocation());    
    }

    @Test
    public void testFindHuman() {
        Human h = new Human("test", field, new Location(5,6));

        z.findHuman(z.getLocation(), new ArrayList());
        try {

            java.lang.reflect.Field attribut = Human.class.getDeclaredField("HP_HUMANS");
            attribut.setAccessible(true);
            int humanHP = (Integer)attribut.get(Human.class);

            assertEquals(humanHP-25, h.getHealthPoints());

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

    @Test
    public void testBeingAttacked() {
        Human h = new Human("test", field, new Location(5,6));

        h.take(new Pistol());

        z.beingAttacked(h);

        java.lang.reflect.Field attribut;
        try {
            attribut = Pistol.class.getDeclaredField("ATTACK_POWER");
            attribut.setAccessible(true);
            int pistolPower = (Integer)attribut.get(Pistol.class);


            assertEquals(zombieHP-pistolPower, z.getHealthPoints());


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
