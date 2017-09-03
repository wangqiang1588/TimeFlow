package com.mobibrw.timeflowmgr.biz.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mobibrw.persist.api.PersistApiBu;
import com.mobibrw.timeflowmgr.biz.R;
import com.mobibrw.utils.TimeUtils;

public class TimeFlowAddItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_flow_add_item);
        final Button btnCommit = (Button)findViewById(R.id.btnCommit);
        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAndSaveTimeFlowItems();
            }
        });
    }

    private void setAndSaveTimeFlowItems(){
        final EditText editText = (EditText)findViewById(R.id.editContent);
        String strContent = editText.getText().toString();
        if((null == strContent) || (strContent.trim().length() <= 0 )){
            Toast.makeText(this.getBaseContext(),this.getString(R.string.content_is_empty),Toast.LENGTH_SHORT).show();
        }else {
            strContent = strContent.trim();
            final DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
            final int year = datePicker.getYear();
            final int month = datePicker.getMonth();
            final int day = datePicker.getDayOfMonth();
            final TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);
            final int hour = timePicker.getCurrentHour();
            final int minute = timePicker.getCurrentMinute();
            final String gmt =  TimeUtils.getGMT(year,month,day,hour,minute);
            PersistApiBu.api().persistTimeItem(strContent,gmt);
            TimeFlowAddItemActivity.this.finish();
        }
    }
}
