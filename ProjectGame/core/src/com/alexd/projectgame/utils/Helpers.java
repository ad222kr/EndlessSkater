package com.alexd.projectgame.utils;

import com.alexd.projectgame.TheGame;
import com.alexd.projectgame.entities.Entity;
import com.badlogic.gdx.physics.box2d.Body;

import java.util.Random;

/**
 * Created by Alex on 2015-04-20.
 */
public class Helpers {

    private static Random _random = new Random();

    public static float convertToMeters(float number){
        return number / TheGame.PIXELS_TO_METERS;
    }

    public static int convertToMeters(int number) {
        return number / TheGame.PIXELS_TO_METERS; }

    public static float convertToPixels(float number){
        return number * TheGame.PIXELS_TO_METERS;
    }

    public static int convertToPixels(int number){
        return number * TheGame.PIXELS_TO_METERS;
    }

    public static float getRandomFloat(float min, float max){
        float random = _random.nextFloat() * (max - min) + min;
        return random;
    }

    public static int getRandomInt(int min, int max) {
        return _random.nextInt((max - min) + 1) + min;
    }

    public static boolean isBodyOutOfBounds(Body body){
        Entity entity = (Entity) body.getUserData();
        return ((body.getPosition().x + entity.getWidth() / 2) < 0) ||
                ((body.getPosition().y + entity.getHeight() / 2) < 0);
    }

    public static boolean stringIsNotNullNotEmptyNotWhiteSpace(final String string){
        return string != null && !string.isEmpty() && !string.trim().isEmpty();
    }


}
