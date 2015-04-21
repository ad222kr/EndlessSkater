package com.alexd.projectgame.helpers;

import com.alexd.projectgame.gameobjects.*;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

/**
 * Created by Alex on 2015-04-16.
 */
public class PhysicsFactory {

    public static Body creteGround(World world, Ground ground){

        BodyDef bodyDef = getBodyDef(Ground.X, Ground.Y, BodyType.StaticBody);
        Body body = world.createBody(bodyDef);
        PolygonShape shape = getBox(Ground.WIDTH, Ground.HEIGHT);
        body.createFixture(shape, Ground.DENSITY);
        body.setUserData(ground);
        shape.dispose();
        return body;

    }

    public static Body createEnemy(World world, Enemy enemy){

        BodyDef bodyDef = getBodyDef(Enemy.X, Enemy.Y, BodyType.KinematicBody);
        PolygonShape shape = getBox(Enemy.WIDTH, Enemy.HEIGHT);
        Body body = world.createBody(bodyDef);
        body.setLinearVelocity(enemy.getEnemyType().getSpeed());

        // Creating fixture definition for the body. This fixture needs more
        // information than the other ones. It is a sensor, meaning that it's
        // "transparent" for the other game objects
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.isSensor = true;
        fixtureDef.shape = shape;
        fixtureDef.density = Enemy.DENSITY;
        body.createFixture(fixtureDef);
        body.resetMassData();
        body.setUserData(enemy);
        shape.dispose();
        return body;

    }

    public static Body createObstacle(World world, Obstacle obstacle){
        BodyDef bodyDef = getBodyDef(Obstacle.X, Obstacle.Y, BodyType.KinematicBody);
        PolygonShape shape = getBox(Obstacle.WIDTH, Obstacle.HEIGHT);
        Body body = world.createBody(bodyDef);
        body.setLinearVelocity(Obstacle.LINEAR_VELOCITY);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.isSensor = true;
        fixtureDef.shape = shape;
        fixtureDef.density = Obstacle.DENSITY;
        body.createFixture(fixtureDef);
        body.resetMassData();
        body.setUserData(obstacle);
        shape.dispose();
        return body;
    }

    public static Body createRunner(World world, Runner runner){

        BodyDef bodyDef = getBodyDef(Runner.X, Runner.Y, BodyType.DynamicBody);
        PolygonShape shape = getBox(Runner.WIDTH, Runner.HEIGHT);
        Body body = world.createBody(bodyDef);
        body.createFixture(shape, Runner.DENSITY);
        body.resetMassData();
        body.setUserData(runner);
        shape.dispose();
        return body;
    }



    private static PolygonShape getBox(float width, float height){

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);
        return shape;
    }

    private static BodyDef getBodyDef(float x, float y, BodyType type){

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = type;
        bodyDef.position.set(new Vector2(x, y));
        return bodyDef;
    }
}
