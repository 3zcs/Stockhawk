package com.sam_chordas.android.stockhawk.ui.wedgit;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;

import android.os.Looper;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by azcs on 29/08/16.
 */
public class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {

    List mCollections = new ArrayList();
    List mNumber = new ArrayList();
    Context mContext = null;
    Cursor mCursor = null ;
    static Uri CONTENT_URI = Uri.parse("content://" + QuoteProvider.BASE_CONTENT_URI + "/");
    String [] mProjection = new String[]{ QuoteColumns._ID, QuoteColumns.SYMBOL, QuoteColumns.BIDPRICE,
            QuoteColumns.PERCENT_CHANGE, QuoteColumns.CHANGE, QuoteColumns.ISUP};
    String mSelectionClause = QuoteColumns.ISCURRENT + " = ?" ;
    String [] mSelectionArgs = new String[]{"1"} ;

    public WidgetDataProvider(Context context , Intent intent) {
        Log.v("msggg" , "here");
        mContext = context;
        mCursor = mContext.getContentResolver().query(
                QuoteProvider.Quotes.CONTENT_URI,  // The content URI of the words table
                mProjection,                       // The columns to return for each row
                mSelectionClause,                  // Either null, or the word the user entered
                mSelectionArgs,                    // Either empty, or the string the user entered
                null);
        int i = 0 ;
        while (mCursor.moveToNext()){
            mCollections.add(mCursor.getString(1));
            mNumber.add(mCursor.getString(2));
        }
    }

    @Override
    public int getCount() {
        return mCollections.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews mView = new RemoteViews(mContext.getPackageName(),
                R.layout.list_item_quote);

        mView.setTextViewText(R.id.stock_symbol, String.valueOf(mCollections.get(position)));
        mView.setTextViewText(R.id.bid_price, String.valueOf(mNumber.get(position)));

        mView.setTextColor(android.R.id.text1, Color.BLACK);
        return mView;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onCreate() {
       initData();
    }

    @Override
    public void onDataSetChanged() {
        initData();
    }

    private void initData() {

    }

    @Override
    public void onDestroy() {

    }
}