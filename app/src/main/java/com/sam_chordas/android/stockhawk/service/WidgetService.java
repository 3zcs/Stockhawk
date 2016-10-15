package com.sam_chordas.android.stockhawk.service;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.sam_chordas.android.stockhawk.ui.wedgit.WidgetDataProvider;

/**
 * Created by azcs on 29/08/16.
 */
public class WidgetService extends RemoteViewsService {
        @Override
        public RemoteViewsFactory onGetViewFactory(Intent intent) {

            WidgetDataProvider dataProvider = new WidgetDataProvider(
                    getApplicationContext(), intent);
            return dataProvider;
        }
}
