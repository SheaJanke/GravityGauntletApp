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
    private int width = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int height = Resources.getSystem().getDisplayMetrics().heightPixels;
    private Player player;
    private Guns guns;
    private Upgrades upgrades;
    private Data data;
    private LinkedList<Meteor> meteors = new LinkedList<>();
    private ArrayList<Meteor> remove = new ArrayList<>();
    private LinkedList<Bullets> bullets = new LinkedList<>();
    private ArrayList<Bullets> addBullets = new ArrayList<>();
    private ArrayList<Bullets> removeBullets = new ArrayList<>();
    private int meteorLvl;
    private int lvlCounter = 5;
    private long lastMeteor = System.currentTimeMillis();
    private String goldEarned = "0";

    GameScreen(Data data, Upgrades upgrades,Player player, Guns guns){
        this.upgrades = upgrades;
        this.data = data;
        this.player = player;
        this.guns = guns;
        reset();

    }

    void tick(GameView gameView, EndScreen endScreen){
        goldEarned = upgrades.addScores(goldEarned, upgrades.multiplyScore(upgrades.getScoreMultiplier(),upgrades.getLvlMultiplier(meteorLvl)/2));
        player.tick();
        guns.tick();
        for(Bullets bullet:bullets){
            bullet.tick(this,meteors,upgrades);
        }
        for(Bullets bullet:addBullets){
            bullets.add(bullet);
        }
        for(Bullets bullet:removeBullets){
            bullets.remove(bullet);
        }
        addBullets.clear();
        if(System.currentTimeMillis()-lastMeteor>3000){
            addMeteor();
            lastMeteor = System.currentTimeMillis();
            lvlCounter++;
        }
        if(lvlCounter >= 10){
            lvlCounter = 0;
            meteorLvl++;
            player.setWeight(player.getWeight()*1.1);
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

    void render(Canvas canvas, Bitmap coin, Bitmap shoot){
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(X(60));
        canvas.drawColor(Color.BLACK);
        player.render(canvas);
        for(Bullets bullet:bullets){
            bullet.render(canvas, player);
        }
        guns.render(canvas,1);
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
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(X(3));
        canvas.drawText(upgrades.simplifyScore(goldEarned),X(150),Y(100),paint);

        //shoot icon
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(X(10));
        paint.setColor(Color.RED);
        canvas.drawCircle(X(1825),Y(825),(int)X(150),paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setARGB(255,212,175,55);
        canvas.drawCircle(X(1825),Y(825),(int)X(150),paint);
        canvas.drawBitmap(Bitmap.createScaledBitmap(shoot,(int)X(250),(int)X(250),true),X(1700),Y(825)-X(125),paint);

    }

    void touched(MotionEvent e){
        if(Math.pow(e.getX()-X(1825),2)+Math.pow(e.getY()-Y(825),2)<Math.pow((int)X(150),2)){
            guns.shoot(addBullets,1);
        }
        player.touched(e);

    }

    private void addMeteor(){
        meteors.add(new Meteor(30,upgrades,meteorLvl));
    }

    void removeMeteor(Meteor meteor){
        remove.add(meteor);
    }

    void removeBullet(Bullets bullet){
        removeBullets.add(bullet);
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

    String getGoldEarned() {
        return upgrades.simplifyScore(goldEarned);
    }
}
