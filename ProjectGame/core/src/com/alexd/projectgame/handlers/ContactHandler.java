package com.alexd.projectgame.handlers;

import com.alexd.projectgame.enums.GameObjectType;
import com.alexd.projectgame.gameobjects.GameObject;
import com.alexd.projectgame.gameobjects.Runner;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Class handling contacts between physics-objects
 */
public class ContactHandler implements ContactListener {
    private Runner _runner;


    public ContactHandler(Runner runner){

        _runner = runner;

    }


    @Override
    public void beginContact(Contact contact) {

        GameObject a = (GameObject) contact.getFixtureA().getBody().getUserData();
        GameObject b = (GameObject) contact.getFixtureB().getBody().getUserData();


        if (isContact(a, b, GameObjectType.RUNNER, GameObjectType.GROUND)){
            _runner.landed();
        }

        if (isContact(a, b, GameObjectType.ENEMY, GameObjectType.RUNNER) ||
            isContact(a, b, GameObjectType.OBSTACLE, GameObjectType.RUNNER)){
            _runner.removeHealth();
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


    public boolean isContact(GameObject a, GameObject b, GameObjectType typeA, GameObjectType typeB){
        return ((a.isExpectedType(typeA) && b.isExpectedType(typeB)) ||
                (b.isExpectedType(typeA) && a.isExpectedType(typeB)));
    }


}
