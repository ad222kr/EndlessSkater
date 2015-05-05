package com.alexd.projectgame.gameobjects;

import com.alexd.projectgame.enums.GameObjectType;
import com.alexd.projectgame.utils.PhysicsConstants;
import com.alexd.projectgame.utils.PhysicsFactory;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by Alex on 2015-04-18.
 */
public class Obstacle extends GameObject {


    public Obstacle(World world, float x, float y, float width, float height){
        super(world);
        initiate(x, y, width, height);


    }

    @Override
    protected void initiate(float x, float y, float width, float height) {
        float fixedY = y - PhysicsConstants.OBSTACLE_HEIGHT; // else obstacles floats
        setupMembers(x, fixedY, width, height);
        _gameObjectType = GameObjectType.OBSTACLE;
        _body = PhysicsFactory.createObstacle(_world, this);

    }


}
