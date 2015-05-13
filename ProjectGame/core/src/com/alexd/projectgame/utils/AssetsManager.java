package com.alexd.projectgame.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;

import javax.xml.soap.Text;
import java.util.HashMap;

/**
 * Created by Alex on 2015-05-01.
 */
public class AssetsManager {

    private static final String ATLAS_PATH = "spritesheet.txt";
    private static final String UI_PATH ="uiatlas.txt";
    private static final String RUNNER_ANIMATION_NAME = "player";
    private static final String ENEMY_ANIMATION_NAME = "enemy";
    private static final String OBSTACLE_NAME = "obstacle";
    private static final String RUNNER_JUMP_NAME = "playerjump";
    private static final String BACKGROUND = "background.png";

    private static HashMap<String, SpriteAnimation> _animationMap;
    private static HashMap<String, AtlasRegion> _atlasRegionMap;
    private static BitmapFont _font;
    private static TextureAtlas _atlas;
    private static Skin _skin;
    private static Texture _background;


    public static void initiate(){
        _atlas = new TextureAtlas(ATLAS_PATH);
        _background = new Texture(BACKGROUND);
        _animationMap = new HashMap<String, SpriteAnimation>();
        _atlasRegionMap = new HashMap<String, AtlasRegion>();
        _font = new BitmapFont(Gdx.files.internal("fonts/thefont.fnt"), Gdx.files.internal("fonts/thefont_0.png"), false);
        _skin = new Skin(new TextureAtlas(UI_PATH));
        loadAssets();
    }

    public static void loadAssets(){
        // Runner animation

        loadAnimation(0.2f, RUNNER_ANIMATION_NAME, 3);
        loadTexture(RUNNER_JUMP_NAME);

        // Enemy sprite
        loadAnimation(0.2f, ENEMY_ANIMATION_NAME, 3);

        // Obstacle
        loadTexture(OBSTACLE_NAME);



    }

    private static void loadAnimation(float animationStep, String key, int numberOfFrames){


        Array<TextureRegion> frames = new Array<TextureRegion>(numberOfFrames);
        for (int i = 0; i < numberOfFrames; i++){
            frames.add(_atlas.findRegion(key + "" + i));
        }
        _animationMap.put(key, new SpriteAnimation(animationStep, frames, Animation.PlayMode.LOOP_PINGPONG));
    }

    private static void loadTexture(String key){
        _atlasRegionMap.put(key, _atlas.findRegion(key));
    }

    public static void dispose() {
        _atlas.dispose();
        _font.dispose();
        _skin.dispose();
    }

    public static TextureAtlas getTextureAtlas(){
        return _atlas;
    }

    public static SpriteAnimation getAnimation(String key){
        return  _animationMap.get(key);
    }

    public static AtlasRegion getAtlasRegion(String key){
        return _atlasRegionMap.get(key);
    }

    public static BitmapFont getFont(){
        return _font;
    }

    public static Texture getBackground(){ return _background; }

    public static Skin getSkin(){
        // TODO: Own atlas for ui later?
        return _skin;
    }




}
