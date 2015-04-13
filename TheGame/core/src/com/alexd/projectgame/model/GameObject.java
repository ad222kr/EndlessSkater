package com.alexd.projectgame.model;

import com.alexd.projectgame.helpers.GameObjectType;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Base class for the game-objets
 */
public abstract class GameObject {
    /* Members */
    protected World world;
    protected Body body;


    /* get/set */
    public Body getBody(){
        return body;
    }

    /* Constructor */
    public GameObject (World world){
        this.world = world;
    }


}
