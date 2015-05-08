package com.alexd.projectgame.utils;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Alex on 2015-04-24.
 */
public class PhysicsConstants {

    /**
     * World
     */
    public static final Vector2 WORLD_GRAVITY = new Vector2(0, -10);

    /**
     * Runner constants
     */

    public static final float RUNNER_X = 4f;
    public static final float RUNNER_Y = 4f;
    public static final float RUNNER_WIDTH = 1.5f;
    public static final float RUNNER_HEIGHT = 2f;
    public static final float RUNNER_DENSITY = 0.5f;
    public static final Vector2 RUNNER_JUMPING_IMPULSE = new Vector2(0, 16f);
    public static final Vector2 RUNNER_BUMP_IMPULSE = new Vector2(0, 18f);



    /**
     * Enemy constants
     */
    public static final float ENEMY_X = 25f;
    public static final float ENEMY_WIDTH = 1.5f;
    public static final float ENEMY_HEIGHT = 2f;
    public static final float ENEMY_DENSITY = 0.5f;



    /**
     * Obstacle constants
     */

    public static final float OBSTACLE_WIDTH = 2.5f;
    public static final float OBSTACLE_HEIGHT = ENEMY_HEIGHT / 3;
    public static final float OBSTACLE_DENSITY = 0.5f;
    public static final Vector2 OBSTACLE_LINEAR_VELOCITY = new Vector2(-6f, 0);


    /**
     * Platform constants
     */

    public static final float PLATFORM_INIT_X = -3f;
    public static final float PLATFORM_INIT_Y = 0f;
    public static final float PLATFORM_WIDTH = 30f;
    public static final float PLATFORM_INIT_WIDTH = PLATFORM_WIDTH * 4;
    public static final float PLATFORM_HEIGHT = 6f;
    public static final float PLATFORM_DENSITY = 0f;
    public static final Vector2 PLATFORM_LINEAR_VELOCITY = new Vector2(-6f, 0);
}
