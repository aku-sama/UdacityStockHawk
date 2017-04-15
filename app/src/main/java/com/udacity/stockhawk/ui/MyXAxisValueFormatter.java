package com.udacity.stockhawk.ui;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Diana.Raspopova on 4/15/2017.
 */

public class MyXAxisValueFormatter implements IAxisValueFormatter {

    private DecimalFormat mFormat;

    public MyXAxisValueFormatter() {
        mFormat =  new DecimalFormat("0.00");
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return mFormat.format(value)+ " $";
    }


}