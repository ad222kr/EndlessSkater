package com.alexd.projectgame.gameinterface.mainmenu;

import com.alexd.projectgame.TheGame;
import com.alexd.projectgame.gameinterface.mainmenu.actors.*;
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
public class MainMenuStage extends Stage{

    private TheGame _game;
    private PlayButton _playButton;
    private SoundButton _soundButton;
    private MusicButton _musicButton;
    private OutfitsButton _outfitsButton;
    private Background _background;
    private Table _table;





    public MainMenuStage(TheGame game){
        super(new StretchViewport(TheGame.APP_WIDTH, TheGame.APP_HEIGHT, new OrthographicCamera(TheGame.APP_WIDTH, TheGame.APP_HEIGHT)));

        _game = game;
        //_playButton = new PlayButton(_game, TheGame.APP_WIDTH / 2, TheGame.APP_HEIGHT / 2, 350, 100);

        _soundButton = new SoundButton(_game, 0,0,0,0);
        _musicButton = new MusicButton(_game, 0,0,0,0);

        _background = new Background();

        Label.LabelStyle titleStyle = new Label.LabelStyle();
        titleStyle.font = AssetsManager.getLargeFont();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = AssetsManager.getSmallFont();

        Label titleLabel = new Label("BRUNK RUNNER", titleStyle);
        Label hsLabel = new Label("High Score: " + Integer.toString(_game.getPrefs().getHighScore()), labelStyle);



        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = new TextureRegionDrawable(AssetsManager.getSkin().getRegion("greenbutton-unpressed"));
        style.down = new TextureRegionDrawable(AssetsManager.getSkin().getRegion("greenbutton-pressed"));
        style.font = AssetsManager.getLargeFont();

        TextButton.TextButtonStyle outfitstyle = new TextButton.TextButtonStyle();
        outfitstyle.up = new TextureRegionDrawable(AssetsManager.getSkin().getRegion("standardbutton-unpressed"));
        outfitstyle.down = new TextureRegionDrawable(AssetsManager.getSkin().getRegion("standardbutton-pressed"));
        outfitstyle.font = AssetsManager.getLargeFont();




        _playButton = new PlayButton("PLAY", style, _game);
        _outfitsButton = new OutfitsButton("OUTFITS", outfitstyle, _game);



        _table = new Table();
        _table.setFillParent(true);

        _table.add(titleLabel).colspan(2).pad(0,0,20,0).center();
        _table.row();
        _table.add(hsLabel).colspan(2).pad(0,0,20,0).center();
        _table.row();
        _table.add(_playButton).colspan(2).pad(0, 0, 20, 0);
        _table.row();
        _table.add(_outfitsButton).colspan(2).pad(0, 0, 20, 0);
        _table.row();
        _table.add(_soundButton).center().pad(0, 90,0,0);
        _table.add(_musicButton).center().pad(0,0,0,90);


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


    public TheGame getGame(){
        return _game;
    }
}
