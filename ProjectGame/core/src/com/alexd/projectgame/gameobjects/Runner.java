package com.alexd.projectgame.gameobjects;

import com.alexd.projectgame.enums.GameObjectType;
import com.alexd.projectgame.helpers.PhysicsFactory;
import com.badlogic.gdx.Gdx;
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
    public static final float HEIGHT = 1.5f;
    public static final float DENSITY = 0.5f;
    public static final int MAX_HEALTH = 3;
    private final Vector2 JUMPING_IMPULSE = new Vector2(0, 14f);


    /* Members */
    private int _health;
    private boolean _isJumping;
    private boolean _isOnGround;


    /* Getters & Setters */

    public int getHealth(){ return _health; }

    public void setHealth(int value){ _health = value; }

    public boolean getIsJumping(){ return _isJumping; }

    public void setIsJumping(boolean value){ _isJumping = value; }

    @Override
    public float getWidth() { return WIDTH; }

    @Override
    public float getHeight() { return HEIGHT; }

    public void setIsOnGround(boolean value){
        _isOnGround = value;
    }

    /* Constructor */
    public Runner(World world){
        super(world);
        initiate();
    }

    public Runner(){

    }


    @Override
    public void initiate() {
        _body = PhysicsFactory.createRunner(_world, this);
        _gameObjectType = GameObjectType.RUNNER;
        _isJumping = false;
        _isOnGround = true;
        _health = MAX_HEALTH;
    }

    public void jump(){
        Gdx.app.log("Velocity: ", ""+_body.getLinearVelocity().y);
        if(!_isJumping && _isOnGround){

            // Jumping code translated from http://www.iforce2d.net/b2dtut/constant-speed
            Vector2 vel = getBody().getLinearVelocity();
            float desiredVel = Math.max(vel.y + 0.1f, 8.5f);
            float velChange = desiredVel - vel.y;
            float impulse = getBody().getMass() * velChange;

            getBody().applyLinearImpulse(new Vector2(0f, impulse), getBody().getWorldCenter(), true);

            _isJumping = true;
            _isOnGround = false;

        }
    }

    public void landed(){
        _isJumping = false;
        _isOnGround = true;
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
