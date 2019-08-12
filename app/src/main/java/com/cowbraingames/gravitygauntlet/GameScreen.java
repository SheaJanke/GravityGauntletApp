package com.cowbraingames.gravitygauntlet;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

public class GameScreen {
    private Player player;
    GameScreen(){
        player = new Player();
    }
    void tick(){
        player.tick();
    }

    void render(Canvas canvas){
        canvas.drawColor(Color.BLACK);
        player.render(canvas);
    }

    void touched(MotionEvent e){
        player.touched(e);
    }
}
