package com.alexd.projectgame.gameinterface.mainmenu;

import com.alexd.projectgame.TheGame;
import com.alexd.projectgame.helpers.AssetsManager;
import com.alexd.projectgame.screens.GameScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;


/**
 * Created by Alex on 2015-05-01.
 */
public class MainMenuStage extends Stage{

    private Game _game;
    private Skin _skin;

    public MainMenuStage(Game game){
        super(new StretchViewport(TheGame.APP_WIDTH, TheGame.APP_HEIGHT));
        _game = game;
        Gdx.input.setInputProcessor(this);


        /*_skin = new Skin();

        _skin.add("play-button", AssetsManager.getAtlasRegion("play-button"));*/

        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.imageUp = new TextureRegionDrawable(AssetsManager.getAtlasRegion("play-button"));

        ImageButton button = new ImageButton(style);
        button.setBounds(TheGame.APP_WIDTH / 2 - button.getWidth() / 2, TheGame.APP_HEIGHT / 2 - button.getHeight() / 2,
                button.getWidth(), button.getHeight());

        addActor(button);


        button.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                _game.setScreen(new GameScreen(_game));
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("up");
            }
        });

    }


}
