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

    private EnemyType _enemyType;

    /* Get & set */
    public EnemyType getEnemyType(){
        return _enemyType;
    }

    public Enemy(World world, float x, float y, float width, float height){
        super(world);
        initiate(x, y, width, height);

    }


    @Override
    protected void initiate(float x, float y, float width, float height) {
        setupMembers(x, y, width, height);
        _gameObjectType = GameObjectType.ENEMY;
        _enemyType = EnemyType.getRandomValue();
        _body = PhysicsFactory.createEnemy(_world, this);

    }
}
