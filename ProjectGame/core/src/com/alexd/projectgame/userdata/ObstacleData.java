package com.alexd.projectgame.userdata;

import com.alexd.projectgame.enums.GameObjectType;

/**
 * Created by Alex on 2015-04-19.
 */
public class ObstacleData extends UserData{

    public ObstacleData(){
        super();
        gameObjectType = GameObjectType.OBSTACLE;
    }
}
