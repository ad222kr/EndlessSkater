package com.alexd.projectgame.helpers;

import com.alexd.projectgame.gameobjects.Ground;
import com.alexd.projectgame.gameobjects.Runner;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by Alex on 2015-04-12.
 */
public class ContactHandler implements ContactListener {
    Runner runner;

    public ContactHandler(Runner runner){
        this.runner = runner;
    }

    @Override
    public void beginContact(Contact contact) {
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();

        if((a.getUserData() instanceof Runner && b.getUserData() instanceof Ground) ||
                a.getUserData() instanceof Ground && b.getUserData() instanceof Runner){
            runner.landed();
            Gdx.app.log("CONTACT", "bodies contact");
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
