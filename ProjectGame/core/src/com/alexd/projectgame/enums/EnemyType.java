package com.alexd.projectgame.enums;

import com.alexd.projectgame.utils.GameManager;
import com.badlogic.gdx.math.Vector2;

/**
 * Enum for different enemy-types. They will have different speeds
 */
public enum EnemyType {
    /**
     * EnemyTypes
     */
    FAST_SPEED(-9f),
    MEDIUM_SPEED(-8f),
    SLOW_SPEED(-7f);

    private float _speed;

    EnemyType(float speed){
        _speed = speed;
    }

    public float getSpeed(){
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
