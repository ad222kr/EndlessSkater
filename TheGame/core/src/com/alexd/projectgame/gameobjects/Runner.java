package com.alexd.projectgame.gameobjects;

import com.alexd.projectgame.helpers.GameObjectType;
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
    private final float X = 3f;
    private final float Y = 3f;
    private final float WIDTH = 1f;
    private final float HEIGHT = 2f;
    private final float DENSITY = 0.5f;
    private final Vector2 JUMPING_IMPULSE = new Vector2(0, 13f);

    /* Members */
    private boolean isJumping;

    /* Constructor */
    public Runner(World world){
        super(world);
        gameObjectType = GameObjectType.RUNNER;
        body = createPhysicsBody();
        isJumping = false;


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
        body.setUserData(this);
        shape.dispose();
        return body;
    }

    public void jump(){
        if (!isJumping){
            body.applyLinearImpulse(JUMPING_IMPULSE, body.getWorldCenter(), true);
            isJumping = true;
        }
    }

    public void landed(){
        isJumping = false;
    }



}
