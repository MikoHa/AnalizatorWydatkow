package com.mikolaj_app.analizatorwydatkowv3;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.mikolaj_app.analizatorwydatkowv3.database.ExpensesBaseHelper;
import com.mikolaj_app.analizatorwydatkowv3.database.ExpensesCursorWrapper;
import com.mikolaj_app.analizatorwydatkowv3.database.ExpensesDbSchema.ExpensesTable;
import com.mikolaj_app.analizatorwydatkowv3.database.ExpensesDbSchema.ExpensesTable.Cols;




public class RestoreDataManager extends Fragment {

    private SharedPreferences sharedPref;
    public SQLiteDatabase mDatabase;
    public ContentValues values;

    //stale zmienne dla zapisu stanu:
    public static final String EXTRA_KEY_FIRST_DIALOG = "pobieranie pieniedzy przy wlaczeniu pierwszy raz aplikacji";
    public static final String EXTRA_KEY_ALL_MONEY = "all money";
    public static final String EXTRA_KEY_WEEK_MONEY = "week money";
    public static final String EXTRA_KEY_DAY_MONEY = "day money";
    public static final String EXTRA_KEY_FINAL_ALL_MONEY = "final all money";

    //do requestow
    public static final String DIALOG_MONEY = "DialogMoney";
    public static final String DIALOG_EXPENSES_MONEY = "DialogExpensesMoney";
    public static final int REQUEST_MONEY = -1; //kod żądania dla zwracania danych do fragmentu docelowego
    public static final int REQUEST_EXPENSES_MONEY = 1; //kod żądania dla zwracania danych


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        Context context = getContext().getApplicationContext();
        sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key),Context.MODE_PRIVATE);
        createBase(context);

    }

    public void createBase(Context context){
        mDatabase = new ExpensesBaseHelper(context)
                .getWritableDatabase();
        values = new ContentValues();
    }




    @Override
    public void onPause() {
        super.onPause();
        saveData();
    }

    //zapisywanie danych
    public void saveData(){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putFloat(EXTRA_KEY_FINAL_ALL_MONEY, ModelClass.get().getAllFinalMoney());
        editor.putFloat(EXTRA_KEY_ALL_MONEY, ModelClass.get().getAllMoney());
        editor.putFloat(EXTRA_KEY_WEEK_MONEY, ModelClass.get().getMoneyPerWeek());
        editor.putFloat(EXTRA_KEY_DAY_MONEY,ModelClass.get().getMoneyPerDay());
        editor.putBoolean(EXTRA_KEY_FIRST_DIALOG, ModelClass.get().isFirstStateDialog());

        Log.d("Stan po sejwie ", "" + ModelClass.get().isFirstStateDialog());
        editor.apply();
    }


    public void restoreData(){
        ModelClass.get().setAllFinalMoney(sharedPref.getFloat(EXTRA_KEY_FINAL_ALL_MONEY,0));
        ModelClass.get().setAllMoney(sharedPref.getFloat(EXTRA_KEY_ALL_MONEY,0));
        ModelClass.get().setMoneyPerWeek(sharedPref.getFloat(EXTRA_KEY_WEEK_MONEY,0));
        ModelClass.get().setMoneyPerDay(sharedPref.getFloat(EXTRA_KEY_DAY_MONEY,0));
        ModelClass.get().setFirstStateDialog(sharedPref.getBoolean(EXTRA_KEY_FIRST_DIALOG, true));
        Log.d("Stan po odczyt ", "" + ModelClass.get().isFirstStateDialog());

        if(ModelClass.get().isFirstStateDialog()){
            setMoneyAction();
        }

    }


    public void setMoneyAction(){
        FragmentManager manager = getFragmentManager();
        MoneyPickerFragment dialog = new MoneyPickerFragment();
        dialog.setTargetFragment(PagerAdapter.mFragments.get(1), REQUEST_MONEY); //definiowanie fragmentu docelowego
        dialog.show(manager,DIALOG_MONEY);
    }


    //dodawanie wpisow do bazy
    public void getContentValues() {
        values.put(Cols.MONEY, ModelClass.get().getExpensesMoney());
        values.put(Cols.DATE, ModelClass.get().getDate().toString("dd-MM-yyyy"));
        values.put(Cols.DESCRIPTION, ModelClass.get().getDescription());
        mDatabase.insert(ExpensesTable.NAME,null,values);

    }




    public ExpensesCursorWrapper queryExpenses(){
        Cursor cursor = mDatabase.query(
                    ExpensesTable.NAME,
                    null, //null bo chce wszystkie kolumny
                    null,
                    null,
                        null,
                        null,
                    null
            );
            return new ExpensesCursorWrapper(cursor);

    }


    public ExpensesCursorWrapper querySumExpenses(){
        Cursor cursor = mDatabase.rawQuery("SELECT " + Cols.DATE + "," + " SUM(" + Cols.MONEY + ")" +
                        "AS money_expenses FROM " + ExpensesTable.NAME +
                        " GROUP BY(" + Cols.DATE+ ")",
                null
        );
        return new ExpensesCursorWrapper(cursor);
    }


    public void updateBase(){
        mDatabase.update(
                ExpensesTable.NAME,
                values,
                Cols.MONEY + " = ?", //aktualizuje baze od tego wiersza <-
                null);
    }

    //tworzenie menu paska narzędzi
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.activity_pager_adapter,menu); //rozwijanie mojego menu
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.reset_data:
                mDatabase.execSQL("DELETE FROM "+ExpensesTable.NAME);
                ModelClass.get().setFirstStateDialog(true);
                setMoneyAction();
                PagerAdapter.mFragments.get(2).onResume();
                return true;

            case R.id.add_super_money:
                ModelClass.get().setAddSuperMoney(true);
                setMoneyAction();
                return true;

            case R.id.new_week:
                AppLab.get().nextWeek();
                PagerAdapter.mFragments.get(1).onResume();
                return true;

            case R.id.new_month:
                mDatabase.execSQL("DELETE FROM "+ExpensesTable.NAME);
                AppLab.get().nextMonth();
                AppLab.get().setMoney();
                PagerAdapter.mFragments.get(1).onResume();
                PagerAdapter.mFragments.get(2).onResume();
                return true;


        }
        return super.onOptionsItemSelected(item);
    }
}
