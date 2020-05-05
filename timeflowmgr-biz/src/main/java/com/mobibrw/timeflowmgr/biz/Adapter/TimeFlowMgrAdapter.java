package com.mobibrw.timeflowmgr.biz.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mobibrw.persist.api.PersistApiBu;
import com.mobibrw.persist.api.TimeFlowCase;
import com.mobibrw.timeflowmgr.biz.Activity.TimeFlowEditCaseActivity;
import com.mobibrw.timeflowmgr.biz.R;

import java.util.ArrayList;

import static com.mobibrw.persist.api.IPersistApi.TIME_FLOW_LOAD_LIMIT_NONE;

/**
 * Created by longsky on 2017/7/15.
 */

public class TimeFlowMgrAdapter extends BaseAdapter {

    private Context ctx;
    private LayoutInflater inflater;
    private ArrayList<TimeFlowCase> timeFlowCases = new ArrayList<>();

    public TimeFlowMgrAdapter(Context context) {
        this.ctx = context;
        this.inflater = LayoutInflater.from(this.getContext());
        final ArrayList<TimeFlowCase> tfCases = PersistApiBu.api().loadCompleteTimeFlowCases(TIME_FLOW_LOAD_LIMIT_NONE);
        timeFlowCases.clear();
        timeFlowCases.addAll(tfCases);
    }

    private Context getContext() { return this.ctx; }

    private LayoutInflater getInflater() { return inflater; }

    @Override
    public void notifyDataSetChanged() {
        final ArrayList<TimeFlowCase> tfCases = PersistApiBu.api().loadCompleteTimeFlowCases(TIME_FLOW_LOAD_LIMIT_NONE);
        timeFlowCases.clear();
        timeFlowCases.addAll(tfCases);
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        final int count = timeFlowCases.size();
        return count;
    }

    @Override
    public Object getItem(int i) {
        return timeFlowCases.get(i);
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
            final TimeFlowCase tfCase = timeFlowCases.get(i);
            holder.tfCase = tfCase;
            view.setTag(holder);
        } else {
            final TimeFlowCase tfCase = timeFlowCases.get(i);
            holder.tfCase = tfCase;
        }

        holder.txtCaption.setText(holder.tfCase.getContent());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent();
                intent.setClass(ctx, TimeFlowEditCaseActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                final ViewHolder holder = (ViewHolder)v.getTag();
                intent.putExtra(TimeFlowEditCaseActivity.TIME_FLOW_CASE_KEY, holder.tfCase.getKey());
                ctx.startActivity(intent);
            }
        });

        return view;
    }

    private static class ViewHolder {
        public TextView txtCaption;
        public TimeFlowCase tfCase;
    }
}
