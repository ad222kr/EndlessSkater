package com.alexd.projectgame.utils;

import com.alexd.projectgame.enums.Difficulty;
import com.alexd.projectgame.enums.GameState;
import com.badlogic.gdx.Gdx;

import java.util.Random;

/**
 * Singleton handling the state and difficulty of the game
 */
public class GameManager {
    private static GameManager ourInstance = new GameManager();

    public static GameManager getInstance() { return ourInstance; }

    private GameState _gameState;
    private Difficulty _difficulty;

    private GameManager() {
        _gameState = GameState.INDEFINITE;
        _difficulty = Difficulty.FIRST;
    }

    public GameState getState() { return _gameState; }

    public void setState(GameState value) { _gameState = value; }

    public void nextDifficulty() {

        _difficulty = _difficulty.next();
        Gdx.app.log("Difficulty: ", getDiffName());
    }

    public void resetDifficulty() { _difficulty = Difficulty.FIRST; }

    public float getEnemyMaxSeconds() { return _difficulty.getEnemyMaxSeconds(); }

    public float getEnemyMinSeconds() { return _difficulty.getEnemyMinSeconds(); }

    public float getMultiplyer() { return _difficulty.getMultiplier(); }

    public boolean isMaxDifficulty() { return _difficulty.isMax(); }

    public boolean isRunning() { return _gameState == GameState.RUNNING; }

    public boolean isPaused() { return _gameState == GameState.PAUSED; }

    public void setRunning() { _gameState = GameState.RUNNING; }

    public void setPaused() { if (_gameState == GameState.RUNNING) _gameState = GameState.PAUSED; }

    public float getEnemySpeed() {
        return Helpers.getRandomFloat(_difficulty.getEnemyMinSpeed(), _difficulty.getEnemyMaxSpeed());
    }

    public float getStaticObjectSpeed() { return _difficulty.getPlatformAndObstacleSpeed(); }

    public String getDiffName(){ return _difficulty.name(); }
}