package com.cowbrain_games.gravity_gauntlet;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import java.util.LinkedList;

public class TutorialScreen extends GameScreen {
    private int state = 0;
    private long timer = System.currentTimeMillis();

    TutorialScreen(Data data, Upgrades upgrades,Player player, Guns guns){
        super(data,upgrades,player,guns);
    }

    void render(Canvas canvas, Bitmap coin, Bitmap shoot, Bitmap ammo, Bitmap grenade, Bitmap black_hole, Bitmap arrow) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(X(60));
        canvas.drawColor(Color.BLACK);
        for(Bullets bullet:bullets){
            bullet.render(canvas, grenade, black_hole);
        }
        player.render(canvas);
        guns.render(canvas);

        if(state > 1) {
            for (Meteor meteor : meteors) {
                meteor.render(canvas, X(15));
            }
        }
        bullets.addAll(addBullets);
        for(Bullets bullet:removeBullets){
            bullets.remove(bullet);
        }
        addBullets.clear();
        removeBullets.clear();

        //drawing health bar
        int healthX = 1600;
        if(state > 0) {
            paint.setColor(Color.RED);
            canvas.drawRect(X(healthX), Y(150), X(healthX + 350), Y(250), paint);
            paint.setColor(Color.GREEN);
            canvas.drawRect(X(healthX), Y(150), X(healthX) + (float) upgrades.divideScores(player.getHealth(), player.getMaxHealth()) * X(350), Y(250), paint);
            paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(X(5));
            canvas.drawRect(X(healthX), Y(150), X(healthX + 350), Y(250), paint);
            paint.setTextSize(X(80));
        }

        //drawing back button
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(X(1600),Y(25),X(1950),Y(125),paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setARGB(255,212,175,55);
        paint.setStrokeWidth(X(10));
        canvas.drawRect(X(1600),Y(25),X(1950),Y(125),paint);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(X(80));
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("BACK", X(1775), Y(100),paint);

        //draw gold earned
        if(state > 3) {
            paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.FILL);
            paint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(upgrades.simplifyScore(getGoldEarned()), X(150), Y(100), paint);
            canvas.drawBitmap(Bitmap.createScaledBitmap(coin, (int) X(80), (int) X(80), true), X(160) + getGoldEarned().length() * X(20), Y(35), paint);
            paint.setStyle(Paint.Style.STROKE);
            paint.setARGB(255, 212, 175, 55);
            paint.setStrokeWidth(X(3));
            canvas.drawText(upgrades.simplifyScore(goldEarned), X(150), Y(100), paint);
        }

