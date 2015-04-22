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
    /* Members */
    protected World _world;
    protected Body _body;
    protected GameObjectType _gameObjectType;

    /* Get & set */

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

    /* Constructor */
    public GameObject (World world){
        _world = world;
    }

    public GameObject () {}

    /* Abstract methods */

    public abstract float getWidth();
    public abstract float getHeight();


    /* Methods */
    public abstract void initiate();



    public GameObjectType getGameObjectType (){
        return _gameObjectType;
    }

    public boolean isExpectedType(GameObjectType type){
        return _gameObjectType == type;
    }




}
