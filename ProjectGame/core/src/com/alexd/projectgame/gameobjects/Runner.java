package com.alexd.projectgame.gameobjects;

import com.alexd.projectgame.enums.GameObjectType;
import com.alexd.projectgame.helpers.PhysicsBodyHelper;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Alex on 2015-04-11.
 */
public class Runner extends GameObject {
    /* CONSTANTS */
    public static final float X = 4f;
    public static final float Y = 4f;
    public static final float WIDTH = 1f;
    public static final float HEIGHT = 1f;
    public static final float DENSITY = 0.5f;
    private final Vector2 JUMPING_IMPULSE = new Vector2(0, 5f);

    /* Members */
    private boolean _isJumping;
    private int _health = 3;

    /* Getters & Setters */
    public boolean getIsJumping(){
        return _isJumping;
    }

    public int getHealth(){
        return _health;
    }


    /* Constructor */
    public Runner(World world){
        super(world);
        _body = PhysicsBodyHelper.createRunner(_world, this);
        _gameObjectType = GameObjectType.RUNNER;
        _isJumping = false;
    }

    public Runner(){
        super();
    }


    /* Methods */


    public void jump(){
        if (!_isJumping){
            _body.applyLinearImpulse(JUMPING_IMPULSE, _body.getWorldCenter(), true);
            _isJumping = true;
        }
    }

    public void landed(){

        _isJumping = false;
    }

    public void removeHealth(){
        if (_health != 0){
            _health--;
        }
    }

    public void addHealth(){
        if (_health < 3){
            _health++;
        }
    }



}
