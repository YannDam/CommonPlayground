package commonplayground.model;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SessionTest {
    private static Session testSession;



    @BeforeClass
    public static void setUp() {
        testSession = new Session("Title", "Description", "Game", "Place", "12.12.2018", "12:00", 42, (long)4, "Genre", "Online");
    }

    @Test
    public void testName(){
        assertEquals(testSession.getTitle(), "Title");
    }

    @Test
    public void testDescription(){
        assertEquals(testSession.getDescription(), "Description");
    }

    @Test
    public void testGame(){
        assertEquals(testSession.getGame(), "Game");
    }

    @Test
    public void testPlace(){
        assertEquals(testSession.getPlace(), "Place");
    }

    @Test
    public void testDate(){
        assertEquals(testSession.getDate(), "12.12.2018");
    }

    @Test
    public void testTime(){
        assertEquals(testSession.getTime(), "12:00");
    }

    @Test
    public void testNumberOfPlayers(){
        assertEquals(testSession.getNumberOfPlayers(), 42);
    }

    @Test
    public void testHostId(){
        assertEquals(0,Long.compare(testSession.getIdOfHost(), 4));
    }

    @Test
    public void addUserToSession(){
        User assertHost = new User("Host", "1234567890","host@host.de");
        testSession.addUserToSession(assertHost);
        assertTrue(testSession.getUsers().contains(assertHost));
    }

    @Test
    public void testAddUserWantToJoin(){
        User userWantsToJoin = new User("Huhu", "1234567890","huhu@hoho.de");
        testSession.addUserWantToJoin(userWantsToJoin);
        assertTrue(testSession.getUserWantToJoin().contains(userWantsToJoin));
    }
}