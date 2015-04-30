package com.alexd.projectgame.helpers;

import com.alexd.projectgame.TheGame;
import com.alexd.projectgame.gameobjects.Runner;
import com.alexd.projectgame.screens.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

/**
 * Class that will render the game objects. The UI-rendering will be taken
 * care of by Scene2D
 */
public class Renderer implements Disposable{

    private SpriteBatch _batch;
    private BitmapFont _font;
    private GameScreen _screen;
    private TextureAtlas _spriteSheet;

    private Animation _animation;
    private float _animationElapsed = 0f;


    public Renderer(GameScreen screen){
        _batch = new SpriteBatch();
        _font = new BitmapFont();
        _font.setColor(Color.RED);
        _screen = screen;

        // Animation with textureatlas test
        _spriteSheet = new TextureAtlas("testatlas.txt");
        _animation = new Animation(1/15f, _spriteSheet.getRegions());

    }


    public void render(Matrix4 projectionMatrix, float delta){
        _batch.setProjectionMatrix(projectionMatrix);

        _font.setScale(0.2f, 0.2f);

        _animationElapsed += delta;

        float x = (_screen.getRunner().getBody().getWorldCenter().x - _screen.getRunner().getWidth() / 2);
        float y = (_screen.getRunner().getBody().getWorldCenter().y - _screen.getRunner().getHeight() / 2);


        _batch.begin();
        _batch.draw(_animation.getKeyFrame(_animationElapsed, true), x, y, 1.5f, 2f);



        _font.draw(_batch, ""+(int)Math.floor(_screen._score), 1, 13);
        _font.draw(_batch, "" + _screen.getRunner().getHealth(), 20, 13);

        _batch.end();

    }

    public void renderRunner(){

    }


    public void dispose(){
        _batch.dispose();
    }



}
