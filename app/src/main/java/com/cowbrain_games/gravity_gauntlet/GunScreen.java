package com.cowbrain_games.gravity_gauntlet;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.LinkedList;

class GunScreen {
    Player player;
    Guns guns;
    Data data;
    Upgrades upgrades;
    private long buyTimer = System.currentTimeMillis();
    private String[] gun1Upgrades = {"Ammo", "Damage", "Burst"};
    private String[] gun1Values = new String[3];
    private int tickCounter = 0;
    private LinkedList<Bullets> bullets = new LinkedList<>();
    private ArrayList<Bullets> addBullets = new ArrayList<>();
    private ArrayList<Bullets> removeBullets = new ArrayList<>();
    private LinkedList<Meteor> meteors = new LinkedList<>();
    private ArrayList<Meteor> removeMeteors = new ArrayList<>();

    GunScreen(Player player, Guns guns, Data data, Upgrades upgrades){
        this.player = player;
        this.guns = guns;
        this.data = data;
        this.upgrades = upgrades;
        gun1Values[0] = upgrades.getGunAmmo(0)[Integer.parseInt(data.getGun1Lvls().substring(0,1))];
        gun1Values[1] = upgrades.getGunDamage(0)[Integer.parseInt(data.getGun1Lvls().substring(1,2))];
        gun1Values[2] = upgrades.getGunBurst(0)[Integer.parseInt(data.getGun1Lvls().substring(2,3))];

    }


    void tick(){
        if(tickCounter%20 == 0){
            guns.shoot(addBullets,1);
        }
        guns.tick();
        for(Bullets bullet:bullets){
            bullet.tick(meteors,upgrades,removeBullets,removeMeteors);
        }
        for(Bullets bullet:addBullets){
            bullets.add(bullet);
        }
        for(Bullets bullet:removeBullets){
            bullets.remove(bullet);
        }
        addBullets.clear();
        removeBullets.clear();
        tickCounter++;
    }
    void render(Canvas canvas, Bitmap coin){
        canvas.drawColor(Color.BLACK);
        Paint paint = new Paint();
        player.render(canvas);
        for(Bullets bullet:bullets){
            bullet.render(canvas, player);
        }
        guns.render(canvas,1);
        paint.setColor(Color.CYAN);
        paint.setTextSize(X(180));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(Typeface.create("Arial",Typeface.BOLD));
        canvas.drawText("GUNS", X(1000), Y(150),paint);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(X(5));
        canvas.drawText("GUNS", X(1000), Y(150),paint);
        paint.setTextSize(X(100));
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        canvas.drawText(data.getGold(),X(960),Y(260),paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setARGB(255,212,175,55);
        canvas.drawText(data.getGold(),X(960),Y(260),paint);
        canvas.drawBitmap(Bitmap.createScaledBitmap(coin,(int)X(100),(int)X(100),true),X(960)+ data.getGold().length()*X(28),Y(170),paint);
        paint.setTextSize(X(90));
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        canvas.drawRect(X(5),Y(5),X(300),Y(140),paint);
        paint.setARGB(255,212,175,55);
        canvas.drawText("BACK",X(150),Y(100),paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(X(10));
        canvas.drawRect(X(5),Y(5),X(300),Y(140),paint);
        for(int a = 0; a < 3; a++){
            paint.setStyle(Paint.Style.FILL);
            if(a%2 == 0) {
                paint.setColor(Color.CYAN);
            }else{
                paint.setColor(Color.BLUE);
            }
            canvas.drawRect(X(50) + X(650)*a, Y(700),X(650)+ X(650)*a,Y(950),paint);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(X(10));
            if(a%2 == 0) {
                paint.setColor(Color.BLUE);
            }else{
                paint.setColor(Color.CYAN);
            }
            canvas.drawRect(X(50) + X(650)*a, Y(700),X(650)+ X(650)*a,Y(950),paint);
            paint.setStyle(Paint.Style.FILL);
            paint.setARGB(255,212,175,55);
            paint.setTextSize(X(80));
            canvas.drawText(gun1Upgrades[a], X(350)+X(650)*a, Y(775),paint);
            canvas.drawText(gun1Values[a],X(300)+X(650)*a, Y(825),paint);
        }
    }

    void touched(MotionEvent e, GameView gameView, UpgradeScreen upgradeScreen){
        if(e.getX() > X(0) && e.getX()<X(300) && e.getY()>Y(0) && e.getY()<Y(140)){
            upgradeScreen.reset();
            gameView.setGameState(3);
        }
    }

    void reset(){
        buyTimer = System.currentTimeMillis();
        player.setX(X(1000));
        player.setY(Y(450));
    }

    private void drawUpgradeButton(Paint paint, Canvas canvas, float x, float y){
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(x -X(100),y-X(50),x+X(100),y+X(50),paint);
        paint.setARGB(255,212,175,55);
        canvas.drawText("BUY",x,y+Y(20),paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(X(10));
        canvas.drawRect(x -X(100),y-X(50),x+X(100),y+X(50),paint);
    }

    private float X(float X) {
        return X * Resources.getSystem().getDisplayMetrics().widthPixels/ 2000f;
    }

    private float Y(float Y) {
        return Y * Resources.getSystem().getDisplayMetrics().heightPixels/ 1000f;
    }
}
