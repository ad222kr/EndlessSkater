/**
 * Created by Alex on 2015-04-16.
 */

package com.alexd.projectgame.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import com.badlogic.gdx.physics.box2d.World;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class GroundTest {

    @Test
    public void createGroundTest(){
        /*
         Testing of the Ground class createPhysicsBody-method
         to see if it returns a physics-body
        */
        Ground ground = new Ground();
        ground.setWorld(new World(new Vector2(0, -10), true));
        Body expected = ground.createPhysicsBody();



        assertTrue(expected instanceof Body);



    }
}
