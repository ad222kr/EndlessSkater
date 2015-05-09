package com.alexd.projectgame.handlers;

import com.alexd.projectgame.enums.GameState;
import com.alexd.projectgame.entities.Runner;
import com.alexd.projectgame.utils.GameManager;
import com.alexd.projectgame.utils.GameStateManager;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

/**
 * Class handling the input
 */
public class GameInputHandler implements InputProcessor {

    private Runner _runner;


    public GameInputHandler(Runner runner){
        _runner = runner;
    }

    @Override
    public boolean keyDown(int keycode) {

        if (keycode == Input.Keys.SPACE && GameManager.getInstance().isRunning()){
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
        if (GameManager.getInstance().isRunning()){
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
