package com.alexd.thegame.screens;

import com.alexd.thegame.stages.GameStage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Alex on 2015-04-06.
 */
public class GameScreen implements Screen {

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    private GameStage stage;

    public GameScreen(){
        stage = new GameStage();
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Should always render things before acting
        stage.draw();
        stage.act(delta);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
