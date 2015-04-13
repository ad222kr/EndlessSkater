package com.alexd.projectgame.userdata;


import com.alexd.projectgame.helpers.GameObjectType;

/**
 * Base class for the UserData-classe that will hold
 * information needed by the physicsbody
 */
public abstract class UserData {

    protected GameObjectType gameObjectType;

    public UserData(){

    }

    public GameObjectType getGameObjectType(){
        return gameObjectType;
    }


    public boolean isRunner(){
        return gameObjectType == GameObjectType.RUNNER;
    }

    public boolean isGround(){
        return gameObjectType == GameObjectType.GROUND;
    }


    public boolean isEnemy(){
        return gameObjectType == GameObjectType.ENEMY;
    }


}
