package com.alexd.projectgame.utils;

import com.alexd.projectgame.graphics.SpriteAnimation;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Handles the assets of the game excluding sounds (handled by AudioManager).
 */
public class AssetsManager {

    private static final String ATLAS_PATH = "spritesheet.txt";
    private static final String UI_PATH ="uiatlas.txt";

    private static final String ENEMY_ANIMATION_NAME = "enemy";
    private static final String OBSTACLE_NAME = "obstacle";
    private static final String RUNNER_JUMP_NAME = "playerjump";
    private static final String PLATFORM_NAME = "platform";

    private static final String[] RUNNER_ANIMATION_NAMES = new String[]{ "player-swe", "player-rus" };
    private static final String[] GAME_BACKGROUND_NAMES = new String[]{ "bg1", "bg2", "bg3", "menubg"};

    private static HashMap<String, SpriteAnimation> _animationMap;
    private static HashMap<String, AtlasRegion> _atlasRegionMap;
    private static HashMap<String, Texture> _backgroundMap;
    private static BitmapFont _bigFont;
    private static BitmapFont _smallFont;
    private static TextureAtlas _atlas;
    private static Skin _skin;



    public static void initiate(){
        _atlas = new TextureAtlas(ATLAS_PATH);
        _animationMap = new HashMap<String, SpriteAnimation>();
        _atlasRegionMap = new HashMap<String, AtlasRegion>();
        _backgroundMap = new HashMap<String, Texture>();
        _bigFont = new BitmapFont(Gdx.files.internal("fonts/thefont.fnt"),
                Gdx.files.internal("fonts/thefont_0.png"), false);
        _smallFont = new BitmapFont(Gdx.files.internal("fonts/thefont.fnt"),
                Gdx.files.internal("fonts/thefont_0.png"), false);
        _skin = new Skin(new TextureAtlas(UI_PATH));
        loadAssets();
    }

    public static void loadAssets(){
        loadTexture(PLATFORM_NAME);
        loadAnimation(0.2f, ENEMY_ANIMATION_NAME, 4);
        loadTexture(OBSTACLE_NAME);
        for (String runnerName : RUNNER_ANIMATION_NAMES){
            loadAnimation(0.2f, runnerName, 11);
            loadTexture(runnerName + "-jump");
        }
        for (String bgName : GAME_BACKGROUND_NAMES){
            loadBackground(bgName, bgName + ".png");
        }
        _smallFont.setScale(0.5f);
    }

    private static void loadBackground(String key, String fileHandle){
        _backgroundMap.put(key, new Texture(Gdx.files.internal(fileHandle)));
    }

    private static void loadAnimation(float animationStep, String key, int numberOfFrames){
        Array<TextureRegion> frames = new Array<TextureRegion>(numberOfFrames);
        for (int i = 0; i < numberOfFrames; i++){
            frames.add(_atlas.findRegion(key + "-" + i));
        }
        _animationMap.put(key, new SpriteAnimation(animationStep, frames, Animation.PlayMode.LOOP));
    }

    private static void loadTexture(String key){
        _atlasRegionMap.put(key, _atlas.findRegion(key));
    }

    public static void dispose() {
        _atlas.dispose();
        _bigFont.dispose();
        _smallFont.dispose();
        _skin.dispose();
        disposeMap(_backgroundMap);
    }

    public static void disposeMap(HashMap map){
        Iterator iterator = map.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry mapValue = (Map.Entry)iterator.next();
            ((Disposable)mapValue.getValue()).dispose();
        }
        map.clear();
    }

    public static SpriteAnimation getAnimation(String key){
        return  _animationMap.get(key);
    }

    public static AtlasRegion getAtlasRegion(String key){
        return _atlasRegionMap.get(key);
    }

    public static Sprite getSprite(String key){
        return new Sprite(_atlasRegionMap.get(key));
    }

    public static BitmapFont getLargeFont(){
        return _bigFont;
    }

    public static BitmapFont getSmallFont(){
        return _smallFont;
    }

    public static Texture getBackground(String key){ return _backgroundMap.get(key); }

    public static Skin getSkin(){ return _skin; }

}
