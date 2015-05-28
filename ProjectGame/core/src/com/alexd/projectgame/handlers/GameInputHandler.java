package com.alexd.projectgame.handlers;

import com.alexd.projectgame.entities.Runner;
import com.alexd.projectgame.enums.GameState;
import com.alexd.projectgame.utils.GameManager;
import com.alexd.projectgame.utils.IWorldEventListener;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

import java.sql.Time;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

/**
 * Class handling the input
 */
public class GameInputHandler implements InputProcessor {

    private Runner _runner;

    private IWorldEventListener _worldEventListener;


    public GameInputHandler(Runner runner){
        _runner = runner;
    }


    public void setWorldEventListener(IWorldEventListener worldEventListener) {
        _worldEventListener = worldEventListener;
    }
    @Override
    public boolean keyDown(int keycode) {

        if (keycode == Input.Keys.SPACE && GameManager.getInstance().isRunning() && _runner.canJump()){
            _runner.jump();
            // Strangely sound effects dont cause problems when player on
            // pac with space bar. Only on android and on PC playing with
            // the mouse (touch)
            _worldEventListener.onPlayerJump();
        }
        if (keycode == Input.Keys.BACK){
            GameManager.getInstance().setPaused();
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
        if (GameManager.getInstance().isRunning() && _runner.canJump()){
            _runner.jump();
            // Sound effects causes minor "lagspike" or jitter in the game...
            // Don't know the reason for it and haven't got the time to
            // check it out right now, so game will have to be w/o soundeffects..
            //_worldEventListener.onPlayerJump();
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
