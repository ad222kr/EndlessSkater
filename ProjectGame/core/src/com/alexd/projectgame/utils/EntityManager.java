package com.alexd.projectgame.utils;

import com.alexd.projectgame.entities.*;
import com.alexd.projectgame.handlers.ContactHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

/**
 * Handles updating the positions of entities, interpolating and physics
 */
public class EntityManager {

    private final float TIME_STEP = 1 / 300f;

    private World _world;
    private Runner _runner;
    private Platform _currentPlatform;
    private Array<Entity> _entities;
    private Array<Body> _bodies;
    private float accumulator = 0;
    private float _lastEnemySpawnTime;
    private float _timeBetweenEnemies;
    private Random _enemyRandom;
    private Random _obstacleRandom;
    private Random _lifeRandom;

    public EntityManager() {
        _entities = new Array<Entity>();
        initiate();
    }


    public void initiate() {
        _enemyRandom = new Random();
        _obstacleRandom = new Random();
        _lifeRandom = new Random();
        _world = new World(Box2DConstants.WORLD_GRAVITY, true);
        _bodies = new Array<Body>();
        _currentPlatform = new Platform(_world, Box2DConstants.PLATFORM_INIT_X, Box2DConstants.PLATFORM_INIT_Y,
                Box2DConstants.PLATFORM_INIT_WIDTH, Box2DConstants.PLATFORM_HEIGHT);

        _runner = new Runner(_world, Box2DConstants.RUNNER_X, Box2DConstants.RUNNER_Y, Box2DConstants.RUNNER_WIDTH,
                Box2DConstants.RUNNER_HEIGHT);

        _world.setContactListener(new ContactHandler(_runner));

        addEntity(_runner);
        addEntity(_currentPlatform);
        _timeBetweenEnemies = Helpers.getRandomFloat(GameManager.getInstance().getEnemyMinSeconds(),
                GameManager.getInstance().getEnemyMaxSeconds());
    }


    public void addEntity(Entity entity) {
        _entities.add(entity);
    }

    public void removeEntity(Entity entity) {
        _entities.removeValue(entity, false);
    }

    public void updateEntities() {
        for (Entity entity : _entities) {
            entity.update();
        }
    }

    public void saveCurrentPosition() {
        // For calculating where the sprite should be between steps
        for (Entity entity : _entities) {
            if (entity.getBody() != null) {
                entity.getPreviousPosition().x = entity.getBody().getPosition().x;
                entity.getPreviousPosition().y = entity.getBody().getPosition().y;
            }
        }
    }

    public void interpolate(float alpha) {
        // Calculates where the entities should be between the world
        // steps supposed to prevent the stuttering that can occur
        // with a fixed time step
        for (Entity entity : _entities) {
            entity.getPosition().x = entity.getBody().getPosition().x *
                    alpha + entity.getPreviousPosition().x * (1.0f - alpha);
            entity.getPosition().y = entity.getBody().getPosition().y *
                    alpha + entity.getPreviousPosition().y * (1.0f - alpha);
        }
    }

    public void spawnEnemy() {
        if (isTimeForEnemySpawn()){
            _timeBetweenEnemies = getRandom((int) GameManager.getInstance().getEnemyMinSeconds(),
                    (int) GameManager.getInstance().getEnemyMaxSeconds(), _enemyRandom);
            float y = getCorrectYPos(true);
            Enemy enemy = new Enemy(_world, Box2DConstants.ENEMY_X, y,
                    Box2DConstants.ENEMY_WIDTH, Box2DConstants.ENEMY_HEIGHT);
            _lastEnemySpawnTime = 0;

            addEntity(enemy);
        }
    }

    private void spawnHealth(){
        float y = (_currentPlatform.getPosition().y + _currentPlatform.getHeight() / 2) + Helpers.getRandomInt(2, 3);
        float x = getCorrectXPos();
        addEntity(new Life(_world, x, y, 1f, 1f));

    }

    private void spawnObstacle() {
        float x = getCorrectXPos();
        float y = getCorrectYPos(false);

        addEntity(new Obstacle(_world, x, y, Box2DConstants.OBSTACLE_WIDTH, Box2DConstants.OBSTACLE_HEIGHT));
    }

