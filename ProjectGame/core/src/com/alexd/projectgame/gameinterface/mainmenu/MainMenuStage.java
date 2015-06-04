package com.alexd.projectgame.gameinterface.mainmenu;

import com.alexd.projectgame.gameinterface.mainmenu.actors.*;
import com.alexd.projectgame.gameinterface.shared.BaseStage;
import com.alexd.projectgame.screens.BaseScreen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;


/**
 * Created by Alex on 2015-05-01.
 */
public class MainMenuStage extends BaseStage{

    private Table _table;
    private Background _background;

    public MainMenuStage(BaseScreen screen){
        super(screen);
        _background = getBackground("menubg");
        _table = getTable();
        addActor(_background);
        addActor(_table);
    }

    private Table getTable(){
        Table table = new Table();
        table.setFillParent(true);

        table.add(getLabel("ENDLESS SKATER", true)).colspan(4).pad(0,0,20,0).center().row();
        table.add(getLabel("High Score: " + Integer.toString(_screen.getGame().getPrefs().getHighScore()), false))
                .colspan(4).pad(0,0,20,0).center().row();
        table.add(getPlayButton("PLAY")).colspan(4).pad(0, 0, 20, 0).row();
        table.add(getOutfitsButton()).colspan(4).pad(0, 0, 20, 0).row();
        table.add(getSoundButton()).center().pad(0, 0, 0, 0);
        table.add(getRateButton()).center().pad(0, 0, 0, 0);
        table.add(getLeaderboardButton()).center().pad(0,0,0,0);
        table.add(getMusicButton()).center().pad(0, 0, 0, 0);

        table.setTouchable(Touchable.enabled);

        return table;
    }

    public void draw(Batch batch){
        super.draw();
        batch.begin();
        _background.draw(batch, 0);
        _table.draw(batch, 0);
        batch.end();
    }

    private OutfitsButton getOutfitsButton(){
        return new OutfitsButton("OUTFITS", getTextButtonStyle("standardbutton-unpressed", "standardbutton-pressed",
                true), _screen.getGame());
    }

    private RateButton getRateButton(){
        return new RateButton(_screen.getGame());
    }

    private LeaderboardButton getLeaderboardButton(){
        return new LeaderboardButton(_screen.getGame());
    }
}
