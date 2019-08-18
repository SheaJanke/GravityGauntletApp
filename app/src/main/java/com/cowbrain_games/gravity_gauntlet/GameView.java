package com.cowbrain_games.gravity_gauntlet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    private StartScreen startScreen;
    private GameScreen gameScreen;
    private EndScreen endScreen;
    private Upgrades upgrades;
    private UpgradeScreen upgradeScreen;
    private Data data;
    private int gameState = 0;
    private Bitmap star = BitmapFactory.decodeResource(getResources(),R.drawable.star);
    private Bitmap coin = BitmapFactory.decodeResource(getResources(),R.drawable.coin);


    public GameView(Context context){
        super(context);
        data = new Data(context);
        upgrades = new Upgrades(data);
        startScreen = new StartScreen(star);
        gameScreen = new GameScreen(data,upgrades);
        endScreen = new EndScreen(star,coin,data,gameScreen);
        upgradeScreen = new UpgradeScreen();


        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);

        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        while(retry){
            try{
                thread.setRunning(false);
                thread.join();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        if(canvas != null){
            if(gameState == 0){
                startScreen.render(canvas);
            }else if(gameState ==1){
                gameScreen.render(canvas,coin);
            }else if(gameState == 2){
                endScreen.render(canvas);
            }else if(gameState == 3){
                upgradeScreen.render(canvas,upgrades,data,star,coin);
            }
        }
    }

    void update(){
        if(gameState == 0){
            startScreen.tick();
        }else if(gameState == 1){
            gameScreen.tick(this,endScreen);
        }else if(gameState == 2){
            endScreen.tick();
        }else if(gameState == 3){
            upgradeScreen.tick();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if(gameState == 0){
            startScreen.touched(e,this,gameScreen,upgradeScreen);
        }else if(gameState == 1){
            gameScreen.touched(e);
        }else if(gameState == 2){
            endScreen.touched(e,this, gameScreen, upgradeScreen);
        }else if(gameState == 3){
            upgradeScreen.touched(e,data,upgrades,gameScreen,this,startScreen);
        }

        return true;
    }

    void setGameState(int gameState){
        this.gameState = gameState;
    }

}
