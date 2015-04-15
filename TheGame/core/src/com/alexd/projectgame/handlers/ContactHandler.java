package com.alexd.projectgame.handlers;

import com.alexd.projectgame.helpers.GameObjectType;
import com.alexd.projectgame.model.Runner;
import com.alexd.projectgame.userdata.UserData;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by Alex on 2015-04-12.
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
        if ((a.isExpectedType(GameObjectType.GROUND) && b.isExpectedType(GameObjectType.RUNNER))||
             a.isExpectedType(GameObjectType.RUNNER) && b.isExpectedType(GameObjectType.GROUND)){
            runner.landed();
        }

        // Checks contact between enemy and runner
        if ((a.isExpectedType(GameObjectType.RUNNER) && b.isExpectedType(GameObjectType.ENEMY)) ||
             b.isExpectedType(GameObjectType.RUNNER) && b.isExpectedType(GameObjectType.ENEMY)){
            Gdx.app.log("CONTACT", "" + a.isExpectedType(GameObjectType.RUNNER));
            Gdx.app.log("CONTACT", "" + b.isExpectedType(GameObjectType.ENEMY));
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
}
