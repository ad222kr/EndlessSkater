package com.alexd.projectgame.gameobjects;

import com.alexd.projectgame.enums.GameObjectType;
import com.alexd.projectgame.helpers.PhysicsFactory;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by Alex on 2015-04-18.
 */
public class Obstacle extends GameObject {


    public static final float WIDTH = 2f;
    public static final float HEIGHT = 0.5f;
    public static final float DENSITY = 0.5f;
    public static final Vector2 LINEAR_VELOCITY = new Vector2(-4f, 0);

    private float _x;
    private float _y;

    public Obstacle(World world, float y){
        super(world);
        setPos(y);
        initiate();

    }
    public float getX(){
        return _x;
    }

    public float getY(){
        return _y;
    }

    @Override
    public float getWidth() {
        return WIDTH;
    }

    @Override
    public float getHeight() {
        return HEIGHT;
    }

    @Override
    public void initiate() {
        _x = 25f;
        _gameObjectType = GameObjectType.OBSTACLE;
        _body = PhysicsFactory.createObstacle(_world, this);
    }

    public void setPos(float y){
        _y = y - getHeight();
    }


}
