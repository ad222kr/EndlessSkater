package com.alexd.projectgame.graphics;

import com.alexd.projectgame.TheGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;


/**
 * Created by Alex on 2015-05-21.
 */
public class ScrollingBackgroundLayer {

    private float _speedScalar;
    private float _height;
    private float _width;
    private Texture _texture;
    private Rectangle _bounds1;
    private Rectangle _bounds2;

    public void setDimensions(float width, float height){
        _width = width;
        _height = height;
    }

    public ScrollingBackgroundLayer(float speedScalar, Texture texture){
        _speedScalar = speedScalar;
        _texture = texture;
        _texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        setBounds();
    }

    public void setBounds(){
        _bounds1 = new Rectangle(0 - _width / 2f, 0f, _width, _height);
        _bounds2 = new Rectangle(_width / 2f, 0f, _width, _height);
    }

    public void update(float delta, float speed){
        _bounds2.x -= delta * (speed * _speedScalar);
        _bounds1.x -= delta * (speed * _speedScalar);

        resetBounds();
    }

    public void resetBounds(){
        if (_bounds2.x + _width  < _width){
            _bounds1 = _bounds2;
            _bounds2 = new Rectangle(_bounds1.x + _width, 0f, _width, _height);
        }
    }

    public void draw(Batch batch){
        batch.draw(_texture, _bounds2.x, _bounds2.y, _width, _height);
        batch.draw(_texture, _bounds1.x, _bounds1.y, _width, _height);
    }
}
