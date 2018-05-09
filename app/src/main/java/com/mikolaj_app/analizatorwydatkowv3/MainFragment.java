package com.mikolaj_app.analizatorwydatkowv3;

import android.app.Activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MainFragment extends RestoreDataManager {

    public ModelClass sModel;
    public AppLab sAppLab;
    private TextView mCounterAllMoney;
    private TextView mCounterWeekMoney;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restoreData();
        sAppLab = AppLab.get();
        sModel = ModelClass.get();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main_fragment, container, false);

        TextView dateText = view.findViewById(R.id.DateText);
        dateText.setText("Witaj w analizatorze wydatków, dzisiaj mamy " + sModel.getCurrentDay() + " dzień miesiąca");

        mCounterAllMoney = view.findViewById(R.id.CounterAllMoney);
        mCounterWeekMoney = view.findViewById(R.id.CounterWeekMoney);

        Button expensesButton = view.findViewById(R.id.expenseButton);
        expensesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expensesAction();
            }
        });

        return view;
    }//koniec metody





    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != Activity.RESULT_OK && requestCode != REQUEST_EXPENSES_MONEY) {
            return;
        }

        //wydatki
        if (requestCode == REQUEST_EXPENSES_MONEY){
            float expensesMoney = (float) data
                    .getSerializableExtra(ExpensesPickerFragment.EXTRA_EXPENSES_MONEY);
            String expensesDescription = (String) data
                    .getSerializableExtra(ExpensesPickerFragment.EXTRA_DESCRIPTION_EXPENSES);
            Log.d("money expenses: ", "" + expensesMoney +""+expensesDescription);

            sModel.setExpensesMoney(expensesMoney);
            sModel.setDescription(expensesDescription);
            sAppLab.updateMoney();
            getContentValues(); //dodaje wartosci do bazy
            updateBase();
            onResumeExtra();
            PagerAdapter.mFragments.get(2).onResume();
            PagerAdapter.mFragments.get(0).onResume();
        }

        //dodaje pierwszy raz kase
        if (requestCode == REQUEST_MONEY & ModelClass.get().isFirstStateDialog()) {
            float AllMoney = (float) data
                    .getSerializableExtra(MoneyPickerFragment.EXTRA_MONEY);
            Log.d("money", "" + AllMoney);
            sModel.setAllMoney(AllMoney);
            sModel.setAllFinalMoney(AllMoney);
            sAppLab.setMoney();
            onResumeExtra();
            ModelClass.get().setFirstStateDialog(false);
        }

        //dodaje dodatkowa kase
        if(requestCode == REQUEST_MONEY & ModelClass.get().isAddSuperMoney()){
            float superMoney = (float) data
                    .getSerializableExtra(MoneyPickerFragment.EXTRA_MONEY);
            superMoney += sModel.getAllMoney();
            sModel.setAllMoney(superMoney);
            sAppLab.setMoney();
            onResumeExtra();
            ModelClass.get().setAddSuperMoney(false);
        }


    }


    @Override
    public void onResume() {
        super.onResume();
        onResumeExtra();
    }



    public void expensesAction(){
        FragmentManager manager = getFragmentManager();
        ExpensesPickerFragment dialog = new ExpensesPickerFragment();
        dialog.setTargetFragment(MainFragment.this, REQUEST_EXPENSES_MONEY); //definiowanie fragmentu docelowego
        dialog.show(manager,DIALOG_EXPENSES_MONEY);
    }

    //moze ok
    public void onResumeExtra(){
        String mAllMoneyString = String.format("%.1f",sModel.getAllMoney());
        String mMoneyPerWeekString = String.format("%.1f",sModel.getMoneyPerWeek());

        mCounterAllMoney.setText(mAllMoneyString);
        mCounterWeekMoney.setText(mMoneyPerWeekString);
    }



}//koniec klasy


