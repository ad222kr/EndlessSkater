package com.alexd.projectgame.enums;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Alex on 2015-04-21.
 */
public enum EnemyType {
    FAST_SPEED(new Vector2(-6f, 0)),
    MEDIUM_SPEED(new Vector2(-5f, 0)),
    SLOW_SPEED(new Vector2(-4f, 0));

    private Vector2 _speed;

    EnemyType(Vector2 speed){
        _speed = speed;
    }

    public Vector2 getSpeed(){
        return _speed;
    }

    public static EnemyType getRandomValue(){
        // get random enum value http://stackoverflow.com/a/8114214

        return values()[(int) (Math.random() * values().length)];
    }


}
