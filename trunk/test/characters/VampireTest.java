package characters;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import items.Pistol;
import graphics.Field;
import graphics.Location;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class VampireTest {

    Vampire v;
    Location location;
    Field field;
    int vampireHP;

    @Before
    public void setUp() throws Exception {

        location = new Location(5,5);
        field = new Field(10,10);
        v = new Vampire("VampireTest", field, location);

        java.lang.reflect.Field attribut = Vampire.class.getDeclaredField("HP_VAMPIRES");
        attribut.setAccessible(true);

        vampireHP = (Integer)attribut.get(Vampire.class);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testAct() {
        List<Character> newCharacters = new ArrayList();
        v.act(newCharacters);
        assertNotSame(new Location(5,5), v.getLocation());       
    }

    @Test
    public void testBite() {
        Human h = new Human("test", field, new Location(5,6));
        v.setIsThirsty(true);
        v.bite(h);
        assertFalse(v.getIsThirsty());
        assertTrue(h.getHasBeenBitten());

    }

    @Test
    public void testFindHuman() {
        Human h = new Human("test", field, new Location(5,6));

        v.findHuman(v.getLocation());
        try {

            java.lang.reflect.Field attribut = Human.class.getDeclaredField("HP_HUMANS");
            attribut.setAccessible(true);
            int humanHP = (Integer)attribut.get(Human.class);

            assertEquals(humanHP-10, h.getHealthPoints());

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

        v.beingAttacked(h);

        java.lang.reflect.Field attribut;
        try {
            attribut = Pistol.class.getDeclaredField("ATTACK_POWER");
            attribut.setAccessible(true);
            int pistolPower = (Integer)attribut.get(Pistol.class);


            assertEquals(vampireHP-pistolPower, v.getHealthPoints());


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
