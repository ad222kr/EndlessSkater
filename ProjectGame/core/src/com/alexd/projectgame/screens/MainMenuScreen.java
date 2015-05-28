package com.alexd.projectgame.screens;

import com.alexd.projectgame.TheGame;
import com.alexd.projectgame.gameinterface.mainmenu.MainMenuStage;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by Alex on 2015-05-02.
 */
public class MainMenuScreen extends BaseScreen {
    private MainMenuStage _stage;



    public MainMenuScreen(TheGame game){
        super(game);
        _stage = new MainMenuStage(this);

        Gdx.input.setInputProcessor(_stage);
    }

    @Override
    public void show() {
        _game.getAudioManager().stopMusic();
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
        _stage.getViewport().update(width, height, true); // True sets (0, 0) in bottom left corner.
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        _stage.dispose();
    }
}
