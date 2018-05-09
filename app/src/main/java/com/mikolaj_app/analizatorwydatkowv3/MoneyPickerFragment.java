package com.mikolaj_app.analizatorwydatkowv3;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;


/**
 * Created by Mikolaj on 03.03.2018.
 */

public class MoneyPickerFragment extends DialogFragment {

    private float mPutMoney;
    private EditText mEditText;
    public static final String EXTRA_MONEY =
            "com.mikolaj_app.analizatorwydatkowv3.money";

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_money, null);

        mEditText = (EditText) v.findViewById(R.id.MoneyEdit);


        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.money_picker_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mPutMoney =  Float.parseFloat(String.valueOf(mEditText.getText()));
                        sendResults(Activity.RESULT_OK,mPutMoney);
                    }
                })
                .create();

    }//koniec glownej metody

    //wysylanie danych do fragmentu docelowego
    private void sendResults(int resultCode, float money){
        if (getTargetFragment() == null){
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_MONEY, money);

        getTargetFragment()
                .onActivityResult(getTargetRequestCode(),resultCode,intent);
    }

}



/*
    return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.money_picker_title)
                .setPositiveButton(android.R.string.ok, null)
                .create();

    }
 */


/*
public class MoneyPickerFragment extends DialogFragment {

    private NumberPicker mMoneyPicker;
    int money;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final ModelClass model = ModelClass.get();

        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_money, null);

        mMoneyPicker = (NumberPicker) v.findViewById(R.id.dialog_money_picker);
        mMoneyPicker.setMinValue(0);
        mMoneyPicker.setMaxValue(99999);

        mMoneyPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                model.setAllMoney(i1);
                Log.d("money","kasa:" + model.getAllMoney());
            }
        });



        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.money_picker_title)
                .setPositiveButton(android.R.string.ok,null)
                .create();

    }

}
 */