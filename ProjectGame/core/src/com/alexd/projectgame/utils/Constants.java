package com.alexd.projectgame.utils;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Alex on 2015-04-24.
 */
public final class Constants {

    private Constants(){
        // preventing creating objects of this class
    }
    /**
     * World
     */
    public static final Vector2 WORLD_GRAVITY = new Vector2(0, -10);

    /**
     * Runner constants
     */

    public static final float RUNNER_X = 4f;
    public static final float RUNNER_Y = 4f;
    public static final float RUNNER_WIDTH = 1f;
    public static final float RUNNER_HEIGHT = 1.5f;
    public static final float RUNNER_DENSITY = 0.5f;
    public static final Vector2 RUNNER_JUMPING_IMPULSE = new Vector2(0, 8f);
    public static final Vector2 RUNNER_BUMP_IMPULSE = new Vector2(0, 8f);

    /**
     * Enemy constants
     */
    public static final float ENEMY_X = 34f;
    public static final float ENEMY_WIDTH = 1f;
    public static final float ENEMY_HEIGHT = 1.5f;
    public static final float ENEMY_DENSITY = 0.5f;
    public static final Vector2 ENEMY_JUMPING_IMPULSE = new Vector2(-1f, 18f);




    /**
     * Platform constants
     */
    public static final float PLATFORM_INIT_X = -3f;
    public static final float PLATFORM_INIT_Y = 0f;
    public static final float PLATFORM_WIDTH = 30f;
    public static final float PLATFORM_INIT_WIDTH = PLATFORM_WIDTH * 2;
    public static final float PLATFORM_HEIGHT = 5.75f;
    public static final float PLATFORM_DENSITY = 0f;
    public static final float PLATFORM_SPEED = -5f;

    /**
     * Obstacle constants
     */
    public static final float OBSTACLE_WIDTH = 2.5f;
    public static final float OBSTACLE_HEIGHT = ENEMY_HEIGHT / 3;
    public static final float OBSTACLE_DENSITY = 0.5f;
    public static final float OBSTACLE_SPEED = PLATFORM_SPEED;
}
