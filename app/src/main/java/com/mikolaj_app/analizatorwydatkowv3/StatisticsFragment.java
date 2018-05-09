package com.mikolaj_app.analizatorwydatkowv3;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.mikolaj_app.analizatorwydatkowv3.database.ExpensesCursorWrapper;


import java.util.ArrayList;
import java.util.List;

//Fragment odpowiedzialny za wyświetlanie statystyk

public class StatisticsFragment extends RestoreDataManager{

    public LineChart mChart;
    public List<Entry> entries;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        entries = new ArrayList<>();
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_statistics_fragment, container, false);

        mChart = view.findViewById(R.id.chart);

        updateStatistics();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateStatistics();
    }

    public void updateStatistics(){
        ExpensesCursorWrapper cursor = querySumExpenses();
        Log.d("testCursor", "liczba wpisow "+ cursor.getCount());

        float money = 0;
        int day = 0;

        try {

            if (cursor.getCount() != 0) {
                entries.clear();
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {

                    money = cursor.getExpensesMoney();
                    day = cursor.getCurrentDay();

                    addEntry(day,money);
                    Log.d("testSQL", "" + money + " " + day);
                    cursor.moveToNext();

                }
                instalChart();
                configureAxis();
            }
        }finally {
            cursor.close();
        }
    }



    public void instalChart(){
        LineDataSet dataSet = new LineDataSet(entries,"Miesięczne wydatki");
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
       // dataSet.setColor(Color.BLUE);
        //dataSet.setValueTextColor(Color.CYAN);
        LineData lineData = new LineData(dataSet);
        mChart.setData(lineData);
        mChart.invalidate();
    }

  public void addEntry(int xDay, float yMoney){
        LineData data = mChart.getData();

        entries.add(new Entry(xDay,yMoney));
        //data.notifyDataChanged();
        mChart.notifyDataSetChanged();
  }


  public void configureAxis(){

      XAxis xAxis = mChart.getXAxis();
      xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
      xAxis.setTextSize(10f);
      xAxis.setTextColor(Color.BLACK);
      xAxis.setGranularity(1f);

      YAxis mYAxisLeft = mChart.getAxisLeft();

      mChart.getAxisRight().setEnabled(false);
  }





}


