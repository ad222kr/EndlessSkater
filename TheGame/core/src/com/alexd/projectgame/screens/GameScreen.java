package com.alexd.projectgame.screens;

import com.alexd.projectgame.TheGame;
import com.alexd.projectgame.gameobjects.Ground;
import com.alexd.projectgame.gameobjects.Runner;
import com.alexd.projectgame.helpers.ContactHandler;
import com.alexd.projectgame.helpers.InputHandler;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by Alex on 2015-04-07.
 */
public class GameScreen implements Screen {

    private final int VIEWPORT_WIDTH = TheGame.APP_WIDTH / 50;
    private final int VIEWPORT_HEIGHT = TheGame.APP_HEIGHT / 50;

    private final Vector2 WORLD_GRAVITY = new Vector2(0, -10);

    private final float TIME_STEP = 1 / 300f;
    private float accumulator = 0f;

    private Game game;
    private World world;
    private Ground ground;
    private Runner runner;
    private Body body;

    private OrthographicCamera camera;
    private Box2DDebugRenderer renderer;


    public GameScreen(Game game) {
        this.game = game;
        world = new World(WORLD_GRAVITY, true);
        ground = new Ground(world);
        runner = new Runner(world);


        renderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f);
        camera.update();

        Gdx.input.setInputProcessor(new InputHandler(runner));
        world.setContactListener(new ContactHandler(runner));
    }

    @Override
    public void show() {

    }

    public Body createKinematicTest() {
        // might use this for moving platforms
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(30, 4);
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1f, 0.25f);

        body.createFixture(shape, 0.5f);
        body.setLinearVelocity(-5.0f, 0.0f);
        shape.dispose();
        return body;
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        renderer.render(world, camera.combined);
        doStep(delta);


    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public void doStep(float delta) {
        float frameTime = Math.min(delta, 0.25f);
        accumulator += frameTime;
        while (accumulator >= TIME_STEP) {
            world.step(TIME_STEP, 6, 2);
            accumulator -= TIME_STEP;
        }

    }

}
