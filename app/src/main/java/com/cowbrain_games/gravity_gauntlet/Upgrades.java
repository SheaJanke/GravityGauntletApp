package com.cowbrain_games.gravity_gauntlet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

class Upgrades {
    private String[] health = new String[100];
    private String[] meteorHealth = new String[100];
    private String[] healthCost = new String[100];
    private String[] startLvlCost = new String[100];
    private String[] scoreMultiplier = new String[100];
    private String[] scoreMultiplierCost = new String[100];
    private double[] lvlMultiplier = new double[100];
    private double[] playerWeight = new double[100];
    private String[] playerWeightCost = new String[100];
    private String[][] gunDamage = new String[5][10];
    private String[][] gunDamageCost = new String[5][10];
    private String[][] gunAmmo = new String[5][10];
    private String[][] gunAmmoCost = new String[5][10];
    private String[][] gunBurst = new String[3][5];
    private String[][] gunBurstCost = new String[3][5];
    private ArrayList<String> scoreEndings = new ArrayList<>(Arrays.asList("K","M","B","t","q","Q","s","S","o","n","d"));
    private Data data;

    Upgrades(Data data){
        this.data = data;
        healthCost[0] = "1.00K";
        scoreMultiplierCost[0] = "3.00K";
        startLvlCost[0] = "1.50K";
        playerWeightCost[0] = "2.00K";
        health[0] = "100";
        scoreMultiplier[0] = "1.0";
        meteorHealth[0] = "1";
        gunDamage[0][0] = "1";
        gunDamageCost[0][0] = "3.00K";
        gunAmmo[0][0] = "10";
        gunAmmoCost[0][0] = "2.00K";
        gunBurst[0][0] = "1";
        gunBurstCost[0][0] = "5.00K";

        lvlMultiplier[0] = 1.0;
        playerWeight[0] = 100;

        for(int a  = 1; a < health.length; a ++){
            health[a] =  simplifyScore(multiplyScore(health[a-1], 2.0));
        }
        for(int a  = 1; a < healthCost.length; a ++){
            healthCost[a] = simplifyScore(multiplyScore(healthCost[a-1], 2.1));
        }
        for(int a = 1; a < scoreMultiplier.length; a ++){
            scoreMultiplier[a] =  simplifyScore(multiplyScore(scoreMultiplier[a-1], 1.5));
        }
        for(int a = 1; a < scoreMultiplierCost.length; a ++){
            scoreMultiplierCost[a] = simplifyScore(multiplyScore(scoreMultiplierCost[a-1],1.75));
        }
        for(int a = 1; a < lvlMultiplier.length; a++){
            lvlMultiplier[a] =  lvlMultiplier[a-1] * 1.2;
        }
        for(int a = 1; a < startLvlCost.length; a ++){
            startLvlCost[a] = simplifyScore(multiplyScore(startLvlCost[a-1],1.5));
        }
        for(int a = 1; a < playerWeight.length; a++){
            playerWeight[a] =  playerWeight[a-1] * 0.9;
        }
        for(int a = 1; a < playerWeightCost.length; a++){
            playerWeightCost[a] = simplifyScore(multiplyScore(playerWeightCost[a-1], 1.75));
        }
        for(int a = 1; a < meteorHealth.length; a++){
            meteorHealth[a] = simplifyScore(multiplyScore(meteorHealth[a-1],1.5));
        }
        for(int a = 1; a < gunDamage[0].length; a++){
            gunDamage[0][a] = simplifyScore(multiplyScore(gunDamage[0][a-1],2.0));
            gunDamageCost[0][a] = simplifyScore(multiplyScore(gunDamage[0][a-1],2.5));
        }
        for(int a = 1; a < gunAmmo[0].length; a++){
            gunAmmo[0][a] = addScores(gunAmmo[0][a-1],"10");
            gunAmmoCost[0][a] = simplifyScore(multiplyScore(gunAmmoCost[0][a-1],2.5));
        }
        for(int a = 1; a < gunBurst[0].length; a++){
            gunBurst[0][a] = addScores(gunDamage[0][a-1],"1");
            gunBurstCost[0][a] = simplifyScore(multiplyScore(gunBurstCost[0][a-1],3.5));
        }

    }
    String[] getGunDamage(int gunLvl){
        return gunDamage[gunLvl];
    }

    String[] getGunDamgeCost(int gunLvl){
        return gunDamageCost[gunLvl];
    }
    String[] getGunAmmo(int gunLvl){
        return gunAmmo[gunLvl];
    }

    String[] getGunAmmoCost(int gunLvl){
        return gunAmmoCost[gunLvl];
    }

    String[] getGunBurst(int gunLvl){
        return gunBurst[gunLvl];
    }

    String[] getGunBurstCost(int gunLvl){
        return gunBurstCost[gunLvl];
    }

    String getNextHealth(){
        return health[data.getMaxHealthLvl()+1];
    }

    String getHealth(){
        return health[data.getMaxHealthLvl()];
    }

    String getNextStartLvlCost(){
        return startLvlCost[data.getStartLvl()];
    }

    String getNextHealthCost(){
        return healthCost[data.getMaxHealthLvl()];
    }

    String getScoreMultiplier(){
        return scoreMultiplier[data.getScoreMultiplierLvl()];
    }

    String[] getMeteorHealth(){
        return  meteorHealth;
    }

    String getNextScoreMultiplier(){
        return scoreMultiplier[data.getScoreMultiplierLvl()+1];
    }

    String getNextScoreMultiplierCost(){
        return scoreMultiplierCost[data.getScoreMultiplierLvl()];
    }

    double getLvlMultiplier(int lvl){
        return lvlMultiplier[lvl];
    }

