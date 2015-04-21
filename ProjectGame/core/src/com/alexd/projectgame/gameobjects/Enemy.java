package com.alexd.projectgame.gameobjects;

import com.alexd.projectgame.enums.GameObjectType;
import com.alexd.projectgame.helpers.PhysicsBodyHelper;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by Alex on 2015-04-13.
 */
public class Enemy extends GameObject {

    /* CONSTANTS */
    public static final float X = 25f;
    public static final float Y = 3.5f;
    public static final float WIDTH = 1f;
    public static final float HEIGHT = 1f;
    public static final float DENSITY = 0.5f;



    public Enemy(World world){
        super(world);
        _body = PhysicsBodyHelper.createEnemy(_world, this);
        _gameObjectType = GameObjectType.ENEMY;
    }



}