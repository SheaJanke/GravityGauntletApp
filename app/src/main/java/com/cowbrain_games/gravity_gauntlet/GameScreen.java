package com.cowbrain_games.gravity_gauntlet;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

class GameScreen {
    int width = Resources.getSystem().getDisplayMetrics().widthPixels;
    int height = Resources.getSystem().getDisplayMetrics().heightPixels;
    Player player;
    Guns guns;
    Upgrades upgrades;
    Data data;
    LinkedList<Meteor> meteors = new LinkedList<>();
    ArrayList<Meteor> remove = new ArrayList<>();
    LinkedList<Bullets> bullets = new LinkedList<>();
    ArrayList<Bullets> addBullets = new ArrayList<>();
    ArrayList<Bullets> removeBullets = new ArrayList<>();
    int meteorLvl;
    private int lvlCounter = 5;
    private long lastMeteor = System.currentTimeMillis();
    private long bossTimer = System.currentTimeMillis();
    String goldEarned = "0";
    int shootX = 1825;

    GameScreen(Data data, Upgrades upgrades,Player player, Guns guns){
        this.upgrades = upgrades;
        this.data = data;
        this.player = player;
        this.guns = guns;
        reset();

    }

    void tick(GameView gameView, EndScreen endScreen){
        goldEarned = upgrades.addScores(goldEarned, upgrades.multiplyScore(upgrades.getScoreMultiplier(),upgrades.getLvlMultiplier(meteorLvl)*0.60));
        player.tick();
        guns.tick(addBullets);
        for(Bullets bullet:bullets){
            bullet.tick(meteors,upgrades,data,removeBullets,remove,gameView);
        }
        bullets.addAll(addBullets);
        for(Bullets bullet:removeBullets){
            bullets.remove(bullet);
        }
        addBullets.clear();
        removeBullets.clear();
        if(System.currentTimeMillis()-lastMeteor>3000 && System.currentTimeMillis()-bossTimer > 20000 && meteorLvl < 100){
            addMeteor();
            lastMeteor = System.currentTimeMillis();
            lvlCounter++;
        }
        if(lvlCounter >= 10){
            lvlCounter = 0;
            meteorLvl++;
            player.setWeight(player.getWeight()*1.1);
            if(meteorLvl%5 == 0){
                bossTimer = System.currentTimeMillis();
                addBoss();
                meteorLvl++;
                player.setWeight(player.getWeight()*1.1);
            }
        }
        if(meteorLvl >= 100 && meteors.size() == 0){
            gameView.setGameState(5);
            data.setGold(upgrades.simplifyScore(upgrades.addScores(data.getGold(),goldEarned)));
        }
        for(Meteor meteor:meteors){
            if(guns.getGunLvl()==5) {
                meteor.tick(player, meteors, this, bullets, Integer.parseInt(upgrades.getGunUnique(5)[Integer.parseInt(data.getGunLvls(5).substring(2,3))]));
            }else{
                meteor.tick(player,meteors,this,new LinkedList<Bullets>(), 0);
            }
        }
        for(Meteor rem : remove){
            meteors.remove(rem);
        }
        remove.clear();
        if(!upgrades.scoreLarger(player.getHealth(),"0")){
            endScreen.reset();
            gameView.setGameState(2);
            data.setGold(upgrades.simplifyScore(upgrades.addScores(data.getGold(),goldEarned)));
        }

    }

