package com.alexd.projectgame.entities;

import com.alexd.projectgame.enums.GameObjectType;
import com.alexd.projectgame.utils.PhysicsFactory;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by Alex on 2015-04-18.
 */
public class Obstacle extends Entity {


    public Obstacle(World world, float x, float y, float width, float height){
        super(world, x, y, width, height);
        initiate();


    }

    @Override
    protected void initiate() {
        _gameObjectType = GameObjectType.OBSTACLE;
        _body = PhysicsFactory.createObstacle(_world, this);

    }


}