    double getPlayerWeight(){
        return playerWeight[data.getPlayerWeightLvl()];
    }
    double getNextPlayerWeight(){
        return playerWeight[data.getPlayerWeightLvl() + 1];
    }
    String getNextPlayerWeightCost(){
        return playerWeightCost[data.getPlayerWeightLvl()];
    }

    String addScores(String s1, String s2){
        double num1;
        double num2;
        String total;
        int end1 = scoreEndings.indexOf(s1.substring(s1.length()-1));
        int end2 = scoreEndings.indexOf(s2.substring(s2.length()-1));
        if(end1 != -1){
            num1 = Double.parseDouble(s1.substring(0, s1.length()-1));
        }else{
            num1 = Double.parseDouble(s1);
        }
        if(end2 != -1){
            num2 = Double.parseDouble(s2.substring(0, s2.length()-1));
        }else{
            num2 = Double.parseDouble(s2);
        }
        if(end1 == end2){
            num1 += num2;
            if(end1 != -1){
                total = num1 + s1.substring(s1.length()-1);
            }else{
                total = Double.toString(num1);
            }
            return simplifyScore(total);
        }else if(end1 > end2){
            num1 += num2 * Math.pow(1000, end2-end1);
            total = num1 + s1.substring(s1.length()-1);
            return total;
        }else{
            num2 += num1 * Math.pow(1000, end1-end2);
            total = num2 + s2.substring(s2.length()-1);
            return total;
        }
    }

    String subtractScore(String s1, String s2){
        double num1;
        double num2;
        String total;
        int end1 = scoreEndings.indexOf(s1.substring(s1.length()-1));
        int end2 = scoreEndings.indexOf(s2.substring(s2.length()-1));
        if(end1 != -1){
            num1 = Double.parseDouble(s1.substring(0, s1.length()-1));
        }else{
            num1 = Double.parseDouble(s1);
        }
        if(end2 != -1){
            num2 = Double.parseDouble(s2.substring(0, s2.length()-1));
        }else{
            num2 = Double.parseDouble(s2);
        }
        if(end1 == end2){
            num1 -= num2;
            if(end1 != -1){
                total = num1 + s1.substring(s1.length()-1);
            }else{
                total = Double.toString(num1);
            }
            return total;
        }else{
            num1 -= num2 * Math.pow(1000, end2-end1);
            total = num1 + s1.substring(s1.length()-1);
            return total;
        }
    }

    String multiplyScore(String score, Double mult){
        double num;
        if(scoreEndings.indexOf(score.substring(score.length()-1)) != -1){
            num = Double.parseDouble(score.substring(0, score.length()-1));
        }else{
            num = Double.parseDouble(score);
        }
        num *= mult;
        if(scoreEndings.indexOf(score.substring(score.length()-1)) != -1){
            return num + score.substring(score.length()-1);
        }else{
            return Double.toString(num);
        }

    }

    double divideScores(String s1, String s2){
        double num1;
        double num2;
        int end1 = scoreEndings.indexOf(s1.substring(s1.length()-1));
        int end2 = scoreEndings.indexOf(s2.substring(s2.length()-1));
        if(end1 != -1){
            num1 = Double.parseDouble(s1.substring(0, s1.length()-1));
        }else{
            num1 = Double.parseDouble(s1);
        }
        if(end2 != -1){
            num2 = Double.parseDouble(s2.substring(0, s2.length()-1));
        }else{
            num2 = Double.parseDouble(s2);
        }
        if(end1 == end2){
            num1 /= num2;
            return num1;
        }else{
            num1 /= num2 * Math.pow(1000, end2-end1);
            return num1;
        }
    }

    String simplifyScore(String score){
        double num;
        int end = scoreEndings.indexOf(score.substring(score.length()-1));
        if(end != -1){
            num = Double.parseDouble(score.substring(0, score.length()-1));
        }else{
            num = Double.parseDouble(score);
        }
        if(num >= 1000){
            return formatNum(num/1000) + scoreEndings.get(scoreEndings.indexOf(score.substring(score.length()-1)) + 1);
        }else if(num < 1 && num >=0){
            if(end > 0){
                return formatNum(num*1000) + scoreEndings.get(scoreEndings.indexOf(score.substring(score.length()-1)) - 1);
            }else if(end == 0){
                return formatNum(num*1000);
            }
        }
        if(end != -1){
            return formatNum(num) + score.substring(score.length()-1);
        }else{
            return formatNum(num);
        }
    }

    boolean scoreLarger(String s1, String s2){
        s1 = simplifyScore(s1);
        s2 = simplifyScore(s2);
        double num1;
        double num2;
        int end1 = scoreEndings.indexOf(s1.substring(s1.length()-1));
        int end2 = scoreEndings.indexOf(s2.substring(s2.length()-1));
        if(end1 != -1){
            num1 = Double.parseDouble(s1.substring(0, s1.length()-1));
        }else{
            num1 = Double.parseDouble(s1);
        }
        if(end2 != -1){
            num2 = Double.parseDouble(s2.substring(0, s2.length()-1));
        }else{
            num2 = Double.parseDouble(s2);
        }
        if(num1<0){
            return false;
        }
        if(end1 > end2){
            return true;
        }else if(end1 < end2){
            return false;
        }else{
            return (num1 > num2);
        }

    }

    private String formatNum(double num){
        if(num<10){
            return String.format(Locale.getDefault(),"%.2f",num);
        }else if(num < 100){
            return String.format(Locale.getDefault(),"%.1f",num);
        }else{
            return String.format(Locale.getDefault(),"%.0f",num);
        }
    }
}
