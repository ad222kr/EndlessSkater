package com.alexd.projectgame.gameobjects;

import com.alexd.projectgame.enums.GameObjectType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Alex on 2015-04-11.
 */
public class Runner extends GameObject {
    /* CONSTANTS */
    private static final float X = 3f;
    private static final float Y = 3f;
    private static final float WIDTH = 1f;
    private static final float HEIGHT = 2f;
    private static final float DENSITY = 0.5f;

    /* Constructor */
    public Runner(World world){
        super(world);
        gameObjectType = GameObjectType.RUNNER;
        body = createPhysicsBody();

    }

    /* Methods */
    private Body createPhysicsBody(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(new Vector2(X, Y));
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(WIDTH / 2, HEIGHT / 2);
        Body body = world.createBody(bodyDef);
        body.createFixture(shape, DENSITY);
        body.resetMassData();
        shape.dispose();
        return body;
    }



}
