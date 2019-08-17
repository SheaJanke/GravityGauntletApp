package com.cowbrain_games.gravity_gauntlet;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.LinkedList;

class GameScreen {
    private int width = Resources.getSystem().getDisplayMetrics().widthPixels+100;
    private int height = Resources.getSystem().getDisplayMetrics().heightPixels;
    private Player player;
    private Upgrades upgrades;
    private Data data;
    private LinkedList<Meteor> meteors = new LinkedList<>();
    private ArrayList<Meteor> remove = new ArrayList<>();
    private int meteorLvl;
    private int lvlCounter = 5;
    private long lastMeteor = System.currentTimeMillis();
    private String goldEarned = "0";

    GameScreen(Data data, Upgrades upgrades){
        player = new Player((int)X(100),upgrades,data);
        this.upgrades = upgrades;
        this.data = data;
        reset();

    }

    void tick(GameView gameView, EndScreen endScreen){
        goldEarned = upgrades.addScores(goldEarned, upgrades.multiplyScore(upgrades.getScoreMultiplier(),upgrades.getLvlMultiplier(meteorLvl)));
        player.tick();
        if(System.currentTimeMillis()-lastMeteor>3000){
            addMeteor();
            lastMeteor = System.currentTimeMillis();
            lvlCounter++;
        }
        if(lvlCounter >= 10){
            lvlCounter = 0;
            meteorLvl++;
            player.setWeight(player.getWeight()*1.2);
        }
        for(Meteor meteor:meteors){
            meteor.tick(player,meteors,this);
        }
        if(!upgrades.scoreLarger(player.getHealth(),"0")){
            endScreen.reset();
            gameView.setGameState(2);
            data.setGold(upgrades.simplifyScore(upgrades.addScores(data.getGold(),goldEarned)));
        }

    }

    void render(Canvas canvas, Bitmap coin){
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
        paint.setTextSize(X(80));
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(upgrades.simplifyScore(goldEarned),X(150),Y(100),paint);
        canvas.drawBitmap(Bitmap.createScaledBitmap(coin,(int)X(80),(int)X(80),true),X(160)+ getGoldEarned().length()*X(20),Y(35),paint);
        paint.setStyle(Paint.Style.FILL);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(X(3));
        canvas.drawText(upgrades.simplifyScore(goldEarned),X(150),Y(100),paint);

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
        player.reset();
        meteors.clear();
        meteorLvl = data.getStartLvl();
        lvlCounter = 5;
        goldEarned = "0";
        for(int a = 0; a < 5; a ++){
            meteors.add(new Meteor(30,upgrades,meteorLvl));
        }
        player.setX(X(1000));
        player.setY(Y(500));
    }

    public String getGoldEarned() {
        return upgrades.simplifyScore(goldEarned);
    }
}
