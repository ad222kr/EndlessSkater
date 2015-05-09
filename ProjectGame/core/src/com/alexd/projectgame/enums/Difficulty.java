package com.alexd.projectgame.enums;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Alex on 2015-05-09.
 */
public enum Difficulty {
    FIRST (1f),
    SECOND(1.1f),
    THIRDS(1.2f),
    FOURTH(1.3f),
    FIFTH(1.4f),
    SIXTH(1.5f),
    SEVENTH(1.6f),
    EIGHTH(1.7f),
    NINTH(1.8f),
    TENTH(1.9f);

    private float _multiplier;

    public float getMultiplier(){
        return _multiplier;
    }

    Difficulty(float multiplier){
        _multiplier = multiplier;
    }

    public Difficulty next(){
        return values()[ordinal() + 1];
    }

    public void nextDifficulty(){
        _multiplier += 0.1f;
    }

    public void reset(){
        _multiplier = 1f;
    }

    public boolean isMax(){
        return values()[ordinal()] == values()[values().length - 1];
    }

}
