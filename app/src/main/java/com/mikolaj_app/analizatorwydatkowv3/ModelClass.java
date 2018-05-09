package com.mikolaj_app.analizatorwydatkowv3;


import org.joda.time.DateTime;


/**
 * Created by Mikolaj on 03.03.2018.
 */

public class ModelClass {
    private static ModelClass sModelClass;
    public float mAllFinalMoney;
    public float mAllMoney;
    public float mMoneyPerDay;
    public float mMoneyPerWeek;
    public float mExpensesMoney;
    public String mDescription;


    public DateTime dateTime;
    public int mCurrentDay;
    public int mAllDaysMonth;
    public boolean mFirstStateDialog;
    public boolean mAddSuperMoney;

    private ModelClass(){
        dateTime = DateTime.now();              //aktualna data

        mCurrentDay = dateTime.getDayOfMonth(); //aktualny dzien miesiaca
        mAllDaysMonth = dateTime.dayOfMonth().withMaximumValue().getDayOfMonth(); //wszystkie dni w miesiacu
    }

    public static ModelClass get(){
            if(sModelClass == null){
                sModelClass = new ModelClass();
            }
        return sModelClass;
    }

    public float getAllMoney() {
        return mAllMoney;
    }

    public void setAllMoney(float allMoney) {
        mAllMoney = allMoney;
    }

    public int getCurrentDay() {
        return mCurrentDay;
    }


    public int getAllDaysMonth() {
        return mAllDaysMonth;
    }


    public DateTime getDate(){
        return dateTime;
    }


    public boolean isFirstStateDialog() {
        return mFirstStateDialog;
    }

    public void setFirstStateDialog(boolean firstStateDialog) {
        mFirstStateDialog = firstStateDialog;
    }

    public float getMoneyPerDay() {
        return mMoneyPerDay;
    }

    public void setMoneyPerDay(float moneyPerDay) {
        mMoneyPerDay = moneyPerDay;
    }

    public float getMoneyPerWeek() {
        return mMoneyPerWeek;
    }

    public void setMoneyPerWeek(float moneyPerWeek) {
        mMoneyPerWeek = moneyPerWeek;
    }

    public float getExpensesMoney() {
        return mExpensesMoney;
    }

    public void setExpensesMoney(float expensesMoney) {
        mExpensesMoney = expensesMoney;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public boolean isAddSuperMoney() {
        return mAddSuperMoney;
    }

    public void setAddSuperMoney(boolean addSuperMoney) {
        mAddSuperMoney = addSuperMoney;
    }

    public float getAllFinalMoney() {
        return mAllFinalMoney;
    }

    public void setAllFinalMoney(float allFinalMoney) {
        mAllFinalMoney = allFinalMoney;
    }
}
