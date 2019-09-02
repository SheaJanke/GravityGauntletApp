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
    private String[] gunCost = {"5.00M","10.0B","5.00t", "5.00q", "10.0Q"};
    private String[][] gunUpgrades = {{"Ammo", "Damage", "Burst"},{"Ammo", "Damage", "Burst"},{"Ammo", "Damage", "Burst"},{"Ammo", "Damage", "Exposion Radius"},{"Ammo", "Damage", "Duration"},{"Ammo", "Damage", "Pull"}};
    private String[][] gunValues = new String[6][3];
    private String[][] nextGunValues = new String[6][3];
    private String[][] nextGunCost = new String[6][3];
    private String maxLvls = "994";
    private int tickCounter = 0;
    private LinkedList<Bullets> bullets = new LinkedList<>();
    private ArrayList<Bullets> addBullets = new ArrayList<>();
    private ArrayList<Bullets> removeBullets = new ArrayList<>();
    private LinkedList<Meteor> meteors = new LinkedList<>();
    private ArrayList<Meteor> removeMeteors = new ArrayList<>();
    private int gunOnScreen;
    private int numberOfGuns = 6;

    GunScreen(Player player, Guns guns, Data data, Upgrades upgrades){
        this.player = player;
        this.guns = guns;
        this.data = data;
        this.upgrades = upgrades;
        gunOnScreen = data.getAllGunPurchases().indexOf("2");
        resetBuy();
    }


    void tick(){
        if(tickCounter%20 == 0){
            guns.shoot(addBullets);
        }
        guns.tick(addBullets);
        for(Bullets bullet:bullets){
            bullet.tick(meteors,upgrades,data,removeBullets,removeMeteors);
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
    void render(Canvas canvas, Bitmap coin, Bitmap next, Bitmap lock, Bitmap grenade){
        canvas.drawColor(Color.BLACK);
        Paint paint = new Paint();
        player.render(canvas);
        for(Bullets bullet:bullets){
            bullet.render(canvas, grenade);
        }
        guns.render(canvas);
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
            canvas.drawRect(X(50) + X(650)*a, Y(650),X(650)+ X(650)*a,Y(950),paint);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(X(10));
            if(a%2 == 0) {
                paint.setColor(Color.BLUE);
            }else{
                paint.setColor(Color.CYAN);
            }
            canvas.drawRect(X(50) + X(650)*a, Y(650),X(650)+ X(650)*a,Y(950),paint);
            paint.setStyle(Paint.Style.FILL);
            paint.setTextSize(X(60));
            if(!data.getGunLvls(gunOnScreen).substring(a, a+1).equals(maxLvls.substring(a,a+1))) {
                canvas.drawText(gunValues[gunOnScreen][a] + "   ->   " + nextGunValues[gunOnScreen][a], X(350) + X(650) * a, Y(790), paint);
            }else{
                canvas.drawText(gunValues[gunOnScreen][a], X(350) + X(650) * a, Y(790), paint);
            }
            paint.setARGB(255,212,175,55);
            paint.setTextSize(X(80));
            canvas.drawText(gunUpgrades[gunOnScreen][a], X(350)+X(650)*a, Y(725),paint);
            if(!data.getGunLvls(gunOnScreen).substring(a, a+1).equals(maxLvls.substring(a,a+1))) {
                drawUpgradeButton(paint, canvas, X(525) + X(650) * a, Y(875));
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(Color.WHITE);
                paint.setTextSize(X(70));
                canvas.drawText(nextGunCost[gunOnScreen][a], X(210) + X(650) * a, Y(900), paint);
                paint.setARGB(255, 212, 175, 55);
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(X(4));
                canvas.drawText(nextGunCost[gunOnScreen][a], X(210) + X(650) * a, Y(900), paint);
                canvas.drawBitmap(Bitmap.createScaledBitmap(coin, (int) X(70), (int) X(70), true), X(260) + X(650) * a + nextGunCost[gunOnScreen][a].length() * X(10), Y(845), paint);
            }else{
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(Color.WHITE);
                paint.setTextSize(X(70));
                canvas.drawText("MAXED", X(350) + X(650) * a, Y(900), paint);
                paint.setARGB(255, 212, 175, 55);
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(X(4));
                canvas.drawText("MAXED", X(350) + X(650) * a, Y(900), paint);
            }
        }
        if(gunOnScreen<numberOfGuns-1) {
            canvas.save();
            canvas.rotate(180, X(1825), Y(450));
            canvas.drawBitmap(Bitmap.createScaledBitmap(next, (int) X(250), (int) X(250), true), X(1700), Y(450) - X(125), paint);
            canvas.restore();
        }
        if(gunOnScreen>0) {
            canvas.drawBitmap(Bitmap.createScaledBitmap(next, (int) X(250), (int) X(250), true), X(50), Y(450) - X(125), paint);
        }
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(X(80));
        if (data.getGunPurchases(gunOnScreen).equals("0")){
            paint.setColor(Color.WHITE);
            canvas.drawRect(X(1605),Y(5),X(1995),Y(140),paint);
            canvas.drawText(gunCost[gunOnScreen-1],X(1750),Y(220),paint);
            paint.setARGB(255, 212, 175, 55);
            canvas.drawText("BUY GUN",X(1800),Y(100),paint);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(X(10));
            canvas.drawRect(X(1605),Y(5),X(1995),Y(140),paint);
            canvas.drawBitmap(Bitmap.createScaledBitmap(coin,(int)X(80),(int)X(80),true),X(1820)+gunCost[gunOnScreen-1].length()*X(10),Y(155),paint);
            for(int a = 0; a < 3; a++){
                canvas.drawBitmap(Bitmap.createScaledBitmap(lock,(int)X(100),(int)X(100),true),X(475) + X(650) * a,Y(825),paint);
            }

        }else if(data.getGunPurchases(gunOnScreen).equals("1")){
            paint.setColor(Color.RED);
            canvas.drawRect(X(1605),Y(5),X(1995),Y(140),paint);
            paint.setARGB(255, 212, 175, 55);
            canvas.drawText("Equip",X(1800),Y(100),paint);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(X(10));
            canvas.drawRect(X(1605),Y(5),X(1995),Y(140),paint);
        }else if(data.getGunPurchases(gunOnScreen).equals("2")){
            paint.setColor(Color.GREEN);
            canvas.drawRect(X(1605),Y(5),X(1995),Y(140),paint);
            paint.setARGB(255, 212, 175, 55);
            canvas.drawText("Equipped",X(1800),Y(100),paint);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(X(10));
            canvas.drawRect(X(1605),Y(5),X(1995),Y(140),paint);
        }
    }

    void touched(MotionEvent e, GameView gameView, UpgradeScreen upgradeScreen){
        if(e.getX() > X(0) && e.getX()<X(300) && e.getY()>Y(0) && e.getY()<Y(140)){
            upgradeScreen.reset();
            gameView.setGameState(3);
        }
        if(!data.getGunPurchases(gunOnScreen).equals("0")) {
            if (!data.getGunLvls(gunOnScreen).substring(0, 1).equals("9")) {
                if (e.getX() > X(425) && e.getX() < X(625) && e.getY() > Y(825) && e.getY() < Y(925) && System.currentTimeMillis() - buyTimer > 500 && upgrades.scoreLarger(data.getGold(), nextGunCost[gunOnScreen][0])) {
                    data.setGold(upgrades.subtractScore(data.getGold(), nextGunCost[gunOnScreen][0]));
                    data.setGunLvls(gunOnScreen, Integer.parseInt(data.getGunLvls(gunOnScreen).substring(0, 1)) + 1 + data.getGunLvls(gunOnScreen).substring(1, 3));
                    reset();
                    resetBuy();
                }
            }
            if (!data.getGunLvls(gunOnScreen).substring(1, 2).equals("9")) {
                if (e.getX() > X(1075) && e.getX() < X(1275) && e.getY() > Y(825) && e.getY() < Y(925) && System.currentTimeMillis() - buyTimer > 500 && upgrades.scoreLarger(data.getGold(), nextGunCost[gunOnScreen][1])) {
                    data.setGold(upgrades.subtractScore(data.getGold(), nextGunCost[gunOnScreen][1]));
                    data.setGunLvls(gunOnScreen, data.getGunLvls(gunOnScreen).substring(0, 1) + (Integer.parseInt(data.getGunLvls(gunOnScreen).substring(1, 2)) + 1) + data.getGunLvls(gunOnScreen).substring(2, 3));
                    reset();
                    resetBuy();
                }
            }
            if (!data.getGunLvls(gunOnScreen).substring(2, 3).equals("4")) {
                if (e.getX() > X(1725) && e.getX() < X(1925) && e.getY() > Y(825) && e.getY() < Y(925) && System.currentTimeMillis() - buyTimer > 500 && upgrades.scoreLarger(data.getGold(), nextGunCost[gunOnScreen][2])) {
                    data.setGold(upgrades.subtractScore(data.getGold(), nextGunCost[gunOnScreen][2]));
                    data.setGunLvls(gunOnScreen, data.getGunLvls(gunOnScreen).substring(0, 2) + (Integer.parseInt(data.getGunLvls(gunOnScreen).substring(2, 3)) + 1));
                    reset();
                    resetBuy();
                }
            }
            if(e.getX() > X(1600) && e.getX() < X(2000) && e.getY() > Y(0) && e.getY() < Y(145) && System.currentTimeMillis() - buyTimer > 300) {
                if (data.getGunPurchases(gunOnScreen).equals("1")){
                    data.setGunPurchases(data.getAllGunPurchases().indexOf("2"),"1");
                    data.setGunPurchases(gunOnScreen, "2");
                    reset();
                    resetBuy();
                }
            }
        }else if(e.getX() > X(1600) && e.getX() < X(2000) && e.getY() > Y(0) && e.getY() < Y(145) && System.currentTimeMillis() - buyTimer > 500 && upgrades.scoreLarger(data.getGold(), gunCost[gunOnScreen-1])) {
            data.setGold(upgrades.subtractScore(data.getGold(), gunCost[gunOnScreen-1]));
            data.setGunPurchases(gunOnScreen,"1");
            reset();
            resetBuy();
        }
        if(Math.pow(e.getX()-X(175),2)+Math.pow(e.getY()-Y(450),2)<Math.pow(X(125),2) && System.currentTimeMillis()-buyTimer>300 && gunOnScreen>0){
            gunOnScreen--;
            guns.setGunLvl(gunOnScreen);
            reset();
        }
        if(Math.pow(e.getX()-X(1825),2)+Math.pow(e.getY()-Y(450),2)<Math.pow(X(125),2) && System.currentTimeMillis()-buyTimer>300 && gunOnScreen<numberOfGuns-1){
            gunOnScreen++;
            guns.setGunLvl(gunOnScreen);
            reset();
        }
    }

    void reset(){
        buyTimer = System.currentTimeMillis();
        player.setX(X(1000));
        player.setY(Y(425));
        guns.reset();
        guns.setAmmo(100000);
    }

    void setGunOnScreen(int gunOnScreen){
        this.gunOnScreen = gunOnScreen;
    }

    void resetBuy() {
        for(int a = 0; a < numberOfGuns; a ++) {
            gunValues[a][0] = upgrades.getGunAmmo(a)[Integer.parseInt(data.getGunLvls(a).substring(0, 1))];
            gunValues[a][1] = upgrades.getGunDamage(a)[Integer.parseInt(data.getGunLvls(a).substring(1, 2))];
            gunValues[a][2] = upgrades.getGunUnique(a)[Integer.parseInt(data.getGunLvls(a).substring(2, 3))];
            if (!data.getGunLvls(a).substring(0, 1).equals("9")) {
                nextGunValues[a][0] = upgrades.getGunAmmo(a)[Integer.parseInt(data.getGunLvls(a).substring(0, 1)) + 1];
                nextGunCost[a][0] = upgrades.simplifyScore(upgrades.getGunAmmoCost(a)[Integer.parseInt(data.getGunLvls(a).substring(0, 1))]);
            }
            if (!data.getGunLvls(a).substring(1, 2).equals("9")) {
                nextGunValues[a][1] = upgrades.getGunDamage(a)[Integer.parseInt(data.getGunLvls(a).substring(1, 2)) + 1];
                nextGunCost[a][1] = upgrades.simplifyScore(upgrades.getGunDamageCost(a)[Integer.parseInt(data.getGunLvls(a).substring(1, 2))]);
            }
            if (!data.getGunLvls(a).substring(2, 3).equals("4")) {
                nextGunValues[a][2] = upgrades.getGunUnique(a)[Integer.parseInt(data.getGunLvls(a).substring(2, 3)) + 1];
                nextGunCost[a][2] = upgrades.simplifyScore(upgrades.getGunUniqueCost(a)[Integer.parseInt(data.getGunLvls(a).substring(2, 3))]);
            }
        }
    }


    private void drawUpgradeButton(Paint paint, Canvas canvas, float x, float y){
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(X(60));
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
