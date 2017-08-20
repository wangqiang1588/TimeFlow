package com.mobibrw.timeflowmgr.biz.Adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.mobibrw.timeflowmgr.biz.R;

/**
 * Created by longsky on 2017/7/15.
 */

public class TimeFlowMgrAdapter implements ListAdapter {

    private Context ctx;
    private LayoutInflater inflater;

    public TimeFlowMgrAdapter(Context context) {
        this.ctx = context;
        this.inflater = LayoutInflater.from(this.getContext());
    }

    private Context getContext() {
        return this.ctx;
    }

    private LayoutInflater getInflater() {
        return inflater;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int i) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (null == view) {
            view = getInflater().inflate(R.layout.time_item, null); //see above, you can use the passed resource id.
        }else{
            holder = (ViewHolder)view.getTag();
        }
        if(null == holder){
            holder = new ViewHolder();
            holder.txtCaption = (TextView) view.findViewById(R.id.txtCaption);
            view.setTag(holder);
        }
        holder.txtCaption.setText("hello");
        return view;
    }

    @Override
    public int getItemViewType(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    private static class ViewHolder {
        public TextView txtCaption;
    }
}
