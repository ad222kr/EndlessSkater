package com.alexd.projectgame.gameobjects;

import com.alexd.projectgame.enums.GameObjectType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Abstract base-class for game objects
 */
public abstract class GameObject {

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

    /**
     * Constructors
     */
    public GameObject (World world){
        _world = world;
    }

    public GameObject () {}




    public abstract void initiate(float x, float y, float width, float height);

    public void setupMembers(float x, float y, float width, float height){
        _x = x;
        _y = y;
        _width = width;
        _height = height;
    }

    /**
     * @param type - GameObjectType to compare against
     * @return - wheter the object is specified type
     */
    public boolean isExpectedType(GameObjectType type){
        return _gameObjectType == type;
    }

    /**
     * Getters and setters
     */
    public Body getBody(){
        return _body;
    }

    public void setBody(Body body){
        _body = body;
    }

    public World getWorld(){
        return _world;
    }

    public void setWorld(World world){
        _world = world;
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




}
