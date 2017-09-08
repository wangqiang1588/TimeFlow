package com.mobibrw.timeflowmgr.biz.View;

import android.content.Context;
import android.util.AttributeSet;

import com.mobibrw.lib.ui.SwipeListView.SwipeListView;

/**
 * Created by longsky on 2017/9/8.
 */

public class SwipeTimeFlowListView extends SwipeListView {

    public SwipeTimeFlowListView(Context context, int swipeBackView, int swipeFrontView) {
        super(context, swipeBackView, swipeFrontView);
    }

    public SwipeTimeFlowListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SwipeTimeFlowListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
