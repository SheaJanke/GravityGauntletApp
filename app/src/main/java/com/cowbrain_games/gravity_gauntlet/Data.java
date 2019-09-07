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

    void setAllGunLvls(String gunLvls){
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mEditor = mPreferences.edit();
        mEditor.putString("gunLvls",gunLvls);
        mEditor.apply();
    }

    void setGunLvls(int gunLvl, String gunLvls){
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mEditor = mPreferences.edit();
        String allGunLvls = "";
        for(int a = 0; a < 6; a ++){
            if(a!=gunLvl){
                allGunLvls+=getGunLvls(a);
            }else{
                allGunLvls+=gunLvls;
            }
        }
        mEditor.putString("gunLvls",allGunLvls);
        mEditor.apply();
    }


    String getGunLvls(int gunLvl){
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return mPreferences.getString("gunLvls","000000000000000000").substring(gunLvl*3, gunLvl*3 + 3);
    }

    void setGunPurchases(int gunLvl, String state){
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mEditor = mPreferences.edit();
        String allGunPurchases = "";
        for(int a = 0; a < 6; a ++){
            if(a!=gunLvl){
                allGunPurchases+=getGunPurchases(a);
            }else{
                allGunPurchases+=state;
            }
        }
        mEditor.putString("gunPurchases",allGunPurchases);
        mEditor.apply();

    }

    String getGunPurchases(int gunLvl){
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return mPreferences.getString("gunPurchases","200000").substring(gunLvl, gunLvl+1);
    }

    String getAllGunPurchases(){
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return mPreferences.getString("gunPurchases","200000");
    }

    void reset(){
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mEditor = mPreferences.edit();
        mEditor.putString("Gold", "0");
        mEditor.putInt("maxHealthLvl",0);
        mEditor.putInt("startLvl", 0);
        mEditor.putInt("scoreMultiplierLvl",0);
        mEditor.putInt("playerWeightLvl",0);
        mEditor.putString("gunLvls", "000000000000000000");
        mEditor.putString("gunPurchases", "200000");
        mEditor.apply();
    }
}
