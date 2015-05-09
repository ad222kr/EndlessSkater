package com.alexd.projectgame.utils;


import com.alexd.projectgame.enums.Difficulty;
import com.alexd.projectgame.enums.GameState;

/**
 * Manages the state and difficulty of the game
 */
public class GameStateManager {
    private GameState _state;
    private Difficulty _difficulty;

    public GameStateManager(){
        _state = GameState.INDEFINITE;
        _difficulty = Difficulty.FIRST;
    }

    public GameState getState(){
        return _state;
    }

    public void setState(GameState value){
        _state = value;
    }

    public void nextDifficulty(){
        _difficulty = _difficulty.next();
    }

    public float getMultiplyer(){
        return _difficulty.getMultiplier();
    }

    public boolean isMaxDifficulty(){
        return _difficulty.isMax();
    }


}
