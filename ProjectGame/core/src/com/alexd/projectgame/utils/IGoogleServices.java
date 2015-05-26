package com.alexd.projectgame.utils;

/**
 * Created by Alex on 2015-05-26.
 */
public interface IGoogleServices {
    public void signIn();
    public void signOut();
    public void rateGame();
    public void submitScore(long score);
    public void showScores();
    public boolean isSignedIn();
}
