package com.example.iuliu.aa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Iuliu on 2016-09-23.
 */
public class DisplayScheduleActivity extends AppCompatActivity {

    SparseArray<Schedule> groups = new SparseArray<Schedule>();
    ExpandableListView listView;
    ScheduleExpandableListAdapter adapter;

    @Override

//tttttt
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        this.createData();
        listView = (ExpandableListView) findViewById(R.id.listView);
        adapter = new ScheduleExpandableListAdapter(this, groups);
        System.out.println(adapter.getGroupCount());
        System.out.println(listView.getVisibility());
        listView.setAdapter(adapter);


        getSupportActionBar().setTitle("Daily Schedule");

    }
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_succes, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        if (id == R.id.mybutton_calendar) {
            Intent intent = new Intent(DisplayScheduleActivity.this, CalendarActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.mybutton_phone) {
            this.call();

        }
        return super.onOptionsItemSelected(item);
    }
    public void call() {
        String url=url = "tel: " + "1234";
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse(url));

        startActivity(callIntent);
    }

    public void createData() {
        for (int j = 0; j < 6; j++) {
            ArrayList<HashMap<String, String>> group = new ArrayList<HashMap<String, String>>();
                HashMap<String, String> hm = new HashMap<String, String>();
                hm.put("TIME", "08:00-9:00");
                group.add(hm);
                hm.put("TIME", "9:00-10:00");
                group.add(hm);
                hm.put("TIME", "10:00-11:00");
                group.add(hm);
                hm.put("TIME", "11:00-12:00");
                group.add(hm);
                hm.put("TIME", "13:00-14:00");
                group.add(hm);
                hm.put("TIME", "14:00-15:00");
                group.add(hm);
                hm.put("TIME", "15:00-16:00");
                group.add(hm);
            Schedule grp = new Schedule("Time slot: " + j);
            for (int i = 0; i < 1; i++) {
                grp.children.add("Customer:" + i);
            }
            groups.append(j, grp);
        }
    }
}

