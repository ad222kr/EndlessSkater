package com.alexd.projectgame.model;

import com.alexd.projectgame.userdata.EnemyData;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * Created by Alex on 2015-04-13.
 */
public class Enemy extends GameObject {

    /* CONSTANTS */
    private final float X = 25f;
    private final float Y = 3.5f;
    private final float WIDTH = 1f;
    private final float HEIGHT = 1f;
    private final float DENSITY = 0.5f;
    private final Vector2 VELOCITY = new Vector2(-8f, 0);

    /* Members */
    public boolean alive;

    public Enemy(World world){
        super(world);
        body = createPhysicsBody();
        alive = false;
    }

    private Body createPhysicsBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(new Vector2(X, Y));
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(WIDTH / 2, HEIGHT / 2);
        Body body = world.createBody(bodyDef);
        body.setLinearVelocity(VELOCITY);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.isSensor = true;
        fixtureDef.shape = shape;
        fixtureDef.density = DENSITY;
        body.createFixture(fixtureDef);
        body.resetMassData();
        body.setUserData(new EnemyData());

        shape.dispose();
        return body;


    }

}
