package com.mobibrw.timeflowmgr.biz.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class TimeFlowMgrAdapter extends RecyclerView.Adapter<TimeFlowMgrAdapter.TFViewHolder> {

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

    public void fireTimeFlowDataSetChanged() {
        final ArrayList<TimeFlowCase> tfCases = PersistApiBu.api().loadCompleteTimeFlowCases(TIME_FLOW_LOAD_LIMIT_NONE);
        timeFlowCases.clear();
        timeFlowCases.addAll(tfCases);
        super.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TFViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = getInflater().inflate(R.layout.case_item, parent, false);
        return new TFViewHolder(ctx, view);
    }

    @Override
    public void onBindViewHolder(@NonNull TFViewHolder holder, int i) {
        final TimeFlowCase tfCase = timeFlowCases.get(i);
        holder.setTfCase(tfCase);
    }

    @Override
    public int getItemCount() {
        return timeFlowCases.size();
    }

    //创建ViewHolder
    public static class TFViewHolder extends RecyclerView.ViewHolder {
        private TextView txtCaption;
        private TimeFlowCase tfCase;
        private Context ctx;

        public TFViewHolder(Context c, View v) {
            super(v);
            ctx = c;
            txtCaption = (TextView) v.findViewById(R.id.txtCaption);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //item 点击事件
                    final Intent intent = new Intent();
                    intent.setClass(ctx, TimeFlowEditCaseActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(TimeFlowEditCaseActivity.TIME_FLOW_CASE_KEY, tfCase.getKey());
                    ctx.startActivity(intent);
                }
            });
        }

        public void setTfCase(TimeFlowCase c) {
            tfCase = c;
            txtCaption.setText(tfCase.getContent());
        }
    }
}
