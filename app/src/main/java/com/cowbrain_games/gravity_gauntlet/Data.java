package com.cowbrain_games.gravity_gauntlet;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Data {
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private Context context;

    Data(Context context){
        this.context = context;
    }

    void setGold(String gold){
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mEditor = mPreferences.edit();
        mEditor.putString("Gold",gold);
        mEditor.apply();
    }

    String getGold(){
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return mPreferences.getString("Gold","0");
    }
}