        //shoot icon
        if(state > 3) {
            if (data.getShootPosition() == 0) {
                shootX = 1825;
            } else {
                shootX = 175;
            }
            paint.setStyle(Paint.Style.FILL);
            paint.setStrokeWidth(X(10));
            paint.setColor(Color.RED);
            canvas.drawCircle(X(shootX), Y(825), (int) X(150), paint);
            paint.setStyle(Paint.Style.STROKE);
            paint.setARGB(255, 212, 175, 55);
            canvas.drawCircle(X(shootX), Y(825), (int) X(150), paint);
            canvas.drawBitmap(Bitmap.createScaledBitmap(shoot, (int) X(250), (int) X(250), true), X(shootX - 125), Y(825) - X(125), paint);
            paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.FILL);
            paint.setTextSize(X(80));
            canvas.drawText(guns.getAmmo(), X(shootX + 25), Y(660), paint);
            canvas.drawBitmap(Bitmap.createScaledBitmap(ammo, (int) X(150), (int) X(100), true), X(shootX - 95) - X(18) * guns.getAmmo().length(), Y(590), paint);
        }

        //draw text box
        paint.setARGB(200,0,128,255);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(X(250),Y(750),X(1750),Y(975),paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(X(15));
        paint.setARGB(255,212,175,55);
        canvas.drawRect(X(250),Y(750),X(1750),Y(975),paint);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(Color.WHITE);
        if(state == 0){
            paint.setTextSize(X(70));
            canvas.drawText("Move your player by sliding your finger",X(1000),Y(850),paint);
            canvas.drawText("across the screen.",X(1000),Y(920),paint);
        }else if(state == 1){
            canvas.drawText("This is your health bar.",X(1000),Y(880),paint);
            canvas.drawBitmap(Bitmap.createScaledBitmap(arrow, (int)X(150), (int)X(200),true),X(1710), Y(250),paint);

        }else if(state == 2){
            paint.setTextSize(X(70));
            canvas.drawText("Meteors spawn at the edge of the screen",X(1000),Y(850),paint);
            canvas.drawText("and gravity pulls them towards you.",X(1000),Y(920),paint);

        }else if(state == 3){
            paint.setTextSize(X(70));
            canvas.drawText("If you hit a meteor, you will lose health.",X(1000),Y(850),paint);
            canvas.drawText("Stay alive for as long as possible!",X(1000),Y(920),paint);

        }else if(state == 4){
            paint.setTextSize(X(70));
            canvas.drawText("Shoot the meteors to make them disappear",X(1000),Y(850),paint);
            canvas.drawText("You have limited ammo.",X(1000),Y(920),paint);
        }else if(state == 5){
            paint.setTextSize(X(70));
            canvas.drawText("Some meteors are stronger than others",X(1000),Y(850),paint);
            canvas.drawText("and can take multiple hits to destroy.",X(1000),Y(920),paint);
        }else if(state == 6){
            paint.setTextSize(X(100));
            canvas.drawText("Earn gold by staying alive",X(1000),Y(920),paint);
            canvas.drawBitmap(Bitmap.createScaledBitmap(arrow, (int)X(150), (int)X(200),true),X(1710), Y(250),paint);
        }else if(state == 7){
            paint.setTextSize(X(70));
            canvas.drawText("You can use gold to upgrade your",X(1000),Y(850),paint);
            canvas.drawText("player and buy new guns!",X(1000),Y(920),paint);
        }

        //draw back button
    }

    @Override
    void tick(GameView gameView, EndScreen endScreen) {
        goldEarned = upgrades.addScores(goldEarned, "1");
        player.tick();
        guns.tick(addBullets);
        for(Bullets bullet: bullets){
            bullet.tick(meteors,upgrades,data,removeBullets,remove,gameView);
        }
        if(state > 0){
            for(Meteor meteor: meteors){
                meteor.tick(player,meteors,this,new LinkedList<Bullets>(), 0);
            }
        }
        for(Meteor rem : remove){
            meteors.remove(rem);
        }
        remove.clear();
        if(state == 1 && System.currentTimeMillis()-timer > 3000) {
            state ++;
            timer = System.currentTimeMillis();
            meteors.add(new Meteor((int)X(40),upgrades,0));
            meteors.add(new Meteor((int)X(50),upgrades,0));
            meteors.add(new Meteor((int)X(30),upgrades,0));
        }
        if(state >= 2 && System.currentTimeMillis()-timer > 5000) {
            state ++;
            timer = System.currentTimeMillis();
            meteors.add(new Meteor((int)X((float)Math.random()*30 + 30),upgrades,0));
            if(state == 5){
                meteors.add(new Meteor((int)X(60),upgrades,1));
            }
        }

    }

    @Override
    void reset() {
        player.tutorialReset();
        guns.tutorialReset();
        meteors.clear();
        bullets.clear();
        state = 0;
        meteorLvl = 1;
        goldEarned = "0";
        player.setX(X(1000));
        player.setY(Y(500));
        timer = System.currentTimeMillis();
    }

    @Override
    void touched(MotionEvent e, GameView gameView) {
        for(int a = 0;a < e.getPointerCount(); a ++){
            if(Math.pow(e.getX(a)-X(shootX),2)+Math.pow(e.getY(a)-Y(825),2)<Math.pow((int)X(150),2) && state > 3){
                guns.shoot(addBullets);
                continue;
            }
            player.touched(e,a);
            if(state == 0 && System.currentTimeMillis()-timer > 500){
                state++;
                timer = System.currentTimeMillis();
            }
            if(e.getX(a)>X(1600) && e.getX(a)<X(1950) && e.getY(a)>Y(25) && e.getY(a) < Y(125)){
                gameView.setGameState(0);
            }
        }
    }
}
