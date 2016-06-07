package com.example.student2student;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.student2student.query_task.ArraylistQueryInterface;
import com.example.student2student.query_task.MatchingData;
import com.example.student2student.query_task.matching;

import java.util.ArrayList;

public class result extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ResultListAdapter listAdapter;
    private ArrayList<ItemResult> resultList;
    private matching match;
    private MatchingData matchingData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Bundle data = getIntent().getExtras();
        if (data == null)
            return;

        final String first, last, ID, email, coursesToTeach, coursesToLearn;
        boolean toTeach;
        ID = data.getString("id");
        first = data.getString("first");
        last = data.getString("last");
        email = data.getString("email");
        toTeach = data.getBoolean("to teach");
        coursesToTeach = data.getString("coursesToTeach");
        coursesToLearn = data.getString("coursesToLearn");

        Log.e("sss", "ctl from res:" + coursesToLearn);
        Log.e("sss", "ctt from res: " + coursesToTeach);

        String[] temp = coursesToTeach.split("\\#");
        ArrayList<String> toTeachArray = new ArrayList<>();
        for (String s : temp) {
            toTeachArray.add(s);
        }

        temp = coursesToLearn.split("\\#");
        ArrayList<String> toLearnArray = new ArrayList<>();
        for (String s : temp) {
            toLearnArray.add(s);
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        resultList = new ArrayList<>();
        listAdapter = new ResultListAdapter(this, resultList);
        recyclerView.setAdapter(listAdapter);

        matchingData = new MatchingData(toLearnArray, toTeachArray, ID);
//
//        match = new matching(this, getTaskId());
////TODO:SET INTERFACE
//        match.execute(matchingData);
    }

    public void toWhichToTeach(View view) {
        Intent intent = new Intent(this, which_to_teach.class);
        startActivity(intent);
    }

    public void toMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}
