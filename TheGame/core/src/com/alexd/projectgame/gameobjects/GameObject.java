package com.alexd.projectgame.gameobjects;

import com.alexd.projectgame.enums.GameObjectType;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Base class for the game-objets
 */
public abstract class GameObject {
    /* Members */
    protected World world;
    protected Body body;
    protected GameObjectType gameObjectType;

    /* get/set */
    public Body getBody(){
        return body;
    }

    public GameObjectType getGameObjectType(){
        return gameObjectType;
    }


    public GameObject (World world){
        this.world = world;
    }


}
