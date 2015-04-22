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
        PolygonShape shape = getBox(Ground.WIDTH, Ground.HEIGHT);
        FixtureDef fixtureDef = getFixtureDef(false, shape, Ground.DENSITY);
        Body body = world.createBody(bodyDef);

        body.createFixture(fixtureDef);
        body.setUserData(ground);
        shape.dispose();

        return body;

    }

    public static Body createEnemy(World world, Enemy enemy){

        BodyDef bodyDef = getBodyDef(Enemy.X, Enemy.Y, BodyType.KinematicBody);
        PolygonShape shape = getBox(Enemy.WIDTH, Enemy.HEIGHT);
        Body body = world.createBody(bodyDef);
        FixtureDef fixtureDef = getFixtureDef(true, shape, Enemy.DENSITY);

        body.setLinearVelocity(enemy.getEnemyType().getSpeed());
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
        FixtureDef fixtureDef = getFixtureDef(true, shape, Obstacle.DENSITY);

        body.setLinearVelocity(Obstacle.LINEAR_VELOCITY);
        body.createFixture(fixtureDef);
        body.resetMassData();
        body.setUserData(obstacle);
        shape.dispose();

        return body;
    }

    public static Body createPlatform(World world, Platform platform ){
        BodyDef bodyDef = getBodyDef(platform.getX(), platform.getY(), BodyType.KinematicBody);
        PolygonShape shape = getBox(platform.getWidth(), platform.getHeight());
        Body body = world.createBody(bodyDef);
        FixtureDef fixtureDef = getFixtureDef(false, shape, Platform.DENSITY);
        fixtureDef.friction = 0f;

        body.setLinearVelocity(Platform.LINEAR_VELOCITY);
        body.createFixture(fixtureDef);
        body.resetMassData();
        body.setUserData(platform);
        shape.dispose();

        return body;

    }

    public static Body createRunner(World world, Runner runner){
        BodyDef bodyDef = getBodyDef(Runner.X, Runner.Y, BodyType.DynamicBody);
        PolygonShape shape = getBox(Runner.WIDTH, Runner.HEIGHT);
        Body body = world.createBody(bodyDef);
        FixtureDef fixtureDef = getFixtureDef(false, shape, Runner.DENSITY);

        body.createFixture(fixtureDef);
        body.resetMassData();
        body.setUserData(runner);
        shape.dispose();

        return body;
    }


    private static FixtureDef getFixtureDef(boolean isSensor, Shape shape, float density ){
        FixtureDef fixtureDef = new FixtureDef();

        fixtureDef.isSensor = isSensor;
        fixtureDef.shape = shape;
        fixtureDef.density = density;


        return fixtureDef;
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
