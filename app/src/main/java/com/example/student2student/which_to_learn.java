package com.example.student2student;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.student2student.query_task.ArraylistQueryInterface;
import com.example.student2student.query_task.StringQueryInterface;
import com.example.student2student.query_task.courseToTeach;
import com.example.student2student.query_task.import_list;
import com.example.student2student.query_task.insert_student;
import com.example.student2student.query_task.insert_teach_to_student;

import java.util.ArrayList;

public class which_to_learn extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CourseListAdapter listAdapter;
    private ArrayList<CourseItem> coursesList;

    private Button btnOK;

    import_list importList;
    insert_teach_to_student itts;
    final Context context = this;
    private String sCoursesToLearn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_which_to_learn);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        coursesList = new ArrayList<>();
        listAdapter = new CourseListAdapter(this, coursesList);
        recyclerView.setAdapter(listAdapter);

        final Bundle data = getIntent().getExtras();
        if (data == null)
            return;

        final String first, last, ID, email, lob, coursesToTeach, phone;
        boolean toTeach;
        first = data.getString("first");
        last = data.getString("last");
        ID = data.getString("id");
        email = data.getString("email");
        lob = data.getString("line of business");
        toTeach = data.getBoolean("to teach");
        coursesToTeach = data.getString("coursesToTeach");
        phone = data.getString("phone");

        final TextView change = (TextView) findViewById(R.id.change);
        change.setText("שלום לך " + first + " " + last);

        importList = new import_list(getApplicationContext(), getTaskId());
        importList.setInterface(new ArraylistQueryInterface() {
            @Override
            public void onSuccess(ArrayList<CourseItem> response) {
                coursesList.addAll(response);
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String errType) {
            }
        });
        importList.execute(lob);


        btnOK = (Button) findViewById(R.id.ok_button);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<courseToTeach> coursesToLearn = new ArrayList<>();
                courseToTeach ctt = null;
                for (CourseItem item : coursesList) {
                    if (item.isChecked()) {
                        coursesToLearn.add(new courseToTeach(ID, item.getCourseName()));
                    }
                }

                if (coursesToLearn.size() > 3) {
                    Toast.makeText(which_to_learn.this, "Please choose maximum 3 courses!", Toast.LENGTH_LONG).show();
                    return;
                }
                if (coursesToLearn.size() == 0) {
                    Toast.makeText(which_to_learn.this, "Please choose at least one course!", Toast.LENGTH_LONG).show();
                    return;
                }

                sCoursesToLearn = which_to_teach.getCoursesToUpdate(coursesToLearn);
                itts = new insert_teach_to_student(insert_teach_to_student.IS_LEARN, ID, getApplicationContext(), getTaskId());
                itts.setInterface(new StringQueryInterface() {
                    @Override
                    public void onSuccess(String response) {
                        Log.e("another", response.toString());
                    }

                    @Override
                    public void onError() {

                    }
                });
                itts.execute(sCoursesToLearn);
                Intent intent = new Intent(context, result.class);
                Log.e("sss", data.toString() + "\t sCoursesToLearn=" + sCoursesToLearn);
                put(intent, data);
                startActivity(intent);
                finish();
            }
        });
    }

    private void put(Intent i, Bundle b) {
        i.putExtra("first", b.getString("first"));
        i.putExtra("last", b.getString("last"));
        i.putExtra("id", b.getString("id"));
        i.putExtra("email", b.getString("email"));
        i.putExtra("line of business", b.getString("line of business"));
        i.putExtra("to teach", b.getBoolean("to teach"));
        i.putExtra("phone", b.getString("phone"));

        if (b.getString("coursesToTeach") == null) {
            i.putExtra("coursesToTeach", "nothing");
        } else {
            i.putExtra("coursesToTeach", b.getString("coursesToTeach"));
        }
        Log.e("sss", "IM-IN-TOLEARN:" + b.getString("coursesToTeach"));

        i.putExtra("coursesToLearn", sCoursesToLearn);
    }
}