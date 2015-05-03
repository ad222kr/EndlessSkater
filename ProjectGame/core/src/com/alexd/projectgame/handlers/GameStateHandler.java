package com.alexd.projectgame.handlers;

import com.alexd.projectgame.enums.GameState;

/**
 * Created by Alex on 2015-05-03.
 */
public class GameStateHandler {
    private GameState _state;

    public GameStateHandler(){
        _state = GameState.INDEFINITE;
    }

    public GameState getState(){
        return _state;
    }

    public void setState(GameState value){
        _state = value;
    }
}
