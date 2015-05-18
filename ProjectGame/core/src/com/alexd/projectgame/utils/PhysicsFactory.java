package com.alexd.projectgame.utils;

import com.alexd.projectgame.TheGame;
import com.alexd.projectgame.entities.*;
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
        bodyDef.fixedRotation = true;
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(enemy.getWidth() / 2, enemy.getHeight() / 2);
        Body body = world.createBody(bodyDef);
        FixtureDef fixtureDef = getFixtureDef(false, shape, Constants.ENEMY_DENSITY);
        fixtureDef.filter.categoryBits = TheGame.ENEMY_BIT; // I am
        fixtureDef.filter.maskBits = TheGame.PLATFORM_BIT | TheGame.RUNNER_BIT | TheGame.PLATFORM_SENSOR_BIT; // I collide with

        PolygonShape sensorShape = new PolygonShape();
        sensorShape.setAsBox(enemy.getWidth() / 2, 0.05f, new Vector2(0, enemy.getHeight() / 2), 0f);
        FixtureDef sensorDef = getFixtureDef(true, sensorShape, 0);
        sensorDef.filter.categoryBits = TheGame.ENEMY_SENSOR_BIT; // I am
        sensorDef.filter.maskBits = TheGame.RUNNER_BIT;

        body.createFixture(sensorDef);
        sensorShape.dispose();

        body.createFixture(fixtureDef);
        body.resetMassData();
        body.setUserData(enemy);
        shape.dispose();
        body.setGravityScale(1.5f);

        body.setLinearVelocity(new Vector2(GameManager.getInstance().getEnemySpeed(), 0));

        return body;
    }

    public static Body createLife(World world, Life life){
        // TODO: replace hardcvoded values woith constants
        BodyDef bodyDef = getBodyDef(life.getX(), life.getY(), BodyType.KinematicBody);
        PolygonShape shape = getBox(life.getWidth(), life.getHeight());
        Body body = world.createBody(bodyDef);
        FixtureDef fixtureDef = getFixtureDef(true, shape, 1f);
        fixtureDef.filter.categoryBits = TheGame.LIFE_BIT;
        fixtureDef.filter.maskBits = TheGame.RUNNER_BIT;

        body.createFixture(fixtureDef);
        shape.dispose();

        body.setUserData(life);

        body.setLinearVelocity(new Vector2(GameManager.getInstance().getStaticObjectSpeed(), 0));

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
        FixtureDef fixtureDef = getFixtureDef(true, shape, Constants.OBSTACLE_DENSITY);
        fixtureDef.filter.categoryBits = TheGame.ENEMY_BIT;
        fixtureDef.filter.maskBits = TheGame.PLATFORM_BIT | TheGame.RUNNER_BIT;
        body.setLinearVelocity(GameManager.getInstance().getStaticObjectSpeed(), 0);
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
        FixtureDef fixtureDef = getFixtureDef(false, shape, Constants.PLATFORM_DENSITY);
        fixtureDef.friction = 0f;
        fixtureDef.filter.categoryBits = TheGame.PLATFORM_BIT;
        fixtureDef.filter.maskBits = TheGame.RUNNER_BIT | TheGame.ENEMY_BIT;

        body.setLinearVelocity(new Vector2(GameManager.getInstance().getStaticObjectSpeed(), 0));
        body.createFixture(fixtureDef);
        body.resetMassData();
        body.setUserData(platform);
        shape.dispose();

        PolygonShape sensorShape = new PolygonShape();
        sensorShape.setAsBox(-1, 3);
        sensorShape.setAsBox( 0.1f, 0.1f, new Vector2(-14.5f ,3), 0f);

        FixtureDef sensorDef = getFixtureDef(true, sensorShape, 0);
        sensorDef.filter.categoryBits = TheGame.PLATFORM_SENSOR_BIT;
        sensorDef.filter.maskBits = TheGame.ENEMY_BIT;

        body.createFixture(sensorDef);
        sensorShape.dispose();

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

        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(runner.getWidth() / 2, runner.getHeight() / 2);
        FixtureDef fixtureDef = getFixtureDef(false, shape, Constants.RUNNER_DENSITY);
        fixtureDef.filter.categoryBits = TheGame.RUNNER_BIT;
        fixtureDef.filter.maskBits = TheGame.PLATFORM_BIT | TheGame.ENEMY_BIT | TheGame.ENEMY_SENSOR_BIT | TheGame.LIFE_BIT;
        body.createFixture(fixtureDef);

        body.setGravityScale(1.70f);
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
