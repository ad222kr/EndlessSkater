package com.alexd.projectgame.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Alex on 2015-04-13. Placeholder for a Game Over screen
 */
public class GameOverScreen implements Screen, InputProcessor {
    private SpriteBatch _batch;
    private BitmapFont _font;
    private Game _game;

    public GameOverScreen(Game game){
        this._game = game;

        Gdx.input.setInputProcessor(this);
    }
    @Override
    public void show() {
        _batch = new SpriteBatch();
        _font = new BitmapFont();
        _font.setColor(Color.RED);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        _batch.begin();
        _font.draw(_batch, "Game over", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        _batch.end();
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
        Gdx.app.log("DISPOSE CALLED ON: ", "GameOverScreen");
        _batch.dispose();
        _font.dispose();


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
        _game.setScreen(new GameScreen(_game));
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
