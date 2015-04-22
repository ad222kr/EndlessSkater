package com.alexd.projectgame.handlers;

import com.alexd.projectgame.gameobjects.Runner;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

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
        return false;
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


        if(!_runner.getIsJumping()){

            Vector2 vel = _runner.getBody().getLinearVelocity();
            float desiredVel = Math.max(vel.y + 0.1f, 8.5f);
            float velChange = desiredVel - vel.y;
            float impulse = _runner.getBody().getMass() * velChange;

            _runner.getBody().applyLinearImpulse(new Vector2(0f, impulse), _runner.getBody().getWorldCenter(), true);


            _runner.setIsJumping(true);



        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        /* For canceling the jump on touch up. Doesn't feel right,
            TODO: Implement this better

        if (_runner.getIsJumping() && !_runner.isFalling()){
            Vector2 vel = _runner.getBody().getLinearVelocity();
            float desiredVel = vel.y * -0.0098f;
            float velChange = desiredVel - vel.y;
            float impulse = _runner.getBody().getMass() * velChange;
            _runner.getBody().applyLinearImpulse(new Vector2(0f, impulse), _runner.getBody().getWorldCenter(), true);
        }*/


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
