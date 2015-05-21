package com.alexd.projectgame.graphics;

import com.alexd.projectgame.utils.Helpers;
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


    public void draw(float stateTime, Batch batch, float x, float y, boolean flip){
        TextureRegion region = getKeyFrame(stateTime);

        float width = region.getRegionWidth();
        float height = region.getRegionHeight();

        batch.draw(region, flip ? x + Helpers.convertToMeters(region.getRegionWidth()) : x, y,
                Helpers.convertToMeters(flip ? -region.getRegionWidth() * _scaling : region.getRegionWidth() * _scaling),
                Helpers.convertToMeters(region.getRegionHeight() * _scaling));
    }

}
