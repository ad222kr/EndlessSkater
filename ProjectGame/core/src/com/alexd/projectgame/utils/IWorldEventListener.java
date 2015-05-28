package com.alexd.projectgame.utils;

/**
 * Don't know if the name is really describing but this interface
 * basicly fires methods from the contacthandles and notifys the
 * Screen implementing it of the "events". Used for such things
 * as adding points to the score if the players kills an enemy
 * or picks up a health on full HP
 */
public interface IWorldEventListener {

    void onEnemyContact();
    void onEnemyKill();
    void onPickupHealth();
    void onPlayerJump();
}
