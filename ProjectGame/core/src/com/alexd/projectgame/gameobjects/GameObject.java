package com.alexd.projectgame.gameobjects;

import com.badlogic.gdx.physics.box2d.Body;

import com.badlogic.gdx.physics.box2d.World;

/**
 * Abstract base-class for game objects
 */
public abstract class GameObject {
    /* Members */
    protected World world;
    protected Body body;

    /* Get & set */

    public Body getBody(){
        return body;
    }

    public void setBody(Body body){
        this.body = body;
    }

    public World getWorld(){
        return world;
    }

    public void setWorld(World world){
        this.world = world;
    }

    /* Constructor */
    public GameObject (World world){
        this.world = world;
    }

    public GameObject () {}



}
