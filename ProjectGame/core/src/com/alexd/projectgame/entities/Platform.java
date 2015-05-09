package com.alexd.projectgame.entities;

import com.alexd.projectgame.enums.GameObjectType;
import com.alexd.projectgame.utils.PhysicsFactory;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Alex on 2015-04-21.
 */
public class Platform extends Entity {

    public Platform(World world, float x, float y, float width, float height){
        super(world, x, y, width, height);
        initiate();
    }

    @Override
    protected void initiate() {

        _gameObjectType = GameObjectType.GROUND;
        _body = PhysicsFactory.createPlatform(_world, this);
    }



}
