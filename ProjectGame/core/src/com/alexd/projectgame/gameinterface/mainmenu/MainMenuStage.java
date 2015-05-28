package com.alexd.projectgame.gameinterface.mainmenu;

import com.alexd.projectgame.TheGame;
import com.alexd.projectgame.gameinterface.mainmenu.actors.*;
import com.alexd.projectgame.gameinterface.shared.BaseStage;
import com.alexd.projectgame.screens.BaseScreen;
import com.alexd.projectgame.utils.AssetsManager;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import javafx.scene.control.Tab;


/**
 * Created by Alex on 2015-05-01.
 */
public class MainMenuStage extends BaseStage{

    private PlayButton _playButton;
    private SoundButton _soundButton;
    private MusicButton _musicButton;
    private OutfitsButton _outfitsButton;
    private Background _background;
    private Table _table;





    public MainMenuStage(BaseScreen screen){
        super(screen);

        _soundButton = new SoundButton(_screen.getGame(), 0,0,0,0);
        _musicButton = new MusicButton(_screen.getGame(), 0,0,0,0);
        _background = new Background("menubg");

        Label.LabelStyle titleStyle = getLabelStyle(true);
        Label.LabelStyle labelStyle = getLabelStyle(false);

        Label titleLabel = new Label("ENDLESS SKATER", titleStyle);
        Label hsLabel = new Label("High Score: " + Integer.toString(_screen.getGame().getPrefs().getHighScore()), labelStyle);

        TextButton.TextButtonStyle style = getTextButtonStyle("greenbutton-unpressed", "greenbutton-pressed", true);
        TextButton.TextButtonStyle outfitstyle = getTextButtonStyle("standardbutton-unpressed", "standardbutton-pressed", true);

        _playButton = new PlayButton("PLAY", style, _screen.getGame());
        _outfitsButton = new OutfitsButton("OUTFITS", outfitstyle, _screen.getGame());

        _table = new Table();
        _table.setFillParent(true);

        _table.add(titleLabel).colspan(4).pad(0,0,20,0).center();
        _table.row();
        _table.add(hsLabel).colspan(4).pad(0,0,20,0).center();
        _table.row();
        _table.add(_playButton).colspan(4).pad(0, 0, 20, 0);
        _table.row();
        _table.add(_outfitsButton).colspan(4).pad(0, 0, 20, 0);
        _table.row();
        _table.add(_soundButton).center().pad(0, 0, 0, 0);
        _table.add(new RateButton(_screen.getGame())).center().pad(0, 0, 0, 0);
        _table.add(new LeaderboardButton(_screen.getGame())).center().pad(0,0,0,0);
        _table.add(new MusicButton(_screen.getGame(),0,0,0,0)).center().pad(0, 0, 0, 0);


        _table.setTouchable(Touchable.enabled);
        addActor(_background);
        addActor(_table);
    }

    public void draw(Batch batch){
        super.draw();
        batch.begin();
        _background.draw(batch, 0);
        _table.draw(batch, 0);
        batch.end();
    }

}
