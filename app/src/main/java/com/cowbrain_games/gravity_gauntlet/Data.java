package com.cowbrain_games.gravity_gauntlet;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

class Data {
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private Context context;
    private Upgrades upgrades = new Upgrades(this);

    Data(Context context){
        this.context = context;
    }

    void setGold(String gold){
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mEditor = mPreferences.edit();
        mEditor.putString("Gold",upgrades.simplifyScore(gold));
        mEditor.apply();
    }

    String getGold(){
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return mPreferences.getString("Gold","0");
    }

    void setStartLvl(int startLvl){
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mEditor = mPreferences.edit();
        mEditor.putInt("startLvl",startLvl);
        mEditor.apply();
    }

    int getStartLvl(){
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return mPreferences.getInt("startLvl",0);
    }

    void setMaxHealthLvl(int maxHealthLvl){
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mEditor = mPreferences.edit();
        mEditor.putInt("maxHealthLvl",maxHealthLvl);
        mEditor.apply();
    }

    int getMaxHealthLvl(){
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return mPreferences.getInt("maxHealthLvl",0);
    }

    void setScoreMultiplierLvl(int scoreMultiplierLvl){
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mEditor = mPreferences.edit();
        mEditor.putInt("scoreMultiplierLvl",scoreMultiplierLvl);
        mEditor.apply();
    }

    int getScoreMultiplierLvl(){
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return mPreferences.getInt("scoreMultiplierLvl",0);
    }

    void setPlayerWeightLvl(int playerWeightLvl){
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mEditor = mPreferences.edit();
        mEditor.putInt("playerWeightLvl",playerWeightLvl);
        mEditor.apply();
    }

    int getPlayerWeightLvl(){
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return mPreferences.getInt("playerWeightLvl",0);
    }

    void setGun1Lvls(String gun1Lvls){
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mEditor = mPreferences.edit();
        mEditor.putString("gun1Lvls",gun1Lvls);
        mEditor.apply();
    }

    String getGun1Lvls(){
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return mPreferences.getString("gun1Lvls","000");
    }

    void reset(){
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mEditor = mPreferences.edit();
        mEditor.putString("Gold", "0");
        mEditor.putInt("maxHealthLvl",0);
        mEditor.putInt("startLvl", 0);
        mEditor.putInt("scoreMultiplierLvl",0);
        mEditor.putInt("playerWeightLvl",0);
        mEditor.putString("gun1Lvls","000");
        mEditor.apply();
    }
}
