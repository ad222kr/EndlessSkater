package com.alexd.projectgame.entities;

import com.alexd.projectgame.enums.GameObjectType;
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
    protected GameObjectType _gameObjectType;
    protected float _x;
    protected float _y;
    protected float _width;
    protected float _height;
    protected boolean _flaggedForDeath;

    /**
     * Constructors
     */
    public Entity(World world, float x, float y, float width, float height){
        _world = world;
        _x = x;
        _y = y;
        _width = width;
        _height = height;
    }

    public Entity() {}


    protected abstract void initiate(float x, float y, float width, float height);

    public boolean isExpectedType(GameObjectType type){
        return _gameObjectType == type;
    }

    /**
     * Getters and setters
     */
    public Body getBody(){
        return _body;
    }

    public GameObjectType getGameObjectType (){
        return _gameObjectType;
    }

    public float getX(){
        return _x;
    }

    public float getY(){
        return _y;
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
