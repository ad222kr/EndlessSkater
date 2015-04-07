package com.alexd.projectgame.stages;

import com.alexd.projectgame.TheGame;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by Alex on 2015-04-07.
 */
public class GameStage extends Stage {
    // Since Box2D is in meters, 100 pixels should be 1 meter in the game. Don't know if this is right.
    private static final int VIEWPORT_WIDTH = TheGame.APP_WIDTH / 100;
    private static final int VIEWPORT_HEIGHT = TheGame.APP_HEIGHT / 100;

    private World world;
    private Body ground;

    private final float TIME_STEP = 1 / 300f;
    private float accumulator = 0f;

    private OrthographicCamera camera;
    private Box2DDebugRenderer renderer;

    public GameStage(){
        world = new World(new Vector2(0, -10), true);
        ground = createGround();
        renderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f);
        camera.update();

    }

    @Override
    public void act(float delta){
        super.act(delta);

        accumulator += delta;

        while (accumulator >= delta){
            world.step(TIME_STEP, 6, 2);
            accumulator -= TIME_STEP;
        }

    }

    @Override
    public void draw(){
        super.draw();
        renderer.render(world, camera.combined);
    }

    public Body createGround(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(new Vector2(0, 0));
        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(14f, 1f);
        body.createFixture(shape, 0);
        shape.dispose();
        return body;

    }


}
