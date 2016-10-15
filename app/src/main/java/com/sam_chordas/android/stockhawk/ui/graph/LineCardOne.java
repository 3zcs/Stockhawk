package com.sam_chordas.android.stockhawk.ui.graph;

import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Looper;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.db.chart.Tools;
import com.db.chart.animation.Animation;
import com.db.chart.animation.easing.BounceEase;
import com.db.chart.model.ChartSet;
import com.db.chart.model.LineSet;
import com.db.chart.renderer.AxisRenderer;
import com.db.chart.view.LineChartView;
import com.google.android.gms.common.server.response.FieldMappingDictionary;
import com.sam_chordas.android.stockhawk.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by azcs on 10/10/16.
 */

public class LineCardOne extends CardController{

    private final LineChartView mChart;
    private final Context mContext;




    private  String[] mLabels = {"1", "2","3", "4","5"};

    private  float[] mValues = {1,1,1, 1,1};

    LineSet dataset ;

    private static Runnable mBaseAction;


    public LineCardOne(CardView card, Context context ) {
        super(card);
        mContext = context;
        mChart = (LineChartView) card.findViewById(R.id.chart1);
    }


    @Override
    public void show(Runnable action) {
        super.show(action);
        dataset = new LineSet(mLabels, mValues);
        dataset.setColor(Color.parseColor("#758cbb"))
                .setFill(Color.parseColor("#2d374c"))
                .setDotsColor(Color.parseColor("#758cbb"))
                .setThickness(4)
                .setDashed(new float[] {10f, 10f})
                .beginAt(0);
        mChart.addData(dataset);

        dataset = new LineSet(mLabels, mValues);
        dataset.setColor(Color.parseColor("#b3b5bb"))
                .setFill(Color.parseColor("#2d374c"))
                .setDotsColor(Color.parseColor("#ffc755"))
                .setThickness(4)
                .endAt(mValues.length);
        mChart.addData(dataset);

        // Chart
        mChart.setBorderSpacing(Tools.fromDpToPx(15))
                .setAxisBorderValues(0, 20)
                .setYLabels(AxisRenderer.LabelPosition.NONE)
                .setLabelsColor(Color.parseColor("#6a84c3"))
                .setXAxis(false)
                .setYAxis(false);

        mBaseAction = action;
        Runnable chartAction = new Runnable() {
            @Override
            public void run() {

                mBaseAction.run();
            }
        };

        Animation anim = new Animation().setEasing(new BounceEase()).setEndAction(chartAction);
        mChart.show(anim);
    }


    @Override
    public void update() {
        super.update();

        mChart.dismissAllTooltips();
        if (firstStage) {
            mChart.updateValues(0, mValues);
            mChart.updateValues(1, mValues);
        } else {
            mChart.updateValues(0, mValues);
            mChart.updateValues(1, mValues);
        }
        mChart.getChartAnimation().setEndAction(mBaseAction);
        mChart.notifyDataUpdate();
    }
    public void resetdata()
    {
        Looper.prepare();
        mChart.reset();
    }
    public void update(float [] list ){
//        mChart.reset();
//        for (int i = 0 ; i < mValues.length ; i++) {
//            mValues[i] = list[i] / 10;
//            Log.v("i =" , String.valueOf(i));
//        }
        mValues = list ;
        show(mBaseAction);


    }





}

