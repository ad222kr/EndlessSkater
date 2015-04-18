package com.alexd.projectgame.model;

import com.alexd.projectgame.helpers.PhysicsBodyHelper;
import com.alexd.projectgame.userdata.RunnerData;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Alex on 2015-04-11.
 */
public class Runner extends GameObject {
    /* CONSTANTS */
    public static final float X = 3f;
    public static final float Y = 4f;
    public static final float WIDTH = 2f;
    public static final float HEIGHT = 2f;
    public static final float DENSITY = 0.5f;
    private final Vector2 JUMPING_IMPULSE = new Vector2(0, 13f);

    /* Members */
    private boolean isJumping;
    private int health = 3;

    /* Getters & Setters */
    public boolean getIsJumping(){
        return isJumping;
    }



    /* Constructor */
    public Runner(World world){
        super(world);
        body = PhysicsBodyHelper.createRunner(this.world);
        isJumping = false;
    }

    /* get set */
    public int getHealth(){
        return health;
    }

    /* Methods */


    public void jump(){
        if (!isJumping){
            body.applyLinearImpulse(JUMPING_IMPULSE, body.getWorldCenter(), true);
            isJumping = true;
        }
    }

    public void landed(){

        isJumping = false;
    }

    public void removeHealth(){
        if (health != 0){
            health--;
        }
    }

    public void addHealth(){
        if (health < 3){
            health++;
        }
    }



}
