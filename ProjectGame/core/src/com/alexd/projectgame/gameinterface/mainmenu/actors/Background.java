package com.alexd.projectgame.gameinterface.mainmenu.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Alex on 2015-05-20.
 */
public class Background extends Actor {

    private Texture _texture;

    public Background(){
        _texture = new Texture(Gdx.files.internal("menubg.png"));
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        super.draw(batch, parentAlpha);
        batch.draw(_texture, 0, 0, _texture.getWidth(), _texture.getHeight());
    }

}
