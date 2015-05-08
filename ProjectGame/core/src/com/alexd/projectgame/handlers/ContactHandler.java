package com.alexd.projectgame.handlers;

import com.alexd.projectgame.TheGame;
import com.alexd.projectgame.entities.Entity;
import com.alexd.projectgame.enums.GameObjectType;
import com.alexd.projectgame.entities.Enemy;
import com.alexd.projectgame.entities.Runner;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Class handling contacts between physics-objects
 */
public class ContactHandler implements ContactListener {

    private Runner _runner;

    public ContactHandler(Runner runner){
        _runner = runner;
    }


    private boolean shouldTrigger(Fixture fixtureA, Fixture fixtureB, int bitOne, int bitTwo){
        int firstBit = fixtureA.getFilterData().categoryBits;
        int secondBit = fixtureB.getFilterData().categoryBits;

        return (firstBit == bitOne && secondBit == bitTwo) || (firstBit == bitTwo && secondBit == bitOne);
    }

    private boolean isRunnerAboveEnemy(Fixture fixtureA, Fixture fixtureB) {
        Enemy enemy = fixtureA.getBody().getUserData() instanceof Enemy ?
                (Enemy) fixtureA.getBody().getUserData() :
                (Enemy) fixtureB.getBody().getUserData();
        float runnerPos = _runner.getBody().getPosition().y - _runner.getHeight() / 2;
        float enemyPos = enemy.getBody().getPosition().y + enemy.getHeight() / 2;

        if (runnerPos > enemyPos){
            enemy.setFlaggedForDeath(true);
            return true;
        }
        return false;
    }
    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (shouldTrigger(fixtureA, fixtureB, TheGame.RUNNER_BIT, TheGame.PLATFORM_BIT)){
            _runner.landed();
        }

        if (shouldTrigger(fixtureA, fixtureB, TheGame.RUNNER_BIT, TheGame.ENEMY_BIT)){
            _runner.removeHealth();
        }

        if (shouldTrigger(fixtureA, fixtureB, TheGame.RUNNER_SENSOR_BIT, TheGame.ENEMY_SENSOR_BIT) &&
            _runner.isFalling() && isRunnerAboveEnemy(fixtureA, fixtureB)){
            _runner.bumpOffEnemy();
        }
    }

    @Override
    public void endContact(Contact contact) {
        Entity objA = (Entity) contact.getFixtureA().getBody().getUserData();
        Entity objB = (Entity) contact.getFixtureB().getBody().getUserData();

        if (checkTypes(objA, objB, GameObjectType.RUNNER, GameObjectType.GROUND)){
            _runner.setIsOnGround(false); // no idea why i do this

        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        Entity objA = (Entity) contact.getFixtureA().getBody().getUserData();
        Entity objB = (Entity) contact.getFixtureB().getBody().getUserData();

        if ((checkTypes(objA, objB, GameObjectType.ENEMY, GameObjectType.RUNNER))){
            contact.setEnabled(false);
        }

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public boolean checkTypes(Entity a, Entity b, GameObjectType typeA, GameObjectType typeB){
        return ((a.isExpectedType(typeA) && b.isExpectedType(typeB)) ||
                (b.isExpectedType(typeA) && a.isExpectedType(typeB)));
    }

}
