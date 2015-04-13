package com.alexd.projectgame.userdata;

import com.alexd.projectgame.helpers.GameObjectType;

/**
 * Created by Alex on 2015-04-13.
 */
public class EnemyData extends UserData {

    public EnemyData(){
        super();
        gameObjectType = GameObjectType.ENEMY;
    }
}
