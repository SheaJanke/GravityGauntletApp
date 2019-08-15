package com.cowbrain_games.gravity_gauntlet;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;

class UpgradeScreen {
    private long startTime;

    UpgradeScreen() {
        startTime = System.currentTimeMillis();
    }

    void tick() {

    }

    void render(Canvas canvas, Upgrades upgrades, Data data) {
        Paint paint = new Paint();
        paint.setColor(Color.CYAN);
        paint.setTextSize(X(150));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(Typeface.create("Arial",Typeface.BOLD));
        canvas.drawText("UPGRADES", X(1000), Y(75),paint);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(X(5));
        canvas.drawText("UPGRADES", X(1000), Y(75),paint);


    }

    void touched(MotionEvent e) {

    }

    private float X(float X) {
        return X * Resources.getSystem().getDisplayMetrics().widthPixels+100 / 2000f;
    }

    private float Y(float Y) {
        return Y * Resources.getSystem().getDisplayMetrics().widthPixels / 1000f;
    }
}