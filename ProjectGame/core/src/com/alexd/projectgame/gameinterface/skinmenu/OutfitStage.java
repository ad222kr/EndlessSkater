package com.alexd.projectgame.gameinterface.skinmenu;

import com.alexd.projectgame.TheGame;
import com.alexd.projectgame.gameinterface.gamehud.actors.ExitButton;
import com.alexd.projectgame.gameinterface.mainmenu.actors.Background;
import com.alexd.projectgame.gameinterface.mainmenu.actors.PlayButton;
import com.alexd.projectgame.gameinterface.shared.BaseStage;
import com.alexd.projectgame.gameinterface.skinmenu.actors.SkinButton;
import com.alexd.projectgame.screens.BaseScreen;
import com.alexd.projectgame.screens.MainMenuScreen;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Alex on 2015-05-24.
 */
public class OutfitStage extends BaseStage{


    private Background _background;
    private Table _table;
    private SkinButton[] _skinButtons;

    public OutfitStage(BaseScreen screen) {
        super(screen);
        _skinButtons = new SkinButton[]{
            new SkinButton("SELECTED", getTextButtonStyle("player-rus-unpressed", "player-rus-pressed", false),
                    _screen.getGame(), "player-rus"),
            new SkinButton("SELECTED", getTextButtonStyle("player-swe-unpressed", "player-swe-pressed", false),
                    _screen.getGame(), "player-swe"),
            new SkinButton("SELECTED", getTextButtonStyle("player-can-unpressed", "player-can-pressed", false),
                    _screen.getGame(), "player-can"),
            new SkinButton("SELECTED", getTextButtonStyle("player-usa-unpressed", "player-usa-pressed", false),
                    _screen.getGame(), "player-usa")
        };
        _background = new Background("bg1");
        _table = new Table();
        _table.setFillParent(true);

        _table.add(new Label("CHOOSE OUTFIT", getLabelStyle(true))).pad(50,0,0, 0).colspan(4).row();
        for (SkinButton button : _skinButtons){
            _table.add(button).pad(50, 0,30, 0);
        }

        _table.row();



        _table.add(new ExitButton("MENU", getTextButtonStyle("standardbutton-unpressed", "standardbutton-pressed",
                true), _screen.getGame())).colspan(2).pad(0,0,50,50);
        _table.add(new PlayButton("PLAY", getTextButtonStyle("greenbutton-unpressed", "greenbutton-pressed",
                true), _screen.getGame())).colspan(2).pad(0,50,50,0);

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



    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.BACK){
            _screen.getGame().setScreen(new MainMenuScreen(_screen.getGame()));
        }
        return true;
    }

    public SkinButton[] getSkinButtons(){
        return _skinButtons;
    }
}
