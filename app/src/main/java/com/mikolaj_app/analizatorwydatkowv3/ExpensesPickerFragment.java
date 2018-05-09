package com.mikolaj_app.analizatorwydatkowv3;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Mikolaj on 04.03.2018.
 */

public class ExpensesPickerFragment extends DialogFragment {

    private EditText mEditMoneyText;
    private EditText mEditDescriptionText;
    private String mDescriptionText;
    private float mExpensesMoney;
    public static final String EXTRA_EXPENSES_MONEY =
            "com.mikolaj_app.analizatorwydatkowv3.money_expenses";
    public static final String EXTRA_DESCRIPTION_EXPENSES=
            "com.mikolaj_app.analizatorwydatkowv3.expenses_description";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_expenses, null);

        mEditMoneyText = v.findViewById(R.id.MoneyEditExpens);
        mEditDescriptionText = v.findViewById(R.id.MoneyEditDescription);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.money_picker_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mExpensesMoney =  Float.parseFloat(String.valueOf(mEditMoneyText.getText()));
                        mDescriptionText = String.valueOf(mEditDescriptionText.getText());
                        sendResults(Activity.RESULT_FIRST_USER,mExpensesMoney,mDescriptionText);
                    }
                })
                .create();

    }//glowna metoda


    //wysylanie danych do fragmentu docelowego
    private void sendResults(int resultCode, float money, String description){
        if (getTargetFragment() == null){
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_EXPENSES_MONEY, money);
        intent.putExtra(EXTRA_DESCRIPTION_EXPENSES,description);

        getTargetFragment()
                .onActivityResult(getTargetRequestCode(),resultCode,intent);
    }
}
