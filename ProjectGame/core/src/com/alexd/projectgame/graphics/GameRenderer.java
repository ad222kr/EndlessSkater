package com.alexd.projectgame.graphics;

import com.alexd.projectgame.graphics.ScrollingBackground;
import com.alexd.projectgame.graphics.ScrollingBackgroundLayer;
import com.alexd.projectgame.graphics.SpriteAnimation;
import com.alexd.projectgame.utils.*;
import com.badlogic.gdx.graphics.g2d.*;

/**
 * Class that will render the game objects. The UI-rendering will be taken
 * care of by Scene2D
 */
public class GameRenderer {

    private ScrollingBackground _background;
    private SpriteAnimation _runnerAnimation;
    private SpriteAnimation _enemyAnimation;
    private Sprite _playerJumpSprite;
    private Sprite _obstacleSprite;
    private Sprite _platformSprite;
    private Sprite _heartSprite;
    private float _runnerAnimationElapsed;
    private float _enemyAnimationElapsed;
    private GamePreferences _preferences;

    public GameRenderer(GamePreferences preferences){

        _preferences = preferences;
        //_preferences.setChosenSkin("player-rus");
        // Animation with textureatlas test
        _runnerAnimation = AssetsManager.getAnimation(_preferences.getChosenSkin());
        _obstacleSprite = AssetsManager.getSprite("obstacle");
        _playerJumpSprite = new Sprite(AssetsManager.getAtlasRegion(_preferences.getChosenSkin() + "-jump"));
        _enemyAnimation = AssetsManager.getAnimation("enemy");
        _platformSprite = AssetsManager.getSprite("platform");
        _heartSprite = AssetsManager.getSkin().getSprite("heart-filled");
        _background = new ScrollingBackground(new ScrollingBackgroundLayer[]{
                new ScrollingBackgroundLayer(0.1f, AssetsManager.getBackground("bg1")),
                new ScrollingBackgroundLayer(0.25f, AssetsManager.getBackground("bg2")),
                new ScrollingBackgroundLayer(0.5f, AssetsManager.getBackground("bg3"))
        }, 2f, 25, 14); // fix this hardcode

    }

    public void updateAnimation(float delta){
        _runnerAnimationElapsed += delta * GameManager.getInstance().getMultiplyer();
        _enemyAnimationElapsed += delta * GameManager.getInstance().getMultiplyer();
    }

    public void drawRunner(Batch batch, float x, float y, boolean isJumping){
        if (!isJumping){
            _runnerAnimation.draw(_runnerAnimationElapsed, batch, x, y, false);
        }
        else {
            batch.draw(_playerJumpSprite, x, y, Helpers.convertToMeters(_playerJumpSprite.getWidth()),
                    Helpers.convertToMeters(_playerJumpSprite.getHeight()));
            _runnerAnimationElapsed = 0;
        }
    }

    public void drawEnemy(Batch batch, float x, float y, boolean isFlipped){
            _enemyAnimation.draw(_enemyAnimationElapsed, batch, x, y, isFlipped);
    }

    public void drawObstacle(Batch batch, float x, float y){
        batch.draw(_obstacleSprite, x, y, Box2DConstants.OBSTACLE_WIDTH, Helpers.convertToMeters(_obstacleSprite.getHeight()));
    }

    public void drawPlatform(Batch batch, float x, float y, float platformWidth){

        if (platformWidth == Box2DConstants.PLATFORM_WIDTH){
            batch.draw(_platformSprite, x, y, Helpers.convertToMeters(_platformSprite.getRegionWidth()),
                    Helpers.convertToMeters(_platformSprite.getRegionHeight()));

        }
        else {
            batch.draw(_platformSprite, x + Box2DConstants.PLATFORM_WIDTH , y,
                    Helpers.convertToMeters(_platformSprite.getRegionWidth()), Helpers.convertToMeters(_platformSprite.getRegionHeight()));

        }

    }

    public void drawHeart(Batch batch, float x, float y){
        batch.draw(_heartSprite, x, y, 1f, 1); // TODO: Fix hardcode


    }

    public void drawBackground(SpriteBatch batch, float delta) {
        _background.draw(batch, delta);

    }
}
