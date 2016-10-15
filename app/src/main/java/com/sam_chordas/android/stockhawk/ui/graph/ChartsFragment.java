package com.sam_chordas.android.stockhawk.ui.graph;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.ui.MyStocksActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import static com.sam_chordas.android.stockhawk.ui.graph.LingGraphActivity.stock;


public class ChartsFragment extends Fragment {
    static LineCardOne cardOne = null;
    float [] val = new float[5];
    OkHttpClient client = new OkHttpClient();
    String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String jsonData = response.body().string();
                    Log.v("daaaa", jsonData);

                    JSONObject Jobject = new JSONObject(jsonData);
                    JSONObject query = Jobject.getJSONObject("query");
                    JSONObject result = query.getJSONObject("results");
                    JSONArray resultArray = result.getJSONArray("quote");
                    for (int i = 0; i < resultArray.length(); i++) {
                        JSONObject object = resultArray.getJSONObject(i);
                        String Adj_Close = object.getString("Adj_Close");
                        val[i] = Float.valueOf(Adj_Close);
                        Log.v("val" , Adj_Close);
                    }
                    workAround();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return null ;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.charts, container, false);
        (cardOne = new LineCardOne((CardView) layout.findViewById(R.id.card1), getContext())).init();
        if (checkNetwork(getContext()))
            Toast.makeText(getContext(), R.string.checkNetwork, Toast.LENGTH_SHORT).show();

        if (savedInstanceState == null) {
            //2012-09-11
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String endDate = df.format(c.getTime());
            Log.v("start time => ", endDate);

            c.add(Calendar.DAY_OF_YEAR, -7);
            String startDate = df.format(c.getTime());
            Log.v("end time => ", startDate);

            String url =
                    "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.historicaldata%20where%20symbol%20%3D%20%22" + stock + "%22%20and%20startDate%20%3D%20%22" + startDate + "%22%20and%20endDate%20%3D%20%22" + endDate + "%22&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";
            try {
                run(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            val = savedInstanceState.getFloatArray("data");
            workAround();
        }

        return layout;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putFloatArray("data" , val);
        super.onSaveInstanceState(outState);
    }

    public void workAround(){
        float [] local = new float[5];
        for (int i = 0 ; i< val.length ; i++)
            local[i] = val[i] % 10 ;
        cardOne.resetdata();
        cardOne.update(local);
    }

    public boolean checkNetwork(Context mContext){
        ConnectivityManager cm =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}