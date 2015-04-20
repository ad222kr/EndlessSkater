/**
 * Created by Alex on 2015-04-16.
 */



import com.alexd.projectgame.gameobjects.Runner;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UnitTests {



    @Test
    public void testRemoveHealth(){
        // Setup

        Runner runner = new Runner();
        int expected = 2;

        // act
        runner.removeHealth();

        // assert
        assertEquals(expected, runner.getHealth());
    }

    @Test
    public void testAddHealth(){
        // Setup

        Runner runner = new Runner();
        int expected = 3;
        runner.removeHealth();

        // act
        runner.addHealth();

        // assert
        assertEquals(expected, runner.getHealth());
    }
}
