package com.cowbrain_games.gravity_gauntlet;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

public class WinScreen {

    void tick(){

    }
    void render(Canvas canvas){
        Paint paint = new Paint();
        canvas.drawColor(Color.BLACK);
        paint.setColor(Color.WHITE);
        paint .setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(Typeface.create("Arial",Typeface.BOLD));
        paint.setTextSize((180));
        canvas.drawText("CONGRATULATIONS!", X(1000),Y(450),paint);
    }

    private float X(float X) {
        return X * Resources.getSystem().getDisplayMetrics().widthPixels/ 2000f;
    }

    private float Y(float Y) {
        return Y * Resources.getSystem().getDisplayMetrics().heightPixels/ 1000f;
    }


}
