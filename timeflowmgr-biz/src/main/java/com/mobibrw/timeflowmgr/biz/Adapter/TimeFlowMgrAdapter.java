package com.mobibrw.timeflowmgr.biz.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mobibrw.persist.api.PersistApiBu;
import com.mobibrw.persist.api.TimeInfo;
import com.mobibrw.timeflowmgr.biz.R;

/**
 * Created by longsky on 2017/7/15.
 */

public class TimeFlowMgrAdapter extends BaseAdapter {

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
    public int getCount() {
        long count = PersistApiBu.api().getTimeItemsCount();
        return Long.valueOf(count).intValue();
    }

    @Override
    public Object getItem(int i) {
        return PersistApiBu.api().getTimeInfoByOffset(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
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
            TimeInfo info = PersistApiBu.api().getTimeInfoByOffset(i);
            holder.info = info;
            view.setTag(holder);
        }

        holder.txtCaption.setText(holder.info.getContent());
        return view;
    }

    private static class ViewHolder {
        public TextView txtCaption;
        public TimeInfo info;
    }
}
