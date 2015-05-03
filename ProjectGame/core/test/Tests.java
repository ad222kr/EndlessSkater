/**
 * Created by Alex on 2015-04-16.
 */



import com.alexd.projectgame.enums.GameObjectType;
import com.alexd.projectgame.gameobjects.GameObject;
import com.alexd.projectgame.gameobjects.Runner;
import com.alexd.projectgame.helpers.Helpers;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Tests {


    /**
     * Test of class Runner
     */
    @Test
    public void testRemoveHealth(){
        // Setup

        Runner runner = new Runner();
        runner.setHealth(3);
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
        runner.setHealth(2);
        int expected = 3;

        // act
        runner.addHealth();

        // assert
        assertEquals(expected, runner.getHealth());
    }

    /**
     * Test of class Helpers
     */

    @Test
    public void testConvertToMeters(){
        // Setup
        int pixels = 100;
        int expected = 2;

        // act

        int result = Helpers.convertToMeters(pixels);

        // assert
        assertEquals(expected, result);
    }

    @Test
    public void testGetRandomInt(){
        // Setup
        int min = 1;
        int max = 20;
        int [] expected = new int[100];

        // act
        for (int i = 0; i < expected.length; i++ ){
            expected[i] = Helpers.getRandomInt(min, max);
        }

        // assert
        for (float value : expected){
            assertTrue("Should be between 1-20", value >= 1 && value <= 20);
        }
    }

    @Test
    public void testGetRandomFloat(){
        int min = 1;
        int max = 20;
        float [] expected = new float[100];

        for (int i = 0; i < expected.length; i++){
            expected[i] = Helpers.getRandomFloat(min, max);
        }

        for(float value : expected){
            assertTrue("Should be between 1-20", value >= 0 && value <= 20);
        }
    }

    @Test
    public void testRunnerJump(){
        // Setup
        World world = new World(new Vector2(0, -10), true);
        Runner runner = new Runner(world, 0, 0, 1, 1);

        // act
        runner.jump();

        // assert
        assertTrue("runner.getIsJumpong should return true", runner.getIsJumping());

    }

    @Test
    public void testRunnerLanded(){
        // Setup
        World world = new World(new Vector2(0, -10), true);
        Runner runner = new Runner(world, 0, 0, 1, 1);
        runner.jump();

        // act
        runner.landed();

        // assert
        assertFalse("runner.getIsJumping should return false", runner.getIsJumping());
    }


}
