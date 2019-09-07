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
    private GunScreen gunScreen;
    private WinScreen winScreen;
    private Data data;
    private Player player;
    private Guns guns;
    private int gameState = 0;
    private Bitmap star = BitmapFactory.decodeResource(getResources(),R.drawable.star);
    private Bitmap coin = BitmapFactory.decodeResource(getResources(),R.drawable.coin);
    private Bitmap shoot = BitmapFactory.decodeResource(getResources(),R.drawable.shoot);
    private Bitmap ammo = BitmapFactory.decodeResource(getResources(),R.drawable.ammo);
    private Bitmap next = BitmapFactory.decodeResource(getResources(),R.drawable.next);
    private Bitmap lock = BitmapFactory.decodeResource(getResources(),R.drawable.lock);
    private Bitmap grenade = BitmapFactory.decodeResource(getResources(),R.drawable.grenade);
    private Bitmap laser_cannon = BitmapFactory.decodeResource(getResources(),R.drawable.laser_cannon);
    private Bitmap black_hole_generator = BitmapFactory.decodeResource(getResources(),R.drawable.black_hole_generator);
    private Bitmap black_hole = BitmapFactory.decodeResource(getResources(),R.drawable.black_hole);

    public GameView(Context context){
        super(context);
        data = new Data(context);
        upgrades = new Upgrades(data);
        player = new Player(upgrades,data);
        guns = new Guns(player,upgrades,data,laser_cannon,black_hole_generator,black_hole);
        startScreen = new StartScreen(star);
        gameScreen = new GameScreen(data,upgrades,player,guns);
        endScreen = new EndScreen(star,coin,data,gameScreen);
        upgradeScreen = new UpgradeScreen();
        gunScreen = new GunScreen(player,guns,data,upgrades);
        winScreen = new WinScreen();



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
                gameScreen.render(canvas,coin,shoot,ammo,grenade,black_hole);
            }else if(gameState == 2){
                endScreen.render(canvas);
            }else if(gameState == 3){
                upgradeScreen.render(canvas,upgrades,data,star,coin);
            }else if(gameState == 4){
                gunScreen.render(canvas,coin,next,lock,grenade,black_hole);
            }else if(gameState == 5){
                winScreen.render(canvas);
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
        }else if(gameState == 4){
            gunScreen.tick();
        }else if(gameState == 5){
            winScreen.tick();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        int pointerIndex = e.getActionIndex();
        int maskedAction = e.getActionMasked();
        switch (maskedAction) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN: {
                if(gameState == 0){
                    startScreen.touched(e,this,gameScreen,upgradeScreen);
                }else if(gameState == 1){
                    gameScreen.touched(e);
                }else if(gameState == 2){
                    endScreen.touched(e,this, gameScreen, upgradeScreen);
                }else if(gameState == 3){
                    upgradeScreen.touched(e,data,upgrades,gameScreen,this,startScreen,gunScreen);
                }else if(gameState == 4){
                    gunScreen.touched(e,this,upgradeScreen);
                }
            }
            case MotionEvent.ACTION_MOVE:{
                if(gameState == 1){
                    gameScreen.touched(e);
                }
            }
        }
        invalidate();
        return true;
    }

    void setGameState(int gameState){
        this.gameState = gameState;
    }

}
