package com.alexd.projectgame.handlers;

import com.alexd.projectgame.entities.Entity;
import com.alexd.projectgame.entities.Life;
import com.alexd.projectgame.enums.EntityType;
import com.alexd.projectgame.entities.Enemy;
import com.alexd.projectgame.entities.Runner;
import com.alexd.projectgame.utils.Box2DConstants;
import com.alexd.projectgame.utils.IWorldEventListener;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Class handling contacts between physics-objects
 */
public class ContactHandler implements ContactListener {

    private Runner _runner;
    private IWorldEventListener _worldEventListener;

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

        if (shouldTrigger(fixtureA, fixtureB, Box2DConstants.RUNNER_BIT, Box2DConstants.PLATFORM_BIT)){
            _runner.landed();
        }

        if (shouldTrigger(fixtureA, fixtureB, Box2DConstants.RUNNER_BIT, Box2DConstants.LIFE_BIT)){
            // If max hp, add 100 points to score
            if (_runner.hasMaxHealth()){
                _worldEventListener.onPickupHealth();
            }
            else {
                _runner.addHealth();
            }
            Life life = fixtureA.getBody().getUserData() instanceof Life ? (Life)fixtureA.getBody().getUserData() :
                    (Life)fixtureB.getBody().getUserData();
            life.setFlaggedForDeath(true);

        }
        if (shouldTrigger(fixtureA, fixtureB, Box2DConstants.PLATFORM_SENSOR_BIT, Box2DConstants.ENEMY_BIT)){
            Enemy enemy = fixtureA.getBody().getUserData() instanceof Enemy ? (Enemy)fixtureA.getBody().getUserData() :
                    (Enemy)fixtureB.getBody().getUserData();
            enemy.flip();
        }


        if (shouldTrigger(fixtureA, fixtureB, Box2DConstants.RUNNER_BIT, Box2DConstants.ENEMY_SENSOR_BIT) &&
                _runner.isFalling() && isRunnerAboveEnemy(fixtureA, fixtureB)){
            _runner.bumpOffEnemy();
            _worldEventListener.onEnemyKill();
        }

        else if (shouldTrigger(fixtureA, fixtureB, Box2DConstants.RUNNER_BIT, Box2DConstants.ENEMY_BIT)){
                _runner.removeHealth();
        }
    }

    @Override
    public void endContact(Contact contact) {
        Entity objA = (Entity) contact.getFixtureA().getBody().getUserData();
        Entity objB = (Entity) contact.getFixtureB().getBody().getUserData();

        if (checkTypes(objA, objB, EntityType.RUNNER, EntityType.GROUND)){
            _runner.setIsOnGround(false); // no idea why i do this

        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        Entity objA = (Entity) contact.getFixtureA().getBody().getUserData();
        Entity objB = (Entity) contact.getFixtureB().getBody().getUserData();

        if ((checkTypes(objA, objB, EntityType.ENEMY, EntityType.RUNNER))){
            contact.setEnabled(false);
        }

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public boolean checkTypes(Entity a, Entity b, EntityType typeA, EntityType typeB){
        return ((a.isExpectedType(typeA) && b.isExpectedType(typeB)) ||
                (b.isExpectedType(typeA) && a.isExpectedType(typeB)));
    }

    public void setWorldEventListener(IWorldEventListener worldEventListener) {
        _worldEventListener = worldEventListener;
    }
}