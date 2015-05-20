package com.alexd.projectgame.enums;


import com.alexd.projectgame.utils.Constants;
import com.badlogic.gdx.Gdx;

/**
 * Created by Alex on 2015-05-09.
 */
public enum Difficulty {
    FIRST (1f, 4, 8),
    SECOND(1.05f, 4, 8),
    THIRD(1.1f, 3.5f, 7.5f),
    FOURTH(1.15f, 3.5f, 7.5f),
    FIFTH(1.2f, 3, 7f),
    SIXTH(1.25f, 3, 7f),
    SEVENTH(1.3f , 2.5f, 6.5f),
    EIGHTH(1.35f, 2.5f, 6.5f),
    NINTH(1.4f, 2f, 6f),
    TENTH(1.45f, 2f, 5.5f),
    ELEVENTH(1.5f, 2f, 5f );

    Difficulty(float multiplier, float enemyMinSeconds, float enemyMaxSeconds){

        _multiplier = multiplier;
        _enemyMinSeconds = enemyMinSeconds;
        _enemyMaxSeconds = enemyMaxSeconds;

    }

    private static Difficulty[] vals = values();

    private float _multiplier;
    private float _enemyMinSeconds;
    private float _enemyMaxSeconds;


    public float getMultiplier(){
        return _multiplier;
    }
    public float getEnemyMinSeconds(){ return _enemyMinSeconds; }
    public float getEnemyMaxSeconds(){ return _enemyMaxSeconds; }
    public float getEnemyMaxSpeed(){ return Constants.PLATFORM_SPEED * _multiplier - 3; }
    public float getEnemyMinSpeed(){ return Constants.PLATFORM_SPEED * _multiplier - 1; }
    public float getPlatformAndObstacleSpeed(){ return Constants.PLATFORM_SPEED * _multiplier; } // Same speed


    public Difficulty next(){
        Gdx.app.log("Difficulty: ", ""+vals[ordinal()].toString());
        return vals[ordinal() + 1];
    }

    public boolean isMax(){
        return values()[ordinal()] == values()[values().length - 1];
    }

}
