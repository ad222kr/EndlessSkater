package com.alexd.projectgame.gameobjects;

import com.alexd.projectgame.enums.GameObjectType;
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

    public static final float INIT_X = 0f;
    public static final float INIT_Y = 0f;
    public static final float INIT_WIDTH= 110f;
    public static final float INIT_HEIGHT = 6f;
    public static final float DENSITY = 0f;
    public static final Vector2 LINEAR_VELOCITY = new Vector2(-4f, 0);

    private float _x;
    private float _y;
    private float _width;
    private float _height;

    public void setX(float value){
        _x = value;
    }
    public float getX(){
        return _x;
    }

    public void setY(float value){
        _y = value;
    }

    public float getY(){
        return _y;
    }

    public float getWidth(){
        return _width;
    }

    public float getHeight(){
        return _height;
    }




    public Platform(World world){
        super(world);
        _body = PhysicsFactory.createPlatform(_world, this);
        _gameObjectType = GameObjectType.GROUND;
    }
    @Override
    public void initiate() {
        _x = INIT_X;
        _y = INIT_Y;
        _width = INIT_WIDTH;
        _height = INIT_HEIGHT;
        _body = PhysicsFactory.createPlatform(_world, this);
    }

    public void initiate(float x, float y){
        _x = x;
        _y = y;
        _width = 25;
        _height = INIT_HEIGHT;
        _body = PhysicsFactory.createPlatform(_world, this);
    }


}
