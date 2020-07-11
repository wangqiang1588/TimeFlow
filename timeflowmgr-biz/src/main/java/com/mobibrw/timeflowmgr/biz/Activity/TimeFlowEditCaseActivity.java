package com.mobibrw.timeflowmgr.biz.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.mobibrw.persist.api.PersistApiBu;
import com.mobibrw.persist.api.TimeFlowCase;
import com.mobibrw.timeflowmgr.biz.R;
import com.mobibrw.utils.HashUtils;
import com.mobibrw.utils.TimeUtils;
import com.mobibrw.utils.ToastUtils;

public class TimeFlowEditCaseActivity extends AppCompatActivity {

    public static String TIME_FLOW_CASE_KEY = "TimeFlowCaseBizKey";

    private static String TAG = "TimeFlowEditCaseActivity";

    private static int MAX_TRY_COUNT = 3;

    private TimeFlowCase timeFlowCase;

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_case);
        final Button btnCommit = (Button) findViewById(R.id.btnCommit);
        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAndSaveTimeFlowItems();
            }
        });
        editText = (EditText) findViewById(R.id.editContent);

        final Intent intent = getIntent();
        final String key = intent.getStringExtra(TimeFlowEditCaseActivity.TIME_FLOW_CASE_KEY);
        if (!TextUtils.isEmpty(key)) {
            timeFlowCase = PersistApiBu.api().loadTimeFlowCase(key);
            editText.setText(timeFlowCase.getContent());
        }
    }

    private void setAndSaveTimeFlowItems() {
        final String strContent = editText.getText().toString();
        if (TextUtils.isEmpty(strContent)) {
            ToastUtils.shortToast(this.getBaseContext(), R.string.warning_content_empty);
        } else {
            int tryCount = 0;
            do {
                tryCount++;
                if (tryCount > MAX_TRY_COUNT) {
                    ToastUtils.shortToast(this.getBaseContext(), R.string.warning_persist_failed);
                    break;
                }
                final String editTime = TimeUtils.generateTimeStamp();
                String strKey = "";
                if (null == timeFlowCase) {
                    strKey = HashUtils.generateSha1(editTime);
                    if (TextUtils.isEmpty(strKey)) {
                        strKey = editTime;
                    }
                    final boolean exists = PersistApiBu.api().isTimeFlowCaseKeyExists(strKey);
                    if (exists) {
                        continue;
                    }
                } else {
                    strKey = timeFlowCase.getKey();
                }

                final DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
                final int year = datePicker.getYear();
                final int month = datePicker.getMonth();
                final int day = datePicker.getDayOfMonth();
                final TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);
                final int hour = timePicker.getCurrentHour();
                final int minute = timePicker.getCurrentMinute();
                final String gmt = TimeUtils.getGMT(year, month, day, hour, minute);
                final boolean success = PersistApiBu.api().persistTimeFlowCase(strKey, strContent, editTime, gmt);
                if (success) {
                    TimeFlowEditCaseActivity.this.finish();
                } else {
                    ToastUtils.shortToast(this.getBaseContext(), R.string.warning_persist_failed);
                }
            } while (false);
        }
    }
}
