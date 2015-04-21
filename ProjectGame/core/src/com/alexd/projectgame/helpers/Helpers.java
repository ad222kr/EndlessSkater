package com.alexd.projectgame.helpers;

import com.alexd.projectgame.TheGame;
import com.alexd.projectgame.screens.GameScreen;
import com.badlogic.gdx.Gdx;

import java.util.Random;

/**
 * Created by Alex on 2015-04-20.
 */
public class Helpers {

    public static float convertToMeters(float number){
        return number / TheGame.PIXELS_TO_METERS;
    }

    public static int convertToMeters(int number) { return number / TheGame.PIXELS_TO_METERS; }

    public static float getRandomNumber(int min, int max){
        float ret =  new Random().nextFloat() * (max - min) + min;
        Gdx.app.log("Random value:", ""+ ret);
        return ret;
    }



}