    public void spawnPlatform() {
        if (isTimeForPlatformSpawn()){
            _currentPlatform = new Platform(_world, 42, Helpers.getRandomFloat(0, 2), Box2DConstants.PLATFORM_WIDTH,
                    Box2DConstants.PLATFORM_HEIGHT);
            addEntity(_currentPlatform);


            // TODO: fix own random methods for obstacle and health so they don't share same random-instance
            if (getRandom(0, 5, _obstacleRandom) <= 1) {
                spawnObstacle();
            }
            if (getRandom(0, 100, _lifeRandom) >= 95){
                spawnHealth();
            }
        }
    }

    private float getCorrectXPos() {
        // Helper for calculating obstacles X-pos to keep it inside the bounds of the
        // platform it resides on, otherwise it could be floating in the air
        int min = (int)(_currentPlatform.getPosition().x - _currentPlatform.getWidth() / 2 + 5);
        int max = (int)(_currentPlatform.getPosition().x + _currentPlatform.getWidth() / 2 - 5);

        return Helpers.getRandomFloat(min, max);
    }

    private float getCorrectYPos(boolean isEnemy) {
        // Helper for calculating the right Y-position for the enemies/obstacles
        // otherwise they are floating (at least obstacles since they are kinematic)
        return _currentPlatform.getPosition().y + _currentPlatform.getHeight() / 2 +
                (isEnemy ? Box2DConstants.ENEMY_HEIGHT : Box2DConstants.OBSTACLE_HEIGHT) / 2;
    }


    public void updateTimers(float delta) {
        _lastEnemySpawnTime += delta;
    }

    public void updateMovingSpeed(){
        // On difficultychange, updatres speed of the platforms that are
        // already on screen
        for (Entity entity : _entities){
            switch (entity.getEntityType()){
                case OBSTACLE:
                case LIFE:
                case GROUND:
                    entity.getBody().setLinearVelocity(GameManager.getInstance().getStaticObjectSpeed(), 0);
                    break;
            }
        }
    }

    public void doStep(float delta) {
        // fixed time step
        // max frame time to avoid spiral of death (on slow devices)

        // ------- FIXED TIME STEP ---------------

        float framteTime = Math.min(delta, 0.25f);
        accumulator += framteTime;
        while (accumulator >= TIME_STEP){

            saveCurrentPosition();
            updateEntities();
            _world.step(TIME_STEP, 10, 8);

            accumulator -= TIME_STEP;
            interpolate(accumulator / TIME_STEP);
        }



        // ----------- VARIABLE TIME STEP ---------------
        // not good if slow device, fps drops etc...
        // _world.step(delta, 10, 8);

    }

    private boolean isTimeForPlatformSpawn() {
        return (_currentPlatform.getPosition().x + _currentPlatform.getWidth() / 2) < 22;
    }

    private boolean isTimeForEnemySpawn() {
        // Second condition checks so that the enemy has a platform to stand on
        return (_lastEnemySpawnTime > _timeBetweenEnemies) && (
                _currentPlatform.getPosition().x + _currentPlatform.getWidth() / 2) > Box2DConstants.ENEMY_X;
    }

    public void destroyBodies(){
        _world.getBodies(_bodies);

        for(Body body : _bodies){
            // First update the body, check if it needs removing
            if(Helpers.isBodyOutOfBounds(body)){
                ((Entity)body.getUserData()).setFlaggedForDeath(true);
            }
            // Then remove it from the world
            if(((Entity)body.getUserData()).getFlaggedForDeath())  {
                removeEntity((Entity)body.getUserData());
                _world.destroyBody(body);
            }
        }
    }

    private int getRandom(int min, int max, Random instance){
        return instance.nextInt((max - min) + 1) + min;
    }
    public Array<Entity> getEntities() {
        return _entities;
    }

    public Runner getRunner() {
        return _runner;
    }

    public World getWorld(){
        return _world;
    }

    public void dispose(){
        _world.dispose();
    }
}
