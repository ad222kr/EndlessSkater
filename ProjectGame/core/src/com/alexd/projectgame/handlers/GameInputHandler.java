package com.alexd.projectgame.handlers;

import com.alexd.projectgame.enums.GameState;
import com.alexd.projectgame.gameobjects.Runner;
import com.alexd.projectgame.screens.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

import java.security.Key;

/**
 * Class handling the input
 */
public class GameInputHandler implements InputProcessor {

    private Runner _runner;
    private GameStateHandler _gsh;


    public GameInputHandler(Runner runner, GameStateHandler gsh){
        _runner = runner;
        _gsh = gsh;
    }

    @Override
    public boolean keyDown(int keycode) {

        if (keycode == Input.Keys.SPACE && _gsh.getState() == GameState.RUNNING){
            _runner.jump();
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (_gsh.getState() == GameState.RUNNING){
            _runner.jump();

        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

}
