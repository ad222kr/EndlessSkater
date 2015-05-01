package com.alexd.projectgame.helpers;

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

    private static final String ATLAS_PATH = "testatlas.txt";
    private static final String RUNNER_ANIMATION_NAME = "sprite";
    private static final String ENEMY_NAME = "enemy";
    private static final String OBSTACLE_NAME = "obstacle";

    private static HashMap<String, Animation> _animationMap;
    private static HashMap<String, AtlasRegion> _atlasRegionMap;
    private static TextureAtlas _atlas;


    public static void initiate(){
        _atlas = new TextureAtlas(ATLAS_PATH);
        _animationMap = new HashMap<String, Animation>();
        _atlasRegionMap = new HashMap<String, AtlasRegion>();
        loadAssets();
    }

    public static void loadAssets(){
        // Runner animation
        _animationMap.put("runner", new Animation(1/15f, _atlas.findRegions(RUNNER_ANIMATION_NAME)));

        // Enemy sprite
        _atlasRegionMap.put("enemy", _atlas.findRegion(ENEMY_NAME));

        // Obstacle
        _atlasRegionMap.put("obstacle", _atlas.findRegion(OBSTACLE_NAME));
    }

    public static void dispose() {
        _atlas.dispose();
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




}
