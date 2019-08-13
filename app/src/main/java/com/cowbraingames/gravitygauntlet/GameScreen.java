package com.cowbraingames.gravitygauntlet;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.LinkedList;

public class GameScreen {
    private int width = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int height = Resources.getSystem().getDisplayMetrics().heightPixels;
    private Player player;
    private Upgrades upgrades;
    private LinkedList<Meteor> meteors = new LinkedList<>();
    private ArrayList<Meteor> remove = new ArrayList<>();

    GameScreen(){
        player = new Player(100,(int)X(100));
        for(int a = 0; a < 30; a ++){
            meteors.add(new Meteor(30,upgrades));
        }

    }

    void tick(){
        player.tick();
        for(Meteor meteor:meteors){
            meteor.tick(player,meteors,this);
        }

    }

    void render(Canvas canvas){
        canvas.drawColor(Color.BLACK);
        player.render(canvas);
        for(Meteor meteor:meteors){
            meteor.render(canvas);
        }
        for(Meteor rem : remove){
            meteors.remove(rem);
        }
        remove.clear();

    }

    void touched(MotionEvent e){
        player.touched(e);
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
}
