package com.alexd.projectgame.userdata;

import com.alexd.projectgame.gameobjects.GameObject;
import com.alexd.projectgame.helpers.GameObjectType;

/**
 * Created by Alex on 2015-04-13.
 */
public abstract class UserData {

    protected GameObjectType gameObjectType;

    public UserData(){

    }

    public GameObjectType getGameObjectType(){
        return gameObjectType;
    }


}
