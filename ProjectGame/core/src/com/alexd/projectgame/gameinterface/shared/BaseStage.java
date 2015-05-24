package com.alexd.projectgame.gameinterface.shared;

import com.alexd.projectgame.TheGame;
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
    protected TheGame _game;

    public BaseStage(TheGame game){
        super(new StretchViewport(TheGame.APP_WIDTH, TheGame.APP_HEIGHT,
                new OrthographicCamera(TheGame.APP_WIDTH, TheGame.APP_HEIGHT)));
        _game = game;
    }

    protected LabelStyle getLabelStyle(boolean hasLargeFont){
        LabelStyle style = new LabelStyle();
        style.font = hasLargeFont ? AssetsManager.getLargeFont() : AssetsManager.getSmallFont();
        return style;
    }

    protected TextButtonStyle getTextButtonStyle(String drawableUp, String drawableDown, boolean hasLargeFont){
        TextButtonStyle style = new TextButtonStyle();
        style.up = new TextureRegionDrawable(AssetsManager.getSkin().getRegion(drawableUp));
        style.down = new TextureRegionDrawable(AssetsManager.getSkin().getRegion(drawableDown));
        style.font = hasLargeFont ? AssetsManager.getLargeFont() : AssetsManager.getSmallFont();
        return style;
    }
}
