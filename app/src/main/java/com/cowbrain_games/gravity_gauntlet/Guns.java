package com.cowbrain_games.gravity_gauntlet;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

class Guns {
    private int width = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int height = Resources.getSystem().getDisplayMetrics().heightPixels;
    private int rotation = 0;

    void tick(){
        rotation+=6;
        if(rotation>360){
            rotation-=360;
        }
    }

    void render(Canvas canvas, Player player, int gunLvl){
        Paint paint = new Paint();
        int saveCount = canvas.save();
        if(gunLvl == 1){
            paint.setColor(Color.GREEN);
            canvas.rotate(rotation,player.getX(),player.getY());
            canvas.drawCircle(player.getX(),player.getY(),X(30),paint);
            canvas.drawRect(player.getX()-X(30),player.getY()-Y(100),player.getX()+X(30),player.getY(),paint);
            canvas.restoreToCount(saveCount);
            paint.setColor(Color.BLACK);
            canvas.drawText(player.getMoveAngle() + "",player.getX(),player.getY(),paint);
        }

    }

    int getRotation(){
        return  rotation;
    }

    private float X(float X){
        return X * width/2000f;
    }

    private float Y(float Y){
        return Y* height/1000f;
    }


}
