package com.alexd.projectgame.utils;

import com.alexd.projectgame.entities.*;
import com.alexd.projectgame.gameinterface.gamehud.actors.Health;
import com.alexd.projectgame.handlers.ContactHandler;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

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

    public EntityManager() {
        _entities = new Array<Entity>();
        initiate();
    }


    public void initiate() {
        _world = new World(Constants.WORLD_GRAVITY, true);
        _bodies = new Array<Body>();
        _currentPlatform = new Platform(_world, Constants.PLATFORM_INIT_X, Constants.PLATFORM_INIT_Y, Constants.PLATFORM_INIT_WIDTH,
                Constants.PLATFORM_HEIGHT);

        _runner = new Runner(_world, Constants.RUNNER_X, Constants.RUNNER_Y, Constants.RUNNER_WIDTH,
                Constants.RUNNER_HEIGHT);

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
        // Saves the current position of the entities
        // for interpolating between steps
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
            _timeBetweenEnemies = Helpers.getRandomFloat(GameManager.getInstance().getEnemyMinSeconds(),
                    GameManager.getInstance().getEnemyMaxSeconds());
            float y = getCorrectYPos(true);
            Enemy enemy = new Enemy(_world, Constants.ENEMY_X, y,
                    Constants.ENEMY_WIDTH, Constants.ENEMY_HEIGHT);
            _lastEnemySpawnTime = 0;

            addEntity(enemy);
        }
    }

    private void spawnHealth(){
        float y = (_currentPlatform.getPosition().y + _currentPlatform.getHeight() / 2) + Helpers.getRandomInt(1, 3);
        float x = getCorrectXPos();
        addEntity(new Life(_world, x, y, 1f, 1f));

    }

    private void spawnObstacle() {
        float x = getCorrectXPos();
        float y = getCorrectYPos(false);

        addEntity(new Obstacle(_world, x, y, Constants.OBSTACLE_WIDTH, Constants.OBSTACLE_HEIGHT));
    }

    public void spawnPlatform() {

        if (isTimeForPlatformSpawn()){
            _currentPlatform = new Platform(_world, 42, Helpers.getRandomFloat(0, 2), Constants.PLATFORM_WIDTH,
                    Constants.PLATFORM_HEIGHT);
            Gdx.app.log("Platform speed: ", "" + _currentPlatform.getBody().getLinearVelocity().x);
            addEntity(_currentPlatform);

            if (Helpers.getRandomInt(0, 5) <= 1) {
                spawnObstacle();
            }
            if (Helpers.getRandomInt(0, 100) >= 95){
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
                (isEnemy ? Constants.ENEMY_HEIGHT : Constants.OBSTACLE_HEIGHT) / 2;
    }


    public void updateTimers(float delta) {
        _lastEnemySpawnTime += delta;
    }

    public void updateMovingSpeed(){
        // On difficultychange, updatres speed of the platforms that are
        // already on screen
        for (Entity entity : _entities){
            switch (entity.getGameObjectType()){
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



        // VARIABLE TIME STEP

    }

    private boolean isTimeForPlatformSpawn() {
        return (_currentPlatform.getPosition().x + _currentPlatform.getWidth() / 2) < 22;
    }

    private boolean isTimeForEnemySpawn() {
        // Second condition checks so that the enemy has a platform to stand on
        return (_lastEnemySpawnTime > _timeBetweenEnemies) && (
                _currentPlatform.getPosition().x + _currentPlatform.getWidth() / 2) > Constants.ENEMY_X;
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
