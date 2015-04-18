package com.alexd.projectgame.handlers;

import com.alexd.projectgame.helpers.GameObjectType;
import com.alexd.projectgame.model.Runner;
import com.alexd.projectgame.userdata.UserData;
import com.badlogic.gdx.Gdx;
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
        if (isRunnerGroundContact(a, b)){
            runner.landed();
        }

        // Checks contact between enemy and runner
        if (isRunnerEnemyContact(a, b)){
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


}
