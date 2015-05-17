package com.alexd.projectgame.utils;

import com.alexd.projectgame.entities.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

/**
 * http://saltares.com/blog/games/fixing-your-timestep-in-libgdx-and-box2d/
 * TODO: Move all handling of entities here
 */
public class EntityManager {
    private Array<Entity>  _entities;


    public EntityManager(){
        _entities = new Array<Entity>();
    }


    public void addEntity(Entity entity){
        _entities.add(entity);
    }

    public void removeEntity(Entity entity){
        _entities.removeValue(entity, false);
    }

    public Array<Entity> getEntities(){
        return _entities;
    }


    public void updateEntities(){
        for (Entity entity : _entities){
            entity.update();
        }
    }

    public void saveCurrentPosition(){
        for (Entity entity : _entities){
            if (entity.getBody() != null){


                    entity.getPosition().x = entity.getBody().getPosition().x;
                    entity.getPosition().y = entity.getBody().getPosition().y;
                }
            }

    }

    public void interpolate(float alpha){
        for (Entity entity : _entities){

                entity.getPosition().x = entity.getBody().getPosition().x * alpha + entity.getPreviousPosition().x * (1.0f - alpha);
                entity.getPosition().y = entity.getBody().getPosition().y * alpha + entity.getPreviousPosition().y * (1.0f - alpha);


        }
    }
}
