package com.alexd.projectgame.graphics;

import com.alexd.projectgame.utils.GameManager;
import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * Created by Alex on 2015-05-21.
 */
public class ScrollingBackground {

    private ScrollingBackgroundLayer [] _layers;
    private float _speed;
    private float _height;
    private float _width;

    public ScrollingBackground(ScrollingBackgroundLayer[] layers, float speed, float width, float height){
        _layers = layers;
        _speed = speed;
        _width = width;
        _height = height;
        setUp();
    }

    public void setUp(){
        for (ScrollingBackgroundLayer layer : _layers){
            layer.setDimensions(_width, _height);
        }
    }

    public void draw(Batch batch, float delta){
        for (ScrollingBackgroundLayer layer : _layers){
            if (GameManager.getInstance().isRunning()){
                layer.update(delta, _speed);
            }
            layer.draw(batch);
        }
    }


}
