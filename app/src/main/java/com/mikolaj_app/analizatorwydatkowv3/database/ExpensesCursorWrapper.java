package com.mikolaj_app.analizatorwydatkowv3.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.mikolaj_app.analizatorwydatkowv3.database.ExpensesDbSchema.ExpensesTable;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


public class ExpensesCursorWrapper extends CursorWrapper {
    private DateTimeFormatter formatter =
            DateTimeFormat.forPattern("dd-MM-yyyy");


    public ExpensesCursorWrapper(Cursor cursor) {
        super(cursor);
    }


    public float getExpensesMoney(){
        return getFloat(getColumnIndex(ExpensesTable.Cols.MONEY));
    }


    public String getDescription(){
        return getString(getColumnIndex(ExpensesTable.Cols.DESCRIPTION));
    }

    public int getCurrentDay(){
        String date = getString(getColumnIndex(ExpensesTable.Cols.DATE));
        DateTime dateT = new DateTime(formatter.parseDateTime(date));
        return dateT.getDayOfMonth();
    }

    public String getAllDayFormat(){
        return getString(getColumnIndex(ExpensesTable.Cols.DATE));
    }





}
