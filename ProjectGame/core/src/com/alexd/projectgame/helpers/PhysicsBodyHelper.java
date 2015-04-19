package com.alexd.projectgame.helpers;

import com.alexd.projectgame.model.Enemy;
import com.alexd.projectgame.model.Ground;
import com.alexd.projectgame.model.Obstacle;
import com.alexd.projectgame.model.Runner;
import com.alexd.projectgame.userdata.EnemyData;
import com.alexd.projectgame.userdata.GroundData;
import com.alexd.projectgame.userdata.ObstacleData;
import com.alexd.projectgame.userdata.RunnerData;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import sun.security.util.PolicyUtil;

/**
 * Created by Alex on 2015-04-16.
 */
public class PhysicsBodyHelper {

    public static Body creteGround(World world){

        BodyDef bodyDef = getBodyDef(Ground.X, Ground.Y, BodyType.StaticBody);
        Body body = world.createBody(bodyDef);
        PolygonShape shape = getBox(Ground.WIDTH, Ground.HEIGHT);
        body.createFixture(shape, Ground.DENSITY);
        body.setUserData(new GroundData());
        shape.dispose();
        return body;

    }

    public static Body createEnemy(World world){

        BodyDef bodyDef = getBodyDef(Enemy.X, Enemy.Y, BodyType.KinematicBody);
        PolygonShape shape = getBox(Enemy.WIDTH, Enemy.HEIGHT);
        Body body = world.createBody(bodyDef);
        body.setLinearVelocity(Enemy.LINEAR_VELOCITY);

        // Creating fixture definition for the body. This fixture needs more
        // information than the other ones. It is a sensor, meaning that it's
        // "transparent" for the other game objects
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.isSensor = true;
        fixtureDef.shape = shape;
        fixtureDef.density = Enemy.DENSITY;
        body.createFixture(fixtureDef);
        body.resetMassData();
        body.setUserData(new EnemyData());
        shape.dispose();
        return body;

    }

    public static Body createObstacle(World world){
        BodyDef bodyDef = getBodyDef(Obstacle.X, Obstacle.Y, BodyType.KinematicBody);
        PolygonShape shape = getBox(Obstacle.WIDTH, Obstacle.HEIGHT);
        Body body = world.createBody(bodyDef);
        body.setLinearVelocity(Obstacle.LINEAR_VELOCITY);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.isSensor = true;
        fixtureDef.shape = shape;
        fixtureDef.density = Obstacle.DENSITY;
        body.createFixture(fixtureDef);
        body.resetMassData();;
        body.setUserData(new ObstacleData());
        shape.dispose();
        return body;
    }

    public static Body createRunner(World world){

        BodyDef bodyDef = getBodyDef(Runner.X, Runner.Y, BodyType.DynamicBody);
        PolygonShape shape = getBox(Runner.WIDTH, Runner.HEIGHT);
        Body body = world.createBody(bodyDef);
        body.createFixture(shape, Runner.DENSITY);
        body.resetMassData();
        body.setUserData(new RunnerData());
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
