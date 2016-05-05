package com.example.student2student;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.student2student.query_task.ArraylistQueryInterface;
import com.example.student2student.query_task.courseToTeach;
import com.example.student2student.query_task.import_list;
import com.example.student2student.query_task.insert_student;
import com.example.student2student.query_task.insert_teach_to_student;

import java.util.ArrayList;

public class which_to_teach extends AppCompatActivity {

    //    insert_student is;
    import_list importList;
    insert_teach_to_student itts;
    ArrayAdapter<String> adapter;
    ListView listOfCourse;
    ArrayList<String> myItems;
    courseToTeach ctt;
    static ArrayList<String> res;
    public static android.os.Handler h = new android.os.Handler();
    public static Runnable r;
    final Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_which_to_teach);

//        Intent starterActivity = getIntent();
        Bundle data = getIntent().getExtras();
        if (data == null)
            return;

        final String first, last, ID, email, lob;
        boolean toTeach;
        first = data.getString("first");
        last = data.getString("last");
        ID = data.getString("id");
        email = data.getString("email");
        lob = data.getString("line of business");
        toTeach = data.getBoolean("to teach");


        importList = new import_list(this, getApplicationContext(), getTaskId());
        importList.setInterface(new ArraylistQueryInterface() {
            @Override
            public void onSuccess(ArrayList<String> response) {

                Log.d("123456789", response.get(0));
                res = response;

                filTheList(lob, res);
                h.post(r);


            }

            @Override
            public void onError() {
                Log.d("123456789", "1234567890987654321999999999999999999999RRR");
            }
        });

        importList.execute(lob);

        // list fill after the if statment
//        Log.i("list",listOfCourse);
//        while(listOfCourse == null)
//        {
//            ////////////////// probebley need progress bar///////
//
//            Log.i("still","waiting");
//        }



        r = new Runnable() {
            @Override
            public void run() {
                Log.d("12345", "before");


        if(listOfCourse != null)
        {
            Log.i("list not empty","sadasd");


            Log.i("now im here", "here");
            listOfCourse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.e("now im here", "here");

                    String item = myItems.get(position);
                    Intent intent = new Intent(context, which_to_learn.class);
                    intent.putExtra("item", item);
                    ctt = new courseToTeach(ID, item);
                    Log.e("asdasdasd", item + "  " + ID);
//                    itts = new insert_teach_to_student(this,getApplicationContext(),getTaskId());
                    //////////////////////////////// problem to update the student info //////////////////////////////

//                    itts.execute(ctt);
//                    finish();
                    startActivity(intent);

                }
            });
        }
        else
        {
            Log.i("ssss","sssssssssss");
        }
                Log.d("12345", "after");
            }
        };

        final TextView change = (TextView) findViewById(R.id.change);
        change.setText("שלום לך " + first + " " + last);
        if (first == "" || last == "" || ID == "" || email == "") {
            Intent intent = new Intent(this, new_user.class);
            startActivity(intent);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    private void filTheList(String lob, ArrayList<String> response) {
        myItems = new ArrayList<String>();

        Log.e("test", response.toString());
        for (int i = 0; i < response.size(); i++)
        {
            Log.e("course", response.get(i));
            myItems.add(response.get(i));
        }


        adapter = new ArrayAdapter<String>(this, R.layout.teachitems, myItems);

//        h.post(r);
        listOfCourse = (ListView) findViewById(R.id.listOfCourses);
        listOfCourse.setAdapter(adapter);


    }



}