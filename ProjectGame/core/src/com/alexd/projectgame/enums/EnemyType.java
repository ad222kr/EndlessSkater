package com.alexd.projectgame.enums;

import com.badlogic.gdx.math.Vector2;

/**
 * Enum for different enemy-types. They will have different speeds
 */
public enum EnemyType {
    /**
     * EnemyTypes
     */
    FAST_SPEED(new Vector2(-8f, 0)),
    MEDIUM_SPEED(new Vector2(-7f, 0)),
    SLOW_SPEED(new Vector2(-6f, 0));

    private Vector2 _speed;

    EnemyType(Vector2 speed){
        _speed = speed;
    }

    public Vector2 getSpeed(){
        return _speed;
    }

    /**
     * @return - A random value of this enum
     */
    public static EnemyType getRandomValue(){
        // get random enum value http://stackoverflow.com/a/8114214

        return values()[(int) (Math.random() * values().length)];
    }


}
