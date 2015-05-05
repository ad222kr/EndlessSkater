package com.alexd.projectgame.handlers;

import com.alexd.projectgame.TheGame;
import com.alexd.projectgame.enums.GameObjectType;
import com.alexd.projectgame.gameobjects.GameObject;
import com.alexd.projectgame.gameobjects.Runner;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
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
        GameObject objA = (GameObject) contact.getFixtureA().getBody().getUserData();
        GameObject objB = (GameObject) contact.getFixtureB().getBody().getUserData();

        if (checkTypes(objA, objB, GameObjectType.RUNNER, GameObjectType.GROUND)){
            _runner.landed();
        }

        if ((contact.getFixtureA().getFilterData().categoryBits == TheGame.ENEMY_BIT &&
             contact.getFixtureB().getFilterData().categoryBits == TheGame.RUNNER_BIT) ||
             contact.getFixtureA().getFilterData().categoryBits == TheGame.RUNNER_BIT &&
             contact.getFixtureB().getFilterData().categoryBits == TheGame.ENEMY_BIT){
            Gdx.app.log("BodyA: ", "" + objA.toString());
            Gdx.app.log("BodyB: ", "" + objB.toString());

            _runner.removeHealth();


        }
        else if ((contact.getFixtureA().getFilterData().categoryBits == TheGame.RUNNER_BIT &&
                  contact.getFixtureB().getFilterData().categoryBits == TheGame.ENEMY_SENSOR_BIT)
               || contact.getFixtureB().getFilterData().categoryBits == TheGame.RUNNER_BIT &&
                  contact.getFixtureA().getFilterData().categoryBits == TheGame.ENEMY_SENSOR_BIT){
            if ( objA.isExpectedType(GameObjectType.RUNNER) && (objA.getBody().getLinearVelocity().y < 0) &&
                 objA.getBody().getPosition().y - objA.getHeight() / 2 > objB.getBody().getPosition().y + objB.getHeight() / 2||
                 objB.isExpectedType(GameObjectType.RUNNER) && (objA.getBody().getLinearVelocity().y < 0)  &&
                 objB.getBody().getPosition().y - objB.getHeight() / 2 > objA.getBody().getPosition().y + objA.getHeight() / 2)
            _runner.getBody().applyLinearImpulse(new Vector2(0, 20f), _runner.getBody().getWorldCenter(), true);
        }

    }

    @Override
    public void endContact(Contact contact) {
        GameObject objA = (GameObject) contact.getFixtureA().getBody().getUserData();
        GameObject objB = (GameObject) contact.getFixtureB().getBody().getUserData();

        if (checkTypes(objA, objB, GameObjectType.RUNNER, GameObjectType.GROUND)){
            _runner.setIsOnGround(false); // what?
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        GameObject objA = (GameObject) contact.getFixtureA().getBody().getUserData();
        GameObject objB = (GameObject) contact.getFixtureB().getBody().getUserData();

        if ((objA.isExpectedType(GameObjectType.ENEMY) && objB.isExpectedType(GameObjectType.RUNNER)) ||
            (objA.isExpectedType(GameObjectType.RUNNER) && objB.isExpectedType(GameObjectType.ENEMY))){
            contact.setEnabled(false);
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public boolean checkTypes(GameObject a, GameObject b, GameObjectType typeA, GameObjectType typeB){
        return ((a.isExpectedType(typeA) && b.isExpectedType(typeB)) ||
                (b.isExpectedType(typeA) && a.isExpectedType(typeB)));
    }

}