    void render(Canvas canvas, Bitmap coin, Bitmap shoot, Bitmap ammo, Bitmap grenade, Bitmap black_hole){
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(X(60));
        canvas.drawColor(Color.BLACK);
        for(Bullets bullet:bullets){
            bullet.render(canvas, grenade, black_hole);
        }
        player.render(canvas);
        guns.render(canvas);
        for(Meteor meteor:meteors){
            meteor.render(canvas,X(15));
        }

        //drawing health bar
        int healthX = 1500;
        paint.setColor(Color.RED);
        canvas.drawText(Integer.toString(meteors.size()),X(1000),Y(100),paint);
        canvas.drawText(Integer.toString(meteorLvl),X(1000),Y(150),paint);

        canvas.drawRect(X(healthX),Y(50),X(healthX+350),Y(150),paint);
        paint.setColor(Color.GREEN);
        canvas.drawRect(X(healthX), Y(50), X(healthX)+(float)upgrades.divideScores(player.getHealth(), player.getMaxHealth()) * X(350), Y(150),paint);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(X(5));
        canvas.drawRect(X(healthX),Y(50),X(healthX+350),Y(150),paint);
        paint.setTextSize(X(80));

        //drawing pause button
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(X(1875),Y(50),X(1975),Y(150),paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setARGB(255,212,175,55);
        paint.setStrokeWidth(X(10));
        canvas.drawRect(X(1875),Y(50),X(1975),Y(150),paint);
        paint.setStrokeWidth(X(20));
        canvas.drawLine(X(1910),Y(75),X(1910),Y(125),paint);
        canvas.drawLine(X(1940),Y(75),X(1940),Y(125),paint);


        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(upgrades.simplifyScore(getGoldEarned()),X(150),Y(100),paint);
        canvas.drawBitmap(Bitmap.createScaledBitmap(coin,(int)X(80),(int)X(80),true),X(160)+ getGoldEarned().length()*X(20),Y(35),paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setARGB(255,212,175,55);
        paint.setStrokeWidth(X(3));
        canvas.drawText(upgrades.simplifyScore(goldEarned),X(150),Y(100),paint);

        //shoot icon
        if(data.getShootPosition() == 0){
            shootX = 1825;
        }else{
            shootX = 175;
        }
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(X(10));
        paint.setColor(Color.RED);
        canvas.drawCircle(X(shootX),Y(825),(int)X(150),paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setARGB(255,212,175,55);
        canvas.drawCircle(X(shootX),Y(825),(int)X(150),paint);
        canvas.drawBitmap(Bitmap.createScaledBitmap(shoot,(int)X(250),(int)X(250),true),X(shootX-125),Y(825)-X(125),paint);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(X(80));
        canvas.drawText(guns.getAmmo(),X(shootX+25),Y(660),paint);
        canvas.drawBitmap(Bitmap.createScaledBitmap(ammo,(int)X(150),(int)X(100),true),X(shootX-95)-X(18)*guns.getAmmo().length(),Y(590),paint);

    }

    void touched(MotionEvent e, GameView gameView){
        for(int a = 0;a < e.getPointerCount(); a ++){
            if(Math.pow(e.getX(a)-X(shootX),2)+Math.pow(e.getY(a)-Y(825),2)<Math.pow((int)X(150),2)){
                guns.shoot(addBullets);
                continue;
            }else if(e.getX(a)>X(1875) && e.getX(a)<X(1975) && e.getY(a)>Y(50) && e.getY(a)<Y(150)){
                gameView.setGameState(-1);
            }

            player.touched(e,a);
        }
    }

    void addMeteor(){
        meteors.add(new Meteor((int)(Math.pow(Math.random(),2)*X(80) + X(30)),upgrades,meteorLvl));
    }

    void addMeteor(Meteor meteor){
        meteors.add(meteor);
    }

    private void addBoss(){
        meteors.add(new Boss((int)X(150),upgrades,meteorLvl));
    }

    void removeMeteor(Meteor meteor){
        remove.add(meteor);
    }

    void removeBullet(Bullets bullet){
        removeBullets.add(bullet);
    }

    float X(float X){
        return X * width/2000f;
    }

    float Y(float Y){
        return Y* height/1000f;
    }

    void reset(){
        player.reset();
        guns.gameReset();
        meteors.clear();
        bullets.clear();
        meteorLvl = data.getStartLvl();
        lvlCounter = 5;
        goldEarned = "0";
        for(int a = 0; a < 5; a ++){
           addMeteor();
        }
        player.setX(X(1000));
        player.setY(Y(500));
    }

    String getGoldEarned() {
        return upgrades.simplifyScore(goldEarned);
    }
}
