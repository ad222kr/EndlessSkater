package com.alexd.projectgame.utils;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Alex on 2015-04-24.
 */
public final class Box2DConstants {

    private Box2DConstants(){

    }

    /**
     * Box2d maskbits
     */
     public static final int RUNNER_BIT = 0x0001;
     public static final int ENEMY_BIT = 0x0002;
     public static final int PLATFORM_BIT = 0x0004;
     public static final int ENEMY_SENSOR_BIT = 0x0008;
     public static final int PLATFORM_SENSOR_BIT = 0x0010;
     public static final int LIFE_BIT = 0x0020;

    /**
     * World
     */
    public static final Vector2 WORLD_GRAVITY = new Vector2(0, -9);

    /**
     * Runner constants
     */
    public static final float RUNNER_X = 4f;
    public static final float RUNNER_Y = 4f;
    public static final float RUNNER_WIDTH = 1f;
    public static final float RUNNER_HEIGHT = 1.5f;
    public static final float RUNNER_DENSITY = 0.5f;
    public static final Vector2 RUNNER_JUMPING_IMPULSE = new Vector2(0, 9f);
    public static final Vector2 RUNNER_BUMP_IMPULSE = new Vector2(0, 10f);
    public static final float RUNNER_GRAVITY_SCALE = 3f;

    /**
     * Enemy constants
     */
    public static final float ENEMY_X = 34f;
    public static final float ENEMY_WIDTH = 1f;
    public static final float ENEMY_HEIGHT = 1.3f;
    public static final float ENEMY_DENSITY = 0.5f;

    /**
     * Platform constants
     */
    public static final float PLATFORM_INIT_X = -3f;
    public static final float PLATFORM_INIT_Y = 0f;
    public static final float PLATFORM_WIDTH = 30f;
    public static final float PLATFORM_INIT_WIDTH = PLATFORM_WIDTH * 2;
    public static final float PLATFORM_HEIGHT = 5.75f;
    public static final float PLATFORM_DENSITY = 0f;
    public static final float PLATFORM_SPEED = -9f;

    /**
     * Obstacle constants
     */
    public static final float OBSTACLE_WIDTH = 2.5f;
    public static final float OBSTACLE_HEIGHT = ENEMY_HEIGHT / 3;
    public static final float OBSTACLE_DENSITY = 0.5f;
}
