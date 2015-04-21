package com.alexd.projectgame.gameobjects;

import com.alexd.projectgame.enums.GameObjectType;
import com.alexd.projectgame.helpers.PhysicsBodyHelper;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Alex on 2015-04-11.
 */
public class Ground extends GameObject {

    /* CONSTANTS */
    public static final float X = 0f;
    public static final float Y = 0f;
    public static final float WIDTH = 52f;
    public static final float HEIGHT = 6f;
    public static final float DENSITY = 0f;

    public Ground (){
        super();
    }
    public Ground(World world){
        super(world);
        _body = PhysicsBodyHelper.creteGround(_world, this);
        _gameObjectType = GameObjectType.GROUND;
    }


}
