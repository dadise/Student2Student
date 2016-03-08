package com.example.student2student;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class result extends AppCompatActivity {

    ArrayAdapter<String> adapter;
    ListView listOfCourse;
    ArrayList<String> myItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        filTheList();
    }

    private void filTheList() {
        String[] myItems = {"אבירם שטרית        aviramshitrit@gmail.com" , "אליהו מיכאל          elimichael@gmail.com"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.teachitems, myItems);

        ListView listOfCourse = (ListView) findViewById(R.id.listOfCourses3);
        listOfCourse.setAdapter(adapter);


    }

    public void toWhichToTeach(View view)
    {
        Intent intent = new Intent(this,which_to_teach.class);
        startActivity(intent);
    }

    public void toMain(View view)
    {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }


}
