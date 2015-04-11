package com.alexd.projectgame.actors;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Alex on 2015-04-11.
 */
public class GroundActor extends Actor {

    private Body body;

    public GroundActor(Body body){
        this.body = body;
    }
}
