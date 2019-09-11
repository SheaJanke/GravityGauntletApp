package com.cowbrain_games.gravity_gauntlet;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import java.util.LinkedList;

public class Boss extends Meteor {
    private int spawnedMeteors;

    Boss(int size, Upgrades upgrades, int lvl) {
        super(size, upgrades, lvl);
    }

    @Override
    void tick(Player player, LinkedList<Meteor> others, GameScreen gameScreen, LinkedList<Bullets> black_holes, int pull) {
        super.tick(player, others, gameScreen, black_holes, pull);

    }

    @Override
    void hitPlayer(Player player, Upgrades upgrades, GameScreen gameScreen) {
        if(upgrades.scoreLarger(player.getHealth(), "0")){
            player.setHealth(upgrades.subtractScore(player.getHealth(), Double.toString(Math.pow(1.35,getLvl()))));
        }
    }

    @Override
    void setTextColor(Paint paint) {
        paint.setTypeface(Typeface.create("Arial",Typeface.BOLD));
        paint.setARGB(255,212,175,55);
    }

    @Override
    void setOutlineColor(Paint paint) {
        paint.setARGB(255,212,175,55);
    }
}
