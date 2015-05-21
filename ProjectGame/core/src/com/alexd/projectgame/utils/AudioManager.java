package com.alexd.projectgame.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by Alex on 2015-05-21.
 */
public class AudioManager {

    private Sound _jumpSound;
    private GamePreferences preferences;


    public AudioManager(GamePreferences preferences){
        _jumpSound = Gdx.audio.newSound(Gdx.files.internal("jumpsound.wav"));
    }







}
