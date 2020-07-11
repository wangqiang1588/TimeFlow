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
import com.mobibrw.utils.TimeUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import static com.mobibrw.persist.api.IPersistApi.TIME_FLOW_LOAD_LIMIT_NONE;

/**
 * Created by longsky on 2017/7/15.
 */

public class TimeFlowMgrAdapter extends RecyclerView.Adapter<TimeFlowMgrAdapter.TFViewHolder> {

    private Context ctx;
    private LayoutInflater inflater;
    private ArrayList<TimeFlowCase> timeFlowCases = new ArrayList<>();
    private TimeFlowViewClickInterceptor clickInterceptor;

    public TimeFlowMgrAdapter(Context context, TimeFlowViewClickInterceptor interceptor) {
        this.ctx = context;
        this.clickInterceptor = interceptor;
        this.inflater = LayoutInflater.from(this.getContext());
        final ArrayList<TimeFlowCase> tfCases = PersistApiBu.api().loadCompleteTimeFlowCases(TIME_FLOW_LOAD_LIMIT_NONE);
        timeFlowCases.clear();
        timeFlowCases.addAll(tfCases);
    }

    private Context getContext() {
        return this.ctx;
    }

    private LayoutInflater getInflater() {
        return inflater;
    }

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
        return new TFViewHolder(ctx, view, clickInterceptor);
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

        private TextView tvCaption;
        private TextView tvDel;
        private TextView tvDate;
        private TimeFlowCase tfCase;
        private Context ctx;
        private TimeFlowViewClickInterceptor clickInterceptor;
        public TFViewHolder(Context c, View v, TimeFlowViewClickInterceptor interceptor) {
            super(v);
            ctx = c;
            clickInterceptor = interceptor;
            tvCaption = (TextView) v.findViewById(R.id.txtCaption);
            tvDel = (TextView) v.findViewById(R.id.tv_delete);
            tvDate = (TextView) v.findViewById(R.id.tv_date);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean fireClick = true;
                    if (null != clickInterceptor) {
                        fireClick = clickInterceptor.onTimeFlowCaseViewClick(v);
                    }
                    if (fireClick) {
                        //item 点击事件
                        final Intent intent = new Intent();
                        intent.setClass(ctx, TimeFlowEditCaseActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra(TimeFlowEditCaseActivity.TIME_FLOW_CASE_KEY, tfCase.getKey());
                        ctx.startActivity(intent);
                    }
                }
            });

            tvDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PersistApiBu.api().removeTimeFlowCase(tfCase.getKey());
                }
            });
        }

        public void setTfCase(TimeFlowCase c) {
            tfCase = c;
            tvCaption.setText(tfCase.getContent());
            setupModifyTime(tvDate, tfCase);
        }

        private void setupModifyTime(TextView dateTime, TimeFlowCase c) {
            final String modTime = c.getModified();
            try {
                final Date dt = TimeUtils.timeStampFmtToDate(modTime);
                final boolean today = TimeUtils.isToday(dt);
                String dtCaption = "";
                if (today) {
                    dtCaption = "" + TimeUtils.getHourOfDay(dt) + ":" + TimeUtils.getMinute(dt) + ":" + TimeUtils.getSecond(dt);
                } else {
                    final boolean thisYear = TimeUtils.isThisYear(dt);
                    if (thisYear) {
                        dtCaption = "" + TimeUtils.getMonth(dt) + "/" + TimeUtils.getDayOfMonth(dt) + " " + TimeUtils.getHourOfDay(dt) + ":" + TimeUtils.getMinute(dt) + ":" + TimeUtils.getSecond(dt);
                    } else {
                        dtCaption = "" + TimeUtils.getYear(dt) + "/" + TimeUtils.getMonth(dt) + "/" + TimeUtils.getDayOfMonth(dt) + " " + TimeUtils.getHourOfDay(dt) + ":" + TimeUtils.getMinute(dt) + ":" + TimeUtils.getSecond(dt);
                    }
                }
                dateTime.setText(dtCaption);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
