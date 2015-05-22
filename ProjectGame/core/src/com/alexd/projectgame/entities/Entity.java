package com.alexd.projectgame.entities;

import com.alexd.projectgame.enums.EntityType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import com.badlogic.gdx.physics.box2d.World;

/**
 * Abstract base-class for game objects
 */
public abstract class Entity {

    /**
     * Members
     */
    protected World _world;
    protected Body _body;
    protected EntityType _entityType;
    protected float _x;
    protected float _y;
    protected float _width;
    protected float _height;
    protected boolean _flaggedForDeath;
    protected Vector2 _previousPosition;
    protected Vector2 _position;

    public Vector2 getPosition(){
        return _position;
    }

    public Vector2 getPreviousPosition(){
        return _previousPosition;
    }


    public void update(){
        _previousPosition.x = _body.getPosition().x;
        _previousPosition.y = _body.getPosition().y;

        _position.x = _previousPosition.x;
        _position.y = _previousPosition.y;
    }

    /**
     * Constructors
     */
    public Entity(World world, float x, float y, float width, float height){
        _world = world;
        _x = x;
        _y = y;
        _previousPosition = new Vector2(x, y);
        _position = new Vector2(x, y);
        _width = width;
        _height = height;
    }

    public Entity() {}

    protected abstract void initiate();

    public boolean isExpectedType(EntityType type){
        return _entityType == type;
    }

    /**
     * Getters and setters
     */
    public Body getBody(){
        return _body;
    }

    public EntityType getEntityType(){
        return _entityType;
    }

    public float getX(){
        return _position.x;
    }

    public float getY(){
        return _position.y;
    }

    public float getWidth(){
        return _width;
    }

    public float getHeight(){
        return _height;
    }

    public boolean getFlaggedForDeath(){
        return _flaggedForDeath;
    }

    public void setFlaggedForDeath(boolean value){
        _flaggedForDeath = value;
    }

}
