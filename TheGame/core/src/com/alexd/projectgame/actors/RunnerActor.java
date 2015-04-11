package com.alexd.projectgame.actors;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Actor class, holds reference to physics body and
 * will take care of rendering sprites later
 */
public class RunnerActor extends Actor{
    private Body body;

    public RunnerActor(Body body){
        this.body = body;
    }



}
