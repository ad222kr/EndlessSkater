/**
 * Created by Alex on 2015-04-16.
 */



import com.alexd.projectgame.model.Enemy;
import com.alexd.projectgame.model.Runner;
import com.alexd.projectgame.screens.GameScreen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UnitTests {

    @Test
    public void testRunnerJump(){
        // Setup
        World world = new World(new Vector2(0, -10), true);
        Runner runner = new Runner(world);

        // act
        runner.jump();

        // assert
        assertTrue("runner.getIsJumpong should return true", runner.getIsJumping());

    }

    @Test
    public void testRunnerLanded(){
        // Setup
        World world = new World(new Vector2(0, -10), true);
        Runner runner = new Runner(world);
        runner.jump();

        // act
        runner.landed();

        // assert
        assertFalse("runner.getIsJumping should return false", runner.getIsJumping());
    }

    @Test
    public void testEnemySpawn(){
        // Setup
        GameScreen screen = new GameScreen();

        // act
        Enemy enemy = screen.spawnEnemy();

        // assert
        assertTrue("enemy should be an instance of Enemy class", enemy instanceof Enemy);
    }

    @Test
    public void testRemoveHealth(){
        // Setup
        World world = new World(new Vector2(0, -10), true);
        Runner runner = new Runner(world);
        int expected = 2;

        // act
        runner.removeHealth();

        // assert
        assertEquals(expected, runner.getHealth());
    }

    @Test
    public void testAddHealth(){
        // Setup
        World world = new World(new Vector2(0, -10), true);
        Runner runner = new Runner(world);
        int expected = 3;
        runner.removeHealth();

        // act
        runner.addHealth();

        // assert
        assertEquals(expected, runner.getHealth());
    }
}
