package com.alexd.projectgame.gameinterface.shared;

import com.alexd.projectgame.TheGame;
import com.alexd.projectgame.gameinterface.mainmenu.actors.Background;
import com.alexd.projectgame.gameinterface.mainmenu.actors.LeaderboardButton;
import com.alexd.projectgame.gameinterface.mainmenu.actors.OutfitsButton;
import com.alexd.projectgame.gameinterface.mainmenu.actors.RateButton;
import com.alexd.projectgame.screens.BaseScreen;
import com.alexd.projectgame.utils.AssetsManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * Base class for Stages used in the game
 */
public abstract class BaseStage extends Stage {
    protected BaseScreen _screen;

    public BaseStage(BaseScreen screen){
        super(new StretchViewport(TheGame.APP_WIDTH, TheGame.APP_HEIGHT,
                new OrthographicCamera(TheGame.APP_WIDTH, TheGame.APP_HEIGHT)));
        _screen = screen;
    }

    protected LabelStyle getLabelStyle(boolean hasLargeFont){
        LabelStyle style = new LabelStyle();
        style.font = hasLargeFont ? AssetsManager.getLargeFont() : AssetsManager.getSmallFont();
        return style;
    }

    protected Label getLabel(String labelText, boolean largeFont){
        return new Label(labelText, getLabelStyle(largeFont));
    }

    protected TextButtonStyle getTextButtonStyle(String drawableUp, String drawableDown, boolean hasLargeFont){
        TextButtonStyle style = new TextButtonStyle();
        style.up = new TextureRegionDrawable(AssetsManager.getSkin().getRegion(drawableUp));
        style.down = new TextureRegionDrawable(AssetsManager.getSkin().getRegion(drawableDown));
        style.font = hasLargeFont ? AssetsManager.getLargeFont() : AssetsManager.getSmallFont();
        return style;
    }

    protected SoundButton getSoundButton(){
        return new SoundButton(_screen.getGame());
    }

    protected MusicButton getMusicButton(){
        return new MusicButton(_screen.getGame());
    }

    protected Background getBackground(String backgroundImageKey){
        return new Background(backgroundImageKey);
    }

    protected PlayButton getPlayButton(String labelText){
        return new PlayButton(labelText, getTextButtonStyle("greenbutton-unpressed", "greenbutton-pressed", true),
                _screen.getGame());
    }

    protected ExitButton getMainMenyButton(){
        return new ExitButton("MENU", getTextButtonStyle("standardbutton-unpressed", "standardbutton-pressed", true),
                _screen.getGame());
    }
}
