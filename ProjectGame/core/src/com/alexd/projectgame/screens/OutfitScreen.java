package com.alexd.projectgame.screens;

import com.alexd.projectgame.TheGame;
import com.alexd.projectgame.gameinterface.skinmenu.OutfitStage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

/**
 * Created by Alex on 2015-05-19.
 */
public class OutfitScreen implements Screen {

    private OutfitStage _stage;
    private TheGame _game;

    public OutfitScreen(TheGame game){
        _game = game;
        _stage = new OutfitStage(_game);
        Gdx.input.setInputProcessor(_stage);
        Gdx.input.setCatchBackKey(true);
        _game.getGoogleServices().signIn();

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        _stage.draw(_game.getBatch());
        _stage.act(delta);
    }

    @Override
    public void resize(int width, int height) {
        _stage.getViewport().update(width, height, true);
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
