package com.alexd.projectgame.userdata;

import com.alexd.projectgame.enums.GameObjectType;

/**
 * Base class for the UserData-classe that will hold
 * information needed by the physicsbody
 */
public abstract class UserData {

    protected GameObjectType gameObjectType;

    public UserData(){

    }

    public boolean isExpectedType(GameObjectType type){
        return gameObjectType == type;
    }
}