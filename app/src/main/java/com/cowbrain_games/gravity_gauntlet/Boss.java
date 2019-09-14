package com.cowbrain_games.gravity_gauntlet;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import java.util.LinkedList;

public class Boss extends Meteor {
    private int spawnedMeteors = 0;
    private Upgrades upgrades;
    Boss(int size, Upgrades upgrades, int lvl) {
        super(size, upgrades, lvl);
        this.upgrades = upgrades;
    }

    @Override
    void tick(Player player, LinkedList<Meteor> others, GameScreen gameScreen, LinkedList<Bullets> black_holes, int pull) {
        super.tick(player, others, gameScreen, black_holes, pull);
        while((float)(5 - spawnedMeteors)/5 > upgrades.divideScores(getHealth(), upgrades.multiplyScore(upgrades.getMeteorHealth()[getLvl()],(double)getSize()/X(60)))){
            Meteor meteor = new Meteor((int)(X(30)),upgrades,getLvl());
            meteor.setX(getX());
            meteor.setY(getY());
            meteor.setRandomVel();
            gameScreen.addMeteor(meteor);
            spawnedMeteors++;
        }
    }

    @Override
    void render(Canvas canvas, float strokeWidth) {
        super.render(canvas, X(30));
    }

    @Override
    void hitPlayer(Player player, Upgrades upgrades, GameScreen gameScreen) {
        if(upgrades.scoreLarger(player.getHealth(), "0")){
            player.setHealth(upgrades.subtractScore(player.getHealth(), Double.toString(Math.pow(1.35,getLvl()))));
        }
    }

    @Override
    void hitMeteor(Meteor other) {

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
