package com.alexd.projectgame.gameinterface.mainmenu;

import com.alexd.projectgame.TheGame;
import com.alexd.projectgame.gameinterface.mainmenu.actors.PlayButton;
import com.alexd.projectgame.gameinterface.shared.GameButton;
import com.alexd.projectgame.utils.AssetsManager;
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
    private PlayButton _playButton;


    public MainMenuStage(Game game){
        super(new StretchViewport(TheGame.APP_WIDTH, TheGame.APP_HEIGHT));
        _game = game;
        _playButton = new PlayButton(_game, TheGame.APP_WIDTH / 2, TheGame.APP_HEIGHT / 2, 350, 100, "play-button");
        Gdx.input.setInputProcessor(this);



        addActor(_playButton);




    }


}
