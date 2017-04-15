package com.udacity.stockhawk.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;
import com.udacity.stockhawk.R;
import com.udacity.stockhawk.utils.MyXAxisValueFormatter;
import com.udacity.stockhawk.utils.MyYAxisValueFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Diana.Raspopova on 4/15/2017.
 */

public class DetailsActivity extends AppCompatActivity implements OnChartValueSelectedListener {

    @BindView(R.id.chart)
    LineChart chart;
    String stockSymbol;
    String history;

    Map<Long, Double> historyMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_details);
        ButterKnife.bind(this);

        stockSymbol = getIntent().getStringExtra("symbol");
        history = getIntent().getStringExtra("history");

        getSupportActionBar().setTitle(stockSymbol);
        parseHistory();
        setupChart();
    }

    private void parseHistory() {
        String[] pairs = history.split("\n");
        historyMap = new HashMap<>();
        for (String pair : pairs) {
            String[] dateVal = pair.split(", ");
            historyMap.put(Long.valueOf(dateVal[0]), Double.valueOf(dateVal[1]));
        }
    }

    private void setupChart() {
        chart.setOnChartValueSelectedListener(this);
        chart.getDescription().setEnabled(false);


        //chart.setMaxVisibleValueCount(60);
        chart.setPinchZoom(false);
        chart.setDrawGridBackground(false);

        XAxis xl = chart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(true);
        xl.setGranularity(10f);
        xl.setValueFormatter(new MyYAxisValueFormatter());

        YAxis yl = chart.getAxisLeft();
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(false);
        yl.setAxisMinimum(0f);
        yl.setValueFormatter(new MyXAxisValueFormatter());

        YAxis yr = chart.getAxisRight();
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(true);
        yr.setAxisMinimum(0f);
        yr.setValueFormatter(new MyXAxisValueFormatter());

        setData(historyMap);
        chart.animateY(700);


        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(8f);
        l.setXEntrySpace(4f);
    }



    @Override
    public void onValueSelected(Entry e, Highlight h) {
        if (e == null)
            return;


        MPPointF position = chart.getPosition(e, chart.getData().getDataSetByIndex(h.getDataSetIndex())
                .getAxisDependency());

        MPPointF.recycleInstance(position);
    }

    @Override
    public void onNothingSelected() {

    }

    private void setData(Map<Long, Double> values) {

        ArrayList<Entry> entries = new ArrayList<Entry>();

        Set<Long> keys = values.keySet();
        Set<Long> keysSorted = new TreeSet(keys);
        Iterator<Long> iterator = keysSorted.iterator();

        while (iterator.hasNext() ) {
            Long date = iterator.next();
            Double val = values.get(date);
            entries.add(new Entry( date.floatValue(),val.floatValue()));
        }

        LineDataSet dataSet = new LineDataSet(entries, stockSymbol); // add entries to dataset

        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate();
    }


}
