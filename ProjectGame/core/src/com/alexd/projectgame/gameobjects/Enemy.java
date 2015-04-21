package com.alexd.projectgame.gameobjects;

import com.alexd.projectgame.enums.EnemyType;
import com.alexd.projectgame.enums.GameObjectType;
import com.alexd.projectgame.helpers.Helpers;
import com.alexd.projectgame.helpers.PhysicsFactory;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by Alex on 2015-04-13.
 */
public class Enemy extends GameObject {

    /* CONSTANTS */
    public static final float X = 25f;
    public static final float Y = 4f;
    public static final float WIDTH = 1.5f;
    public static final float HEIGHT = 2f;
    public static final float DENSITY = 0.5f;

    /* Members */

    private EnemyType _enemyType;

    /* Get & set */

    public EnemyType getEnemyType(){
        return _enemyType;
    }



    public Enemy(World world){
        super(world);
        _enemyType = EnemyType.getRandomValue();
        _gameObjectType = GameObjectType.ENEMY;
        _body = PhysicsFactory.createEnemy(_world, this);
    }




}
