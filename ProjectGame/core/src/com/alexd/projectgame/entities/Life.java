package com.alexd.projectgame.entities;

import com.alexd.projectgame.enums.GameObjectType;
import com.alexd.projectgame.utils.PhysicsFactory;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Alex on 2015-05-13.
 */
public class Life extends Entity {


    public Life(World world, float x, float y, float width, float height){
        super(world, x, y, width, height);
        initiate();


    }
    @Override
    protected void initiate() {
        _gameObjectType = GameObjectType.LIFE;
        _body = PhysicsFactory.createLife(_world, this);
    }
}
