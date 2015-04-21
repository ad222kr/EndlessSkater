package com.alexd.projectgame.gameobjects;

import com.alexd.projectgame.enums.GameObjectType;
import com.alexd.projectgame.helpers.PhysicsFactory;
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
    public static final float X = 4f;
    public static final float Y = 4f;
    public static final float WIDTH = 1.5f;
    public static final float HEIGHT = 2f;
    public static final float DENSITY = 0.5f;
    public static final int MAX_HEALTH = 3;
    private final Vector2 JUMPING_IMPULSE = new Vector2(0, 14f);

    /* Members */
    private int _health;
    private int _jumpCount;
    private boolean _isJumping;
    private float _jumpTimer;

    /* Getters & Setters */


    public int getHealth(){
        return _health;
    }

    public void setHealth(int value){
        _health = value;
    }

    public float getJumpTimer(){
        return _jumpTimer;
    }

    public boolean getIsJumping(){
        return _isJumping;
    }


    /* Constructor */
    public Runner(World world){
        super(world);
        _body = PhysicsFactory.createRunner(_world, this);
        _gameObjectType = GameObjectType.RUNNER;
        _isJumping = false;
        _health = MAX_HEALTH;
        _jumpCount = 0;

    }

    public Runner(){

    }


    /* Methods */

    public void incrementJumpTimer(float value){
        _jumpTimer += value;
    }

    public void jump(){
        if (!_isJumping){
            _body.applyLinearImpulse(JUMPING_IMPULSE, _body.getWorldCenter(), true);
            _isJumping = true;
        }
    }

    public void landed(){

        _isJumping = false;
        _jumpTimer = 0;

    }

    public boolean isFalling(){

        return _body.getLinearVelocity().y < 0;
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
