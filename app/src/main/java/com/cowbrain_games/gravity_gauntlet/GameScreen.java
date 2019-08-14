package com.cowbrain_games.gravity_gauntlet;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.LinkedList;

class GameScreen {
    private int width = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int height = Resources.getSystem().getDisplayMetrics().heightPixels;
    private Player player;
    private Upgrades upgrades;
    private LinkedList<Meteor> meteors = new LinkedList<>();
    private ArrayList<Meteor> remove = new ArrayList<>();
    private int meteorLvl;
    private int lvlCounter = 0;
    private long lastMeteor = System.currentTimeMillis();

    GameScreen(){
        player = new Player(100,(int)X(100),"10");
        upgrades = new Upgrades();
        meteorLvl = 0;
        for(int a = 0; a < 5; a ++){
            meteors.add(new Meteor(30,upgrades,meteorLvl));
        }

    }

    void tick(GameView gameView){
        player.tick();
        if(System.currentTimeMillis()-lastMeteor>3000){
            addMeteor();
            lastMeteor = System.currentTimeMillis();
            lvlCounter++;
        }
        if(lvlCounter >= 10){
            lvlCounter = 0;
            meteorLvl++;
        }
        for(Meteor meteor:meteors){
            meteor.tick(player,meteors,this);
        }
        if(!upgrades.scoreLarger(player.getHealth(),"0")){
            gameView.setGameState(2);
        }

    }

    void render(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(X(60));
        canvas.drawColor(Color.BLACK);
        player.render(canvas);
        for(Meteor meteor:meteors){
            meteor.render(canvas);
        }
        for(Meteor rem : remove){
            meteors.remove(rem);
        }
        remove.clear();
        paint.setColor(Color.RED);
        canvas.drawRect(X(1600),Y(50),X(1950),Y(150),paint);
        paint.setColor(Color.GREEN);

        canvas.drawRect(X(1600), Y(50), X(1600)+(float)upgrades.divideScores(player.getHealth(), player.getMaxHealth()) * X(350), Y(150),paint);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(X(5));
        canvas.drawRect(X(1600),Y(50),X(1950),Y(150),paint);

    }

    void touched(MotionEvent e){
        player.touched(e);
    }

    void addMeteor(){
        meteors.add(new Meteor(30,upgrades,meteorLvl));
    }

    void removeMeteor(Meteor meteor){
        remove.add(meteor);
    }

    private float X(float X){
        return X * width/2000f;
    }

    private float Y(float Y){
        return Y* height/1000f;
    }

    void reset(){
        player.setHealth(player.getMaxHealth());
        meteors.clear();
        meteorLvl = 0;
        lvlCounter = 0;
        for(int a = 0; a < 30; a ++){
            meteors.add(new Meteor(30,upgrades,meteorLvl));
        }
        player.setX(X(1000));
        player.setY(Y(500));
    }

}
