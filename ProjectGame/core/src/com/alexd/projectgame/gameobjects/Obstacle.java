package com.alexd.projectgame.gameobjects;

import com.alexd.projectgame.enums.GameObjectType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by Alex on 2015-04-18.
 */
public class Obstacle extends GameObject {
    public static final float X = 25f;
    public static final float Y = 3.25f;
    public static final float WIDTH = 2f;
    public static final float HEIGHT = 0.5f;
    public static final float DENSITY = 0.5f;
    public static final Vector2 LINEAR_VELOCITY = new Vector2(-3f, 0);




    public Obstacle(World world){
        super(world);
        _body = createBody();
        _gameObjectType = GameObjectType.OBSTACLE;
    }

    public Body createBody(){
        BodyDef bodyDef = getBodyDef(Obstacle.X, Obstacle.Y, BodyDef.BodyType.KinematicBody);
        PolygonShape shape = getBox(Obstacle.WIDTH, Obstacle.HEIGHT);
        Body body = _world.createBody(bodyDef);
        body.setLinearVelocity(Obstacle.LINEAR_VELOCITY);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.isSensor = true;
        fixtureDef.shape = shape;
        fixtureDef.density = Obstacle.DENSITY;
        body.createFixture(fixtureDef);
        body.resetMassData();
        body.setUserData(this);
        shape.dispose();
        return body;
    }


}
