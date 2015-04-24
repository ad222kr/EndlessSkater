package com.alexd.projectgame.gameobjects;

import com.alexd.projectgame.enums.GameObjectType;
import com.alexd.projectgame.helpers.Helpers;
import com.alexd.projectgame.helpers.PhysicsFactory;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Alex on 2015-04-21.
 */
public class Platform extends GameObject {

    public Platform(World world, float x, float y, float width, float height){
        super(world);
        initiate(x, y, width, height);
    }

    @Override
    public void initiate(float x, float y, float width, float height) {

        setupMembers(x, y, width, height);
        _gameObjectType = GameObjectType.GROUND;
        _body = PhysicsFactory.createPlatform(_world, this);
    }



}
