package com.mikolaj_app.analizatorwydatkowv3.database;

public class ExpensesDbSchema {
    //nazwa tabeli:
    public static final class ExpensesTable {
        public static final String NAME = "expenses";

        //kolumny tabeli:
        public static final class Cols{
            public static final String MONEY = "money_expenses";
            public static final String DATE = "date";
            public static final String DESCRIPTION = "description";
        }
    }

}
