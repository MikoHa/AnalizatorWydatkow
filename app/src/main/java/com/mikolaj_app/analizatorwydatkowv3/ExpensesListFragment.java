package com.mikolaj_app.analizatorwydatkowv3;


import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mikolaj_app.analizatorwydatkowv3.database.ExpensesCursorWrapper;

import java.util.ArrayList;
import java.util.List;

//FRAGMENT KTÓRY BĘDZIE ODPOWIEDZIALNY ZA WYSWIETLANIA KWIESTII ORGANIZYJNYCH
//NOWY TYDZIEN, WALUTA, WPROWADZANIE PIENIEDZY ITP...

public class ExpensesListFragment extends RestoreDataManager{
    private RecyclerView mExpensesRecycleView;
    private ExpensesAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_expenseslist_fragment, container, false);

        mExpensesRecycleView = view.
                findViewById(R.id.statistics_recycler_view);
        mExpensesRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateList();

        return view;
    }



    @Override
    public void onResume() {
        super.onResume();
        updateList();
    }

    private class ExpensesHolder extends RecyclerView.ViewHolder{
        private TextView mDescriptionMoneyView;
        private TextView mDateMoneyView;
        private TextView mMoneyView;

        public ExpensesHolder(LayoutInflater inflater, ViewGroup parent, int viewType){
            super(inflater.inflate(R.layout.list_item_money, parent, false));
            mDescriptionMoneyView = itemView.findViewById(R.id.money_description);
            mDateMoneyView = itemView.findViewById(R.id.money_date);
            mMoneyView = itemView.findViewById(R.id.money_size);
        }

        public void bind(ExpensesList expensesList){
            mDateMoneyView.setText(expensesList.getDate());
            mDescriptionMoneyView.setText(expensesList.getDescription());
            mMoneyView.setText(expensesList.getMoney());
        }

    }//koniec holdera

    private class ExpensesAdapter extends RecyclerView.Adapter<ExpensesHolder>{
        private List<ExpensesList> mExpensesList;

        public ExpensesAdapter(List<ExpensesList> expensesList){
            mExpensesList = expensesList;
        }

        @NonNull
        @Override
        public ExpensesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ExpensesHolder(layoutInflater,parent,viewType);
        }


        @Override
        public void onBindViewHolder(@NonNull ExpensesHolder holder, int position) {
            ExpensesList expensesList = mExpensesList.get(position);
            holder.bind(expensesList);
        }


        @Override
        public int getItemCount() {
            return mExpensesList.size();
        }
    }

    public void updateList(){
        ExpensesCursorWrapper cursor = queryExpenses();
        List<ExpensesList> expensesLists = new ArrayList<>();
        ExpensesList expenses;


        try {

            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                expensesLists.clear();
                while (!cursor.isAfterLast()) {
                    expenses = new ExpensesList();

                    String money = String.valueOf(cursor.getExpensesMoney());
                    String date = cursor.getAllDayFormat();
                    String description = cursor.getDescription();

                    expenses.setMoney(money + " zł");
                    expenses.setDescription(description);
                    expenses.setDate(date);

                    expensesLists.add(expenses);

                    cursor.moveToNext();
                }

                mAdapter = new ExpensesAdapter(expensesLists);
                mExpensesRecycleView.setAdapter(mAdapter);
            }
        }finally {
            cursor.close();
        }

    }



}



/*
      mNextWeek = (Button) view.findViewById(R.id.new_week_button);
        mNextWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainFragment mMainFragment = (MainFragment) PagerAdapter.mFragments.get(1);
                AppLab.get().nextWeek();
                saveData();
                mMainFragment.onResumeExtra();
            }
        });
 */