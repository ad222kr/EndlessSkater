package com.alexd.projectgame.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by Alex on 2015-05-18.
 */
public class GamePreferences {
    private static GamePreferences ourInstance = new GamePreferences();

    public static GamePreferences getInstance() {
        return ourInstance;
    }

    private GamePreferences() {
    }

    public static final String PREF_NAME = "TheGamePreferences";
    private static final String MUSIC = "music";
    private static final String SOUND = "sound";
    private static final String HIGH_SCORE = "high-score";



    public void saveHighScore(int score){
        Preferences pref = getPrefs();

        if (score > pref.getInteger(HIGH_SCORE)){
            pref.putInteger(HIGH_SCORE, score);
            pref.flush();
        }
    }

    public int getHighScore(){
        return getPrefs().getInteger(HIGH_SCORE);
    }

    public void enableSound(){
        Preferences pref = getPrefs();
        if (!pref.getBoolean(SOUND)){
            pref.putBoolean(SOUND, true);
            pref.flush();
        }

        // blabla toggle sound here later
    }

    public void disableSound(){
        Preferences pref = getPrefs();

        pref.putBoolean(SOUND, false);
        pref.flush();

    }

    public boolean isSoundEnabled(){
        Gdx.app.log("Sound is: ", ""+getPrefs().getBoolean(SOUND));
        return getPrefs().getBoolean(SOUND);
    }


    public void enableMusic(){
        Preferences pref = getPrefs();

        pref.putBoolean(MUSIC, true);
        pref.flush();


        // blabla toggle sound here later
    }

    public void disableMusic(){
        Preferences pref = getPrefs();

        pref.putBoolean(MUSIC, false);
        pref.flush();

    }

    public boolean isMusicEnabled(){
        Gdx.app.log("MUSIC is: ", ""+getPrefs().getBoolean(MUSIC));
        return getPrefs().getBoolean(MUSIC);
    }

    private Preferences getPrefs(){
        return Gdx.app.getPreferences(PREF_NAME);
    }

}
