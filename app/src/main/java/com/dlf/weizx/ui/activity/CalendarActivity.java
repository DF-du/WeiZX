package com.dlf.weizx.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.dlf.weizx.R;
import com.dlf.weizx.util.LogUtil;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity implements View.OnClickListener {

    private MaterialCalendarView mMcv;
    /**
     * 确定
     */
    private Button mBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        initView();
    }

    private void initView() {
        mMcv = (MaterialCalendarView) findViewById(R.id.mcv);
        mBtn = (Button) findViewById(R.id.btn);
        mBtn.setOnClickListener(this);

        //设置日历

        //月份是从0开始数,格里高利历,
        //日历对象,java提供的获取日期,进行日期操作的一个类
        Calendar calendar = Calendar.getInstance();

        mMcv.state().edit()
                .setFirstDayOfWeek(Calendar.MONDAY)//设置每周的第一天
                .setMinimumDate(CalendarDay.from(2020, 1, 3))//设置日历最早日期
                 //设置最新日期,应该动态获取
                .setMaximumDate(CalendarDay.from(calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)))
                .setCalendarDisplayMode(CalendarMode.MONTHS)//设置按月显示
                .commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn:
                sendDate();
                break;
        }
    }

    private void sendDate() {
        CalendarDay date = mMcv.getSelectedDate();
        LogUtil.print("calendar date"+date.toString());
        if (date != null){
            //回传到前面一个页面
            EventBus.getDefault().post(date);
        }
        finish();
    }
}
