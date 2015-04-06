package com.alexd.thegame.stages;

import com.alexd.thegame.screens.GameScreen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by Alex on 2015-04-06.
 */
public class GameStage extends Stage {

    private static final Vector2 GRAVITY = new Vector2(0, -10);

    private World world;

    private final float TIME_STEP = 1 / 300f;
    private float accumulator = 0f;

    private OrthographicCamera cam;
    private Box2DDebugRenderer renderer; // for debug-rendering

    public GameStage(){
        world = new World(GRAVITY, true);
        renderer = new Box2DDebugRenderer();
    }

    private void setupCamera(){
        cam = new OrthographicCamera(GameScreen.WIDTH, GameScreen.HEIGHT);
        cam.position.set(cam.viewportWidth / 2, cam.viewportHeight / 2, 0f);
    }

    @Override
    public void act(float delta){
        super.act(delta);

        // fixed time step to avoid "lag" http://gafferongames.com/game-physics/fix-your-timestep/
        float frameTime = Math.min(delta, 0.25f);
        accumulator += frameTime;
        while (accumulator >= TIME_STEP){
            world.step(TIME_STEP, 6, 2);
            accumulator -= TIME_STEP;
        }
    }

    @Override
    public void draw(){
        super.draw();
        renderer.render(world, cam.combined);
    }
}
