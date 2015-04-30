/**
 * Created by Alex on 2015-04-20.
 */


import com.alexd.projectgame.gameobjects.Enemy;
import com.alexd.projectgame.gameobjects.GameObject;
import com.alexd.projectgame.gameobjects.Obstacle;
import com.alexd.projectgame.gameobjects.Runner;
import com.alexd.projectgame.screens.GameScreen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class IntegrationTests {

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

    @Test
    public void testEnemySpawn(){
        // Setup
        GameScreen screen = new GameScreen();

        // act
        GameObject enemy = screen.spawnEnemy();

        // assert.
        assertTrue("enemy should be an instance of Enemy class", enemy instanceof Enemy || enemy instanceof Obstacle);
    }
}
