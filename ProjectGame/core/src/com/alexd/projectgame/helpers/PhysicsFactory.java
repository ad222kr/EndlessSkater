package com.alexd.projectgame.helpers;

import com.alexd.projectgame.TheGame;
import com.alexd.projectgame.gameobjects.*;

import com.alexd.projectgame.screens.GameScreen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;


/**
 * Class for creating the bodies for box2d
 */
public class PhysicsFactory {

    /**
     * Creates a body for an enemy
     * @param world - the world that will hold the body
     * @param enemy - instance of Enemy class that's tied to the body
     * @return - a body
     */
    public static Body createEnemy(World world, Enemy enemy){
        BodyDef bodyDef = getBodyDef(enemy.getX(), enemy.getY(), BodyType.DynamicBody);
        PolygonShape shape = getBox(enemy.getWidth(), enemy.getHeight());
        Body body = world.createBody(bodyDef);
        FixtureDef fixtureDef = getFixtureDef(false, shape, PhysicsConstants.ENEMY_DENSITY);

        body.setLinearVelocity(enemy.getEnemyType().getSpeed());
        body.createFixture(fixtureDef);
        body.resetMassData();
        body.setUserData(enemy);
        shape.dispose();

        return body;

    }

    /**
     * Creates a body for an obstacle
     * @param world - the world the body will inhabit
     * @param obstacle - instance of Obstacle class that's tied to the body
     * @return
     */
    public static Body createObstacle(World world, Obstacle obstacle){
        BodyDef bodyDef = getBodyDef(obstacle.getX(), obstacle.getY(), BodyType.KinematicBody);
        PolygonShape shape = getBox(obstacle.getWidth(), obstacle.getHeight());
        Body body = world.createBody(bodyDef);
        FixtureDef fixtureDef = getFixtureDef(true, shape, PhysicsConstants.OBSTACLE_DENSITY);

        body.setLinearVelocity(PhysicsConstants.OBSTACLE_LINEAR_VELOCITY);
        body.createFixture(fixtureDef);
        body.resetMassData();
        body.setUserData(obstacle);
        shape.dispose();

        return body;
    }

    /**
     * Creates a body for a platform
     * @param world - the world the body will inhabit
     * @param platform - instance of Platform class that's tied to the body
     * @return
     */
    public static Body createPlatform(World world, Platform platform ){
        BodyDef bodyDef = getBodyDef(platform.getX(), platform.getY(), BodyType.KinematicBody);
        PolygonShape shape = getBox(platform.getWidth(), platform.getHeight());
        Body body = world.createBody(bodyDef);
        FixtureDef fixtureDef = getFixtureDef(false, shape, PhysicsConstants.PLATFORM_DENSITY);
        fixtureDef.friction = 0f;

        body.setLinearVelocity(PhysicsConstants.PLATFORM_LINEAR_VELOCITY);
        body.createFixture(fixtureDef);
        body.resetMassData();
        body.setUserData(platform);
        shape.dispose();

        return body;
    }

    /**
     * Creates a body for a runner
     * @param world - the world the body will inhabit
     * @param runner - instance of Runner class that's tied to the body
     * @return
     */
    public static Body createRunner(World world, Runner runner){
        BodyDef bodyDef = getBodyDef(runner.getX(), runner.getY(), BodyType.DynamicBody);
        bodyDef.fixedRotation = true;
        PolygonShape shape = getBox(runner.getWidth(), runner.getHeight());
        Body body = world.createBody(bodyDef);
        FixtureDef fixtureDef = getFixtureDef(false, shape, PhysicsConstants.RUNNER_DENSITY);
        body.createFixture(fixtureDef);
        body.setGravityScale(1.75f);
        body.resetMassData();
        body.setUserData(runner);
        shape.dispose();

        return body;
    }


    /**
     * Helper for creating a FixtureDef
     * @param isSensor - if the body will collide with other bodies
     * @param shape - Shape object of the body
     * @param density - Density of the body
     * @return
     */
    private static FixtureDef getFixtureDef(boolean isSensor, Shape shape, float density ){
        FixtureDef fixtureDef = new FixtureDef();

        fixtureDef.isSensor = isSensor;
        fixtureDef.shape = shape;
        fixtureDef.density = density;

        return fixtureDef;
    }

    /**
     * Creates a shape for the body
     * @param width - width of the shape
     * @param height - height of the shape
     * @return - an instance of a PolygonShape
     */
    private static PolygonShape getBox(float width, float height){
        PolygonShape shape = new PolygonShape();

        shape.setAsBox(width / 2, height / 2);

        return shape;
    }

    /**
     * Helper for creating a BodyDef
     * @param x - x-position for the body
     * @param y - y-position for the body
     * @param type - the type of the body
     * @return - an instance of a BodyDef
     */
    private static BodyDef getBodyDef(float x, float y, BodyType type){
        BodyDef bodyDef = new BodyDef();

        bodyDef.type = type;
        bodyDef.position.set(new Vector2(x, y));

        return bodyDef;
    }
}
