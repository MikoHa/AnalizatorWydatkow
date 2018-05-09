package com.mikolaj_app.analizatorwydatkowv3.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.mikolaj_app.analizatorwydatkowv3.database.ExpensesDbSchema.ExpensesTable;

public class ExpensesBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "expensesBase.db";

    public ExpensesBaseHelper(Context context){
        super(context,DATABASE_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "
                + ExpensesTable.NAME
                + "("
                + " _id integer primary key autoincrement, "
                + ExpensesTable.Cols.MONEY + ", "
                + ExpensesTable.Cols.DATE + ", "
                + ExpensesTable.Cols.DESCRIPTION
                + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        //usun starsza tabele jesli istnieje
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ExpensesTable.NAME);
        //utworz na nowo
        onCreate(sqLiteDatabase);
    }

}
