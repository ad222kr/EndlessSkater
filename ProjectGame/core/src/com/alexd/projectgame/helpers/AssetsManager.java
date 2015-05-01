package com.alexd.projectgame.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.graphics.g2d.Animation;

import java.util.HashMap;
import java.util.SplittableRandom;

/**
 * Created by Alex on 2015-05-01.
 */
public class AssetsManager {

    private static final String ATLAS_PATH = "spritesheet.txt";
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
        _animationMap.put(RUNNER_ANIMATION_NAME, new Animation(1/15f, _atlas.findRegions(RUNNER_ANIMATION_NAME)));

        // Enemy sprite
        _atlasRegionMap.put(ENEMY_NAME, _atlas.findRegion(ENEMY_NAME));

        // Obstacle
        _atlasRegionMap.put(OBSTACLE_NAME, _atlas.findRegion(OBSTACLE_NAME));

        // Hearts for health
        _atlasRegionMap.put(HEART_DEPLETED_NAME, _atlas.findRegion(HEART_DEPLETED_NAME));
        _atlasRegionMap.put(HEART_FILLED_NAME, _atlas.findRegion(HEART_FILLED_NAME));
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
