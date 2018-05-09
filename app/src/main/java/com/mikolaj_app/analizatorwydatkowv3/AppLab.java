package com.mikolaj_app.analizatorwydatkowv3;


import android.util.Log;

import org.joda.time.DateTime;

public class AppLab {
    private static AppLab sAppLab;


    private AppLab(){

    }

    public static AppLab get(){
        if(sAppLab == null){
            sAppLab = new AppLab();
        }
        return sAppLab;
    }


    public void setMoney(){
        float moneyPerDay = ModelClass.get().getAllMoney() / ModelClass.get().getAllDaysMonth();
        ModelClass.get().setMoneyPerDay(moneyPerDay);

        float moneyPerWeek = ModelClass.get().getMoneyPerDay() * 7;
        ModelClass.get().setMoneyPerWeek(moneyPerWeek);
    }


    public void updateMoney(){
        float allMoney = ModelClass.get().getAllMoney();
        float moneyPerWeek = ModelClass.get().getMoneyPerWeek();

        allMoney -= ModelClass.get().getExpensesMoney();
        moneyPerWeek -= ModelClass.get().getExpensesMoney();

        ModelClass.get().setAllMoney(allMoney);
        ModelClass.get().setMoneyPerWeek(moneyPerWeek);
    }


    public void nextWeek(){
        float lastWeek = ModelClass.get().getMoneyPerWeek();

        if(ModelClass.get().getCurrentDay() >= 29){
            ModelClass.get().setMoneyPerWeek(ModelClass.get().getAllMoney());
        }else{
            float moneyPerWeek = ModelClass.get().getMoneyPerDay() * 7;
            moneyPerWeek += lastWeek;
            ModelClass.get().setMoneyPerWeek(moneyPerWeek);
        }
    }

    public void nextMonth(){
        float allFinalMoney = ModelClass.get().getAllFinalMoney();
        ModelClass.get().setAllMoney(allFinalMoney);
    }



}
