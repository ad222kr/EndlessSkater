package com.alexd.projectgame.utils;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import javax.xml.soap.Text;

/**
 * Custom sprite-class to scale animations
 */
public class SpriteAnimation extends Animation {

    private float _scaling = 1;
    public SpriteAnimation(float frameDuration, Array<? extends TextureRegion> keyFrames, PlayMode playMode) {
        super(frameDuration, keyFrames, playMode);
    }

    public void setScaling(float value){
        _scaling = value;
    }


    public void draw(float stateTime, Batch batch, float x, float y){
        TextureRegion region = getKeyFrame(stateTime);
        batch.draw(region, x, y, Helpers.convertToMeters(region.getRegionWidth() * _scaling),
                Helpers.convertToMeters(region.getRegionHeight() * _scaling));
    }

}
