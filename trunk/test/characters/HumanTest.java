package characters;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import items.HealthPack;
import items.Item;
import items.Meal;
import items.Pistol;
import items.Shotgun;
import items.TypeOfItem;
import graphics.Field;
import graphics.Location;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HumanTest {

    Human h;
    Location location;
    Field field;
    int humanHP;

    @Before
    public void setUp() throws Exception {
        location = new Location(5,5);
        field = new Field(10,10);
        h = new Human("HumanTest", field, location);

        java.lang.reflect.Field attribut = Human.class.getDeclaredField("HP_HUMANS");
        attribut.setAccessible(true);

        humanHP = (Integer)attribut.get(h);

    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void testTake() {
        Item item = new Shotgun();
        assertEquals(0, h.getBag().size());
        h.take(item);
        assertEquals(1, h.getBag().size());        
    }

    @Test
    public void testGetItem() {
        Item item = new Meal();
        h.take(item);
        assertEquals(null, h.getItem(TypeOfItem.SHOTGUN));
        assertEquals(item, h.getItem(TypeOfItem.MEAL));
    }

    @Test
    public void testBagContains() {
        Item item = new Meal();
        h.take(item);
        assertTrue(h.bagContains(TypeOfItem.MEAL));
        assertFalse(h.bagContains(TypeOfItem.SHOTGUN));
    }

    @Test
    public void testUse() {
        Item item = new HealthPack();
        h.take(item);
        h.reduceHealthPoints(30);
        h.use(item, h);
        assertEquals(100, h.getHealthPoints());

    }


    @Test
    public void testTurnIntoVampire() {
        Vampire v = h.turnIntoVampire();
        assertEquals(field, v.getField());
        assertEquals(Vampire.HP_VAMPIRES,v.getHealthPoints());
        assertEquals(h.getName(),v.getName());
    }

    @Test
    public void testTurnIntoZombie() {
        Zombie v = h.turnIntoZombie();
        assertEquals(field, v.getField());

        java.lang.reflect.Field attribut;
        try {
            attribut = Zombie.class.getDeclaredField("HP_ZOMBIES");
            attribut.setAccessible(true);
            int zombieHP = (Integer)attribut.get(Zombie.class);

            assertEquals(zombieHP,v.getHealthPoints());

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        assertEquals(h.getName(),v.getName());
    }

    @Test
    public void testFindEnnemys() {
        Vampire v = new Vampire("TestVampire", field, new Location(5,6));
        h.take(new Pistol());
        h.findEnnemies(h.getLocation());
        try {

            java.lang.reflect.Field attribut = Vampire.class.getDeclaredField("HP_VAMPIRES");
            attribut.setAccessible(true);
            int vampireHP = (Integer)attribut.get(Vampire.class);

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

    @Test
    public void testBeingAttackedVampire() {        
        h.beingAttacked(new Vampire("VampireTest", field, new Location(5,6)));
        assertEquals(humanHP-10, h.getHealthPoints());
    }

    @Test
    public void testBeingAttackedZombie() {
        h.beingAttacked(new Zombie("ZombieTest", field, new Location(5,6)));
        assertEquals(humanHP-25, h.getHealthPoints());
    }

    @Test
    public void testBeingAttackedMadZombie() {
        h.beingAttacked(new MadZombie("ZombieTest", field, new Location(5,6)));
        assertEquals(humanHP-40, h.getHealthPoints());
    }

    @Test
    public void testFindItems() {
        Item item = new Shotgun();
        field.placeItem(item, 5, 6);
        assertFalse(h.bagContains(TypeOfItem.SHOTGUN));
        h.findItems(h.getLocation());
        assertTrue(h.bagContains(TypeOfItem.SHOTGUN));
    }

    @Test
    public void testEndOfTurn() {
        assertEquals(0, h.getTurnsSinceLastMeal());
        h.endOfTurn();
        h.endOfTurn();
        h.endOfTurn();
        h.endOfTurn();
        assertEquals(4, h.getTurnsSinceLastMeal());
        assertEquals(humanHP-5, h.getHealthPoints());

    }


    @Test
    public void testAct() {
        List<Character> newCharacters = new ArrayList();
        h.act(newCharacters);
        assertNotSame(new Location(5,5), h.getLocation());
        h.setHasBeenBitten(true);
        h.act(newCharacters);
        assertEquals(1, newCharacters.size());
    }

}
