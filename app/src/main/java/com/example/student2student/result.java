package com.example.student2student;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.student2student.query_task.ArraylistQueryInterface;
import com.example.student2student.query_task.MatchingData;
import com.example.student2student.query_task.delete_learn_to_student;
import com.example.student2student.query_task.delete_teach_to_student;
import com.example.student2student.query_task.matching;

import java.util.ArrayList;

public class result extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ResultListAdapter listAdapter;
    private ArrayList<MatchingStudentItem> resultList;
    private matching match;
    private MatchingData matchingData;
    private String userID;
    private Bundle data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        data = getIntent().getExtras();
        if (data == null)
            return;
        final String first, last, ID, email, coursesToTeach, coursesToLearn, lob;
        boolean toTeach;
        ID = data.getString("id");
        userID = ID;
        first = data.getString("first");
        last = data.getString("last");
        email = data.getString("email");
        toTeach = data.getBoolean("to teach");
        lob = data.getString("line of business");
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
        listAdapter = new ResultListAdapter(this, resultList, first, email);
        recyclerView.setAdapter(listAdapter);

        matchingData = new MatchingData(toLearnArray, toTeachArray, ID);
        match = new matching(this, getTaskId());
        match.setInterface(new matching.MatchingInterface() {
            @Override
            public void onSuccess(ArrayList<MatchingStudentItem> resList) {
                resultList.addAll(resList);

                if (resultList.isEmpty()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(result.this, "No matches found", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    listAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError() {

            }
        });
        match.execute(matchingData);
    }

    public void toWhichToTeach(View view) {
        final Activity activity = this;
        new AlertDialog.Builder(this)
                .setTitle("האם תרצה ללמד?")
                .setPositiveButton("כן", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(activity, which_to_teach.class);
                        put(intent, data);
                        startActivity(intent);
                        activity.finish();
                    }
                })
                .setNegativeButton("לא", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        delete_teach_to_student deleteTeachToStudent = new delete_teach_to_student(activity, getTaskId());
                        deleteTeachToStudent.setInterface(new delete_teach_to_student.DeleteTeachInterface() {
                            @Override
                            public void onSuccess() {
                                Intent intent = new Intent(activity, which_to_learn.class);
                                put(intent, data);
                                startActivity(intent);
                                activity.finish();
                            }
                        });
                        if (userID != null) {
                            deleteTeachToStudent.execute(userID);
                        }

                    }
                })
                .setNeutralButton("בטל", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
//TODO:
    }

    public void toMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void put(Intent i, Bundle b) {
        i.putExtra("first", b.getString("first"));
        i.putExtra("last", b.getString("last"));
        i.putExtra("id", b.getString("id"));
        i.putExtra("email", b.getString("email"));
        i.putExtra("line of business", b.getString("line of business"));
        i.putExtra("to teach", b.getBoolean("to teach"));
        i.putExtra("phone", b.getString("phone"));
    }


}
