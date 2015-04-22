package com.alexd.projectgame.helpers;

import com.alexd.projectgame.TheGame;
import com.alexd.projectgame.gameobjects.GameObject;
import com.alexd.projectgame.screens.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;

import java.util.Random;

/**
 * Created by Alex on 2015-04-20.
 */
public class Helpers {

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

    public static float getRandomFloat(int min, int max){
        return new Random().nextFloat() * (max - min) + min;
    }

    public static int getRandomInt(int min, int max){
        return new Random().nextInt((max - min) + 1) + min;
    }

    public static boolean isBodyOutOfBounds(Body body){
        GameObject gameObject = (GameObject) body.getUserData();
        return ((body.getPosition().x + gameObject.getWidth() / 2) < 0) ||
                ((body.getPosition().y + gameObject.getHeight() / 2) < 0);
    }


}
