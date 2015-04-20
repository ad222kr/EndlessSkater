package com.alexd.projectgame.handlers;

import com.alexd.projectgame.enums.GameObjectType;
import com.alexd.projectgame.gameobjects.Runner;
import com.alexd.projectgame.userdata.UserData;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Class handling contacts between physics-objects
 */
public class ContactHandler implements ContactListener {
    private Runner runner;


    public ContactHandler(Runner runner){
        this.runner = runner;

    }


    @Override
    public void beginContact(Contact contact) {

        UserData a = (UserData) contact.getFixtureA().getBody().getUserData();
        UserData b = (UserData) contact.getFixtureB().getBody().getUserData();

        // Checks for contact between runner and ground to prevent double-jumping
        /*if (isRunnerGroundContact(a, b)){
            runner.landed();
        }

        // Checks contact between enemy and runner
        if (isRunnerEnemyContact(a, b) || isRunnerObstacleContact(a, b)){
            runner.removeHealth();
        } */

        if (isContact(a, b, GameObjectType.RUNNER, GameObjectType.GROUND)){
            runner.landed();
        }

        if (isContact(a, b, GameObjectType.ENEMY, GameObjectType.RUNNER) ||
            isContact(a, b, GameObjectType.OBSTACLE, GameObjectType.RUNNER)){
            runner.removeHealth();
        }


    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public boolean isRunnerGroundContact(UserData a, UserData b){
        return ((a.isExpectedType(GameObjectType.GROUND) && b.isExpectedType(GameObjectType.RUNNER))||
                 a.isExpectedType(GameObjectType.RUNNER) && b.isExpectedType(GameObjectType.GROUND));
    }

    public boolean isRunnerEnemyContact(UserData a, UserData b){
        return (a.isExpectedType(GameObjectType.RUNNER) && b.isExpectedType(GameObjectType.ENEMY)) ||
                b.isExpectedType(GameObjectType.RUNNER) && b.isExpectedType(GameObjectType.ENEMY);

    }

    public boolean isRunnerObstacleContact(UserData a, UserData b){
        return (a.isExpectedType(GameObjectType.RUNNER) && b.isExpectedType(GameObjectType.OBSTACLE)) ||
                b.isExpectedType(GameObjectType.RUNNER) && b.isExpectedType(GameObjectType.OBSTACLE);
    }

    public boolean isContact(UserData a, UserData b, GameObjectType typeA, GameObjectType typeB){
        return ((a.isExpectedType(typeA) && b.isExpectedType(typeB)) ||
                (b.isExpectedType(typeA) && a.isExpectedType(typeB)));
    }


}
