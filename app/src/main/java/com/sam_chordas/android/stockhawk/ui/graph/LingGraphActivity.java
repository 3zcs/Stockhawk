package com.sam_chordas.android.stockhawk.ui.graph;

import android.os.Bundle;

import com.sam_chordas.android.stockhawk.R;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import static com.sam_chordas.android.stockhawk.ui.MyStocksActivity.INTENT_TAG;

public class LingGraphActivity extends AppCompatActivity {
    private Fragment mCurrFragment;
    public static String stock ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_graph);
        String s = getIntent().getStringExtra(INTENT_TAG);
        Bundle b = new Bundle();
        b.putString("stock" , s);
        mCurrFragment = new ChartsFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.container, mCurrFragment).commit();
    }
}




