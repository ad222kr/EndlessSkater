package com.alexd.projectgame.gameinterface.shared;

import com.alexd.projectgame.utils.AssetsManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Base class for buttons
 */
public abstract class GameButton extends Button {

    protected float _height;
    protected float _width;
    protected float _x;
    protected float _y;
    protected Game _game;

    public GameButton(Game game,  float x, float y, float width, float height, String skinKey){
        _game = game;
        _x = x;
        _y = y;
        _width = width;
        _height = height;
        setUp(skinKey);
    }

    protected void setUp(String skinKey){
        ButtonStyle style = new ButtonStyle();
        style.up = new TextureRegionDrawable(AssetsManager.getAtlasRegion(skinKey));
        setStyle(style);

        setBounds(_x - _width / 2, _y - _height / 2, _width, _height);

        addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                if (clickInBounds(x, y)) {
                    onClick();

                }
                Gdx.app.log("", "" + getTapSquareSize());
                Gdx.app.log("X: " + x, "Y: " + y);
            }
        });


    }

    protected abstract void onClick();

    private boolean clickInBounds(float x, float y){
        return x > 0 && x < getWidth() && y > 0 && y < getHeight();
    }

    public void enable(){
        setVisible(true);
        setTouchable(Touchable.enabled);
    }

    public void disable(){
        setVisible(false);
        setTouchable(Touchable.disabled);
    }
}
