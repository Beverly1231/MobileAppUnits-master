package com.example.iuliu.aa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class RESTfulActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restful);
    }

    public void BacktoCar(View v){
        Intent intent = new Intent (RESTfulActivity.this, CalendarActivity.class);
        startActivity(intent);
    }

    public void Send(View v){
        SendToServer cnt = new SendToServer();
    }

    public void Get(View v){
        RetrieveFromServer cnt = new RetrieveFromServer();
    }
}
