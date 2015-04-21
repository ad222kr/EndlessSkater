package com.alexd.projectgame.gameobjects;

import com.alexd.projectgame.enums.GameObjectType;
import com.alexd.projectgame.helpers.Helpers;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by Alex on 2015-04-13.
 */
public class Enemy extends GameObject {

    /* CONSTANTS */
    public static final float X = 25f;
    public static final float Y = 3.75f;
    public static final float WIDTH = 1f;
    public static final float HEIGHT = 1.5f;
    public static final float DENSITY = 0.5f;



    public Enemy(World world){
        super(world);
        _body = createBody();
        _gameObjectType = GameObjectType.ENEMY;
    }

    protected Body createBody(){

        BodyDef bodyDef = getBodyDef(Enemy.X, Enemy.Y, BodyDef.BodyType.KinematicBody);
        PolygonShape shape = getBox(Enemy.WIDTH, Enemy.HEIGHT);
        Body body = _world.createBody(bodyDef);
        body.setLinearVelocity(new Vector2( -Helpers.getRandomNumber(4, 7), 0));

        // Creating fixture definition for the body. This fixture needs more
        // information than the other ones. It is a sensor, meaning that it's
        // "transparent" for the other game objects
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.isSensor = true;
        fixtureDef.shape = shape;
        fixtureDef.density = Enemy.DENSITY;
        body.createFixture(fixtureDef);
        body.resetMassData();
        body.setUserData(this);
        shape.dispose();
        return body;

    }


}
