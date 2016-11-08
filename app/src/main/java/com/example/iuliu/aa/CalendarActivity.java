package com.example.iuliu.aa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

public class CalendarActivity extends AppCompatActivity {
    CalendarView calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        setTitle("Active Ageing Montly Schedule");


        calendar=(CalendarView) findViewById(R.id.calendarView);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth){
                Toast.makeText(getApplicationContext(), dayOfMonth+"/"+month+"/"+year,Toast.LENGTH_LONG).show();
            }
        }

        );
    }

    public void btn3onClick(View v){
        Intent intent = new Intent (CalendarActivity.this, RESTfulActivity.class);
        startActivity(intent);
    }

}
