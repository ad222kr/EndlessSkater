package com.alexd.projectgame.gameinterface.shared;

import com.alexd.projectgame.TheGame;
import com.alexd.projectgame.gameinterface.gamehud.actors.PauseButton;
import com.alexd.projectgame.utils.AssetsManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
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
    protected TheGame _game;
    protected Skin _skin;

    public GameButton(TheGame game, float x, float y, float width, float height){
        _game = game;
        _x = x;
        _y = y;
        _width = width;
        _height = height;
        _skin = AssetsManager.getSkin();
        setUp();
        setButtonStyle();


    }

    public GameButton(TheGame game){
        // used when the button is inside a table, dont need to specify bounds then
        this(game, 0, 0, 0, 0);
    }

    protected void setUp(){
        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                onClick();

            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                super.touchDown(event, x, y, pointer, button);
                if (_game.getPrefs().isSoundEnabled()){
                    _game.getAudioManager().playClickSound();
                }
                return true;
            }
        });


    }

    protected abstract void onClick();
    protected abstract String getUpDrawableKey();
    protected abstract String getDownDrawableKey();

    protected void setButtonStyle(){
        ButtonStyle style = new ButtonStyle();
        style.up = _skin.getDrawable(getUpDrawableKey());
        style.down = _skin.getDrawable(getDownDrawableKey());
        setStyle(style);
    }


    public void enable(){
        setVisible(true);
        setTouchable(Touchable.enabled);
    }

    public void hide(){
        setVisible(false);
        setTouchable(Touchable.disabled);
    }

    public void disableClick(){
        setTouchable(Touchable.disabled);
    }


}
