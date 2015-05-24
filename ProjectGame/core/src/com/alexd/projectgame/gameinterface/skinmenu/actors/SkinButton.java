package com.alexd.projectgame.gameinterface.skinmenu.actors;

import com.alexd.projectgame.TheGame;

import com.alexd.projectgame.gameinterface.shared.TextGameButton;
import com.alexd.projectgame.gameinterface.skinmenu.OutfitStage;


/**
 * Created by Alex on 2015-05-24.
 */
public class SkinButton extends TextGameButton {

    private boolean _isSelected;
    private String _skinName;



    public SkinButton(String text, TextButtonStyle style, TheGame game, String skinName) {
        super(text, style, game);
        _skinName = skinName;
        String selected = _game.getPrefs().getChosenSkin();
        _isSelected = (selected.equals(skinName));

        getLabel().setVisible(_isSelected);



    }


    @Override
    protected void onClick() {
        if (!_isSelected){
            _game.getPrefs().setChosenSkin(_skinName);

        }

        for (SkinButton button : ((OutfitStage)getStage()).getSkinButtons()){
            button.update(button.getSkinName().equals(_game.getPrefs().getChosenSkin()));
        }



    }

    public void update(boolean selected) {
        _isSelected = selected;
        getLabel().setVisible(_isSelected);
    }

    public String getSkinName(){
        return _skinName;
    }
}
