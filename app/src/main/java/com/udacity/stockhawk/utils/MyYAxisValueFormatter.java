package com.udacity.stockhawk.utils;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.SimpleDateFormat;

/**
 * Created by Diana.Raspopova on 4/15/2017.
 */

public class MyYAxisValueFormatter implements IAxisValueFormatter {

    SimpleDateFormat mFormat;

    public MyYAxisValueFormatter() {
        mFormat = new SimpleDateFormat("dd.MM.yy");
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return mFormat.format(value);
    }


}