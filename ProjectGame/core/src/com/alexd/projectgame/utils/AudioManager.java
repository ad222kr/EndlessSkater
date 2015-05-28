package com.alexd.projectgame.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Alex on 2015-05-21.
 */
public class AudioManager implements Disposable {

    private Sound _jumpSound;
    private Sound _clickSound;
    private Music _music;
    private GamePreferences _preferences;


    public AudioManager(GamePreferences preferences){
        _preferences = preferences;
        _jumpSound = Gdx.audio.newSound(Gdx.files.internal("sound/jump.wav"));
        _music = Gdx.audio.newMusic(Gdx.files.internal("sound/music.wav"));
        _clickSound = Gdx.audio.newSound(Gdx.files.internal("sound/click.wav"));

    }

    public void playJumpSound(){
        if (_preferences.isSoundEnabled()){
            _jumpSound.play();
        }
    }

    public void playClickSound(){
        if(_preferences.isSoundEnabled()){
            _clickSound.play();
        }
    }

    public void toggleMusic() {
        if (_preferences.isMusicEnabled()){

            _music.play();
            _music.setLooping(true);
        }
        else {
            _music.stop();
        }
    }

    public void stopMusic(){
        _music.stop();
    }

    @Override
    public void dispose(){
        _music.dispose();
        _clickSound.dispose();
        _jumpSound.dispose();
    }
}
