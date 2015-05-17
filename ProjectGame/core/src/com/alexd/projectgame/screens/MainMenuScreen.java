package com.alexd.projectgame.screens;

import com.alexd.projectgame.gameinterface.mainmenu.MainMenuStage;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by Alex on 2015-05-02.
 */
public class MainMenuScreen implements Screen {
    private Stage _stage;
    private Game _game;

    public MainMenuScreen(Game game){
        _game = game;
        _stage = new MainMenuStage(_game);
        Gdx.input.setInputProcessor(_stage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        _stage.draw();
        _stage.act(delta);
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
        dispose();
    }

    @Override
    public void dispose() {
        _stage.dispose();
    }
}
