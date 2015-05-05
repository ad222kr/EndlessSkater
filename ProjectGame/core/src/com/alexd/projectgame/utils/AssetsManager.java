package com.alexd.projectgame.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Animation;

import java.util.HashMap;

/**
 * Created by Alex on 2015-05-01.
 */
public class AssetsManager {

    private static final String ATLAS_PATH = "testatlas.txt";
    private static final String RUNNER_ANIMATION_NAME = "runner";
    private static final String ENEMY_NAME = "enemy";
    private static final String OBSTACLE_NAME = "obstacle";
    private static final String HEART_FILLED_NAME = "heart-filled";
    private static final String HEART_DEPLETED_NAME = "heart-depleted";

    private static HashMap<String, Animation> _animationMap;
    private static HashMap<String, AtlasRegion> _atlasRegionMap;
    private static BitmapFont _font;
    private static TextureAtlas _atlas;


    public static void initiate(){
        _atlas = new TextureAtlas(ATLAS_PATH);
        _animationMap = new HashMap<String, Animation>();
        _atlasRegionMap = new HashMap<String, AtlasRegion>();
        _font = new BitmapFont(Gdx.files.internal("fonts/thefont.fnt"), Gdx.files.internal("fonts/thefont_0.png"), false);
        loadAssets();
    }

    public static void loadAssets(){
        // Runner animation

        loadAnimation(1/15f, RUNNER_ANIMATION_NAME);

        // Enemy sprite
        loadTexture(ENEMY_NAME);

        // Obstacle
        loadTexture(OBSTACLE_NAME);

        // Hearts for health
        loadTexture(HEART_DEPLETED_NAME);
        loadTexture(HEART_FILLED_NAME);

        // Buttons
        loadTexture("play-button");
        loadTexture("pause-button");


    }

    private static void loadAnimation(float animationStep, String key){
        _animationMap.put(key, new Animation(animationStep, _atlas.findRegions(key)));
    }

    private static void loadTexture(String key){
        _atlasRegionMap.put(key, _atlas.findRegion(key));
    }

    public static void dispose() {
        _atlas.dispose();
        _font.dispose();
    }

    public static TextureAtlas getTextureAtlas(){
        return _atlas;
    }

    public static Animation getAnimation(String key){
        return  _animationMap.get(key);
    }

    public static AtlasRegion getAtlasRegion(String key){
        return _atlasRegionMap.get(key);
    }

    public static BitmapFont getFont(){
        return _font;
    }




}
