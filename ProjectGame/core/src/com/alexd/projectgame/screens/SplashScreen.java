package com.alexd.projectgame.screens;

import com.alexd.projectgame.TheGame;
import com.alexd.projectgame.gameinterface.mainmenu.actors.Background;
import com.alexd.projectgame.gameinterface.shared.BaseStage;
import com.alexd.projectgame.utils.AssetsManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Timer;

/**
 * Created by Alex on 2015-05-27.
 */
public class SplashScreen extends BaseScreen {

    public static final int SPLASH_TIME = 5;

    private Image _splashImage;

    private Stage _stage;
    private Runnable _splashFinished;


    public SplashScreen(TheGame game){
        super(game);
        _splashImage = new Image(AssetsManager.getBackground("splash"));
        _stage = new Stage();
        _stage.addActor(_splashImage);
        Gdx.input.setInputProcessor(_stage);


        _splashFinished = new Runnable() {
            @Override
            public void run() {
                _game.setScreen(new MainMenuScreen(_game));
            }
        };
        _splashImage.addAction(Actions.sequence(Actions.fadeOut(0.0001f), Actions.fadeIn(2f), Actions.fadeOut(2f),  Actions.run(_splashFinished)));
    }




    @Override
    public void show() {


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        _stage.act(delta);
        _stage.draw();

        if (Gdx.input.isTouched()){
            _game.setScreen(new MainMenuScreen(_game));
        }


        /*if (!_timerOn){
            _timerOn = true;

            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    _game.setScreen(new MainMenuScreen(_game));
                }
            }, SPLASH_TIME);
        }
        else if(Gdx.input.isTouched() && AssetsManager.hasLoaded()){
            Timer.instance().clear();
            _game.setScreen(new MainMenuScreen(_game));
        }*/
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

    }
}
