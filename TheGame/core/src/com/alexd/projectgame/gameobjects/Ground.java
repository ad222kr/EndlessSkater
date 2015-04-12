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
public class Ground extends GameObject {

     /* CONSTANTS */
    private static final float X = 0f;
    private static final float Y = 0f;
    private static final float WIDTH = 50f;
    private static final float HEIGHT = 4f;
    private static final float DENSITY = 0f;

    public Ground(World world){
        super(world);
        gameObjectType = GameObjectType.RUNNER;
        body = createPhysicsBody();
    }

    public Body createPhysicsBody(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(new Vector2(X, Y));
        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(WIDTH / 2, HEIGHT / 2);
        body.createFixture(shape, DENSITY);
        shape.dispose();
        return body;

    }
}
