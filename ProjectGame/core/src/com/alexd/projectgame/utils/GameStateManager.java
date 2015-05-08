package com.alexd.projectgame.utils;

import com.alexd.projectgame.enums.GameState;

/**
 * Created by Alex on 2015-05-03.
 */
public class GameStateManager {
    private GameState _state;

    public GameStateManager(){
        _state = GameState.INDEFINITE;
    }

    public GameState getState(){
        return _state;
    }

    public void setState(GameState value){
        _state = value;
    }
}
