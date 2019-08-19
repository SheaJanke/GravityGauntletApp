package com.cowbrain_games.gravity_gauntlet;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

class Bullets {
    private int width = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int height = Resources.getSystem().getDisplayMetrics().heightPixels;
    private float velX;
    private float velY;
    private float x;
    private float y;
    private float rotation;
    private float rotationX;
    private float rotationY;

    Bullets(Player player, Guns guns){
        rotation = guns.getRotation();
        x = player.getX();
        y = player.getY()-Y(80);
        rotationX = player.getX();
        rotationY = player.getY();
        velY = X(-10);
        velX = 0;
    }

    void tick(){
        y+=velY;
        x+=velX;
    }

    void render(Canvas canvas,Player player){
        int saveState = canvas.save();
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        canvas.rotate(rotation,rotationX,rotationY);
        canvas.drawCircle(x,y,20,paint);
        canvas.restore();

    }

    private float X(float X){
        return X * width/2000f;
    }

    private float Y(float Y){
        return Y* height/1000f;
    }
}
