package com.alexd.projectgame.entities;

import com.alexd.projectgame.enums.EnemyType;
import com.alexd.projectgame.enums.GameObjectType;
import com.alexd.projectgame.utils.Constants;
import com.alexd.projectgame.utils.GameManager;
import com.alexd.projectgame.utils.PhysicsFactory;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by Alex on 2015-04-13.
 */
public class Enemy extends Entity {

    private EnemyType _enemyType;

    /* Get & set */
    public EnemyType getEnemyType(){
        return _enemyType;
    }



    public Enemy(World world, float x, float y, float width, float height){
        super(world, x, y, width, height);
        initiate();

    }


    @Override
    protected void initiate() {
        _gameObjectType = GameObjectType.ENEMY;
        _enemyType = EnemyType.getRandomValue();
        _body = PhysicsFactory.createEnemy(_world, this);

    }

    public void jump(){
        _body.applyLinearImpulse(Constants.ENEMY_JUMPING_IMPULSE, _body.getWorldCenter(), true);
    }
}
