package com.example.student2student;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.preference.DialogPreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.student2student.query_task.ArraylistQueryInterface;
import com.example.student2student.query_task.MatchingData;
import com.example.student2student.query_task.delete_learn_to_student;
import com.example.student2student.query_task.delete_teach_to_student;
import com.example.student2student.query_task.insert_grade_to_student;
import com.example.student2student.query_task.insert_to_grade_table;
import com.example.student2student.query_task.matching;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class result extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ResultListAdapter listAdapter;
    private ArrayList<MatchingStudentItem> resultList;
    private matching match;
    private MatchingData matchingData;
    private String userID;
    private Bundle data;
    private final Context context = this;

    private View gradeDialogView;
    private TextView hiddenGrade;
    private EditText toStudentID;
    private insert_to_grade_table itgt;
    private insert_grade_to_student igts;
    private String userToGrade;
    private ProgressDialog progressDialog;
    private String grade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        data = getIntent().getExtras();
        if (data == null)
            return;
        final String first, last, email, coursesToTeach, coursesToLearn, lob;
        boolean toTeach;
        userID = data.getString("id");
        first = data.getString("first");
        last = data.getString("last");
        email = data.getString("email");
        toTeach = data.getBoolean("to teach");
        lob = data.getString("line of business");
        coursesToTeach = data.getString("coursesToTeach");
        coursesToLearn = data.getString("coursesToLearn");

        if(data.getString("grade") == null){
            findViewById(R.id.gradeBtn).setVisibility(View.INVISIBLE);
        }

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

        matchingData = new MatchingData(toLearnArray, toTeachArray, userID);
        match = new matching(this, getTaskId());
        match.setInterface(new matching.MatchingInterface() {
            @Override
            public void onSuccess(ArrayList<MatchingStudentItem> resList) {
                //before sorting
                //// TODO: 22/06/2016 this is where the sorting acure
                resultList.clear();
                boolean added = false;
                for (MatchingStudentItem item : resList) {
                    added = false;
                    if (resultList.isEmpty()) {
                        resultList.add(item);
                        continue;
                    }

                    for (int i = 0; i < resultList.size(); i++) {
                        if (Double.parseDouble(resultList.get(i).getGrade().split("\\#")[0])
                                >=
                                Double.parseDouble(item.getGrade().split("\\#")[0])) {

                            resultList.add(i, item);
                            added = true;
                            break;
                        }
                    }
                    if (!added) {
                        resultList.add(item);

                    }
                }

                if (resultList.isEmpty()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(result.this, "No matches found", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Collections.reverse(resultList);
                    listAdapter.notifyDataSetChanged();
                }

                if(progressDialog.isShowing()){
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onError() {
                if(progressDialog.isShowing()){
                    progressDialog.dismiss();
                };
            }
        });
        progressDialog = ProgressDialog.show(this, null,
                "מחפש לך מורה...", true);
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
    }

    public void gradeUser(View view) {

        final ImageButton star1, star2, star3, star4, star5;
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        gradeDialogView = layoutInflater.inflate(R.layout.grade_dialog, null);
        hiddenGrade = (TextView) gradeDialogView.findViewById(R.id.hiddenGrade);
        toStudentID = (EditText) gradeDialogView.findViewById(R.id.studentID);

        star1 = (ImageButton) gradeDialogView.findViewById(R.id.star1);
        star2 = (ImageButton) gradeDialogView.findViewById(R.id.star2);
        star3 = (ImageButton) gradeDialogView.findViewById(R.id.star3);
        star4 = (ImageButton) gradeDialogView.findViewById(R.id.star4);
        star5 = (ImageButton) gradeDialogView.findViewById(R.id.star5);

        star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star1.setImageResource(android.R.drawable.btn_star_big_on);
                star2.setImageResource(android.R.drawable.btn_star_big_off);
                star3.setImageResource(android.R.drawable.btn_star_big_off);
                star4.setImageResource(android.R.drawable.btn_star_big_off);
                star5.setImageResource(android.R.drawable.btn_star_big_off);
                hiddenGrade.setText("1");
            }
        });
        star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star1.setImageResource(android.R.drawable.btn_star_big_on);
                star2.setImageResource(android.R.drawable.btn_star_big_on);
                star3.setImageResource(android.R.drawable.btn_star_big_off);
                star4.setImageResource(android.R.drawable.btn_star_big_off);
                star5.setImageResource(android.R.drawable.btn_star_big_off);
                hiddenGrade.setText("2");
            }
        });
        star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star1.setImageResource(android.R.drawable.btn_star_big_on);
                star2.setImageResource(android.R.drawable.btn_star_big_on);
                star3.setImageResource(android.R.drawable.btn_star_big_on);
                star4.setImageResource(android.R.drawable.btn_star_big_off);
                star5.setImageResource(android.R.drawable.btn_star_big_off);
                hiddenGrade.setText("3");
            }
        });
        star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star1.setImageResource(android.R.drawable.btn_star_big_on);
                star2.setImageResource(android.R.drawable.btn_star_big_on);
                star3.setImageResource(android.R.drawable.btn_star_big_on);
                star4.setImageResource(android.R.drawable.btn_star_big_on);
                star5.setImageResource(android.R.drawable.btn_star_big_off);
                hiddenGrade.setText("4");
            }
        });
        star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star1.setImageResource(android.R.drawable.btn_star_big_on);
                star2.setImageResource(android.R.drawable.btn_star_big_on);
                star3.setImageResource(android.R.drawable.btn_star_big_on);
                star4.setImageResource(android.R.drawable.btn_star_big_on);
                star5.setImageResource(android.R.drawable.btn_star_big_on);
                hiddenGrade.setText("5");
            }
        });

        igts = new insert_grade_to_student(this, getTaskId());
        igts.setInterface(new insert_grade_to_student.GradeInterface() {
            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(result.this, "Grade successfully updated", Toast.LENGTH_LONG).show();

//                        finish();
//                        put(getIntent(), data);
//                        startActivity(getIntent());

                    }
                });

            }

            @Override
            public void onError() {

            }
        });

        itgt = new insert_to_grade_table(this, getTaskId());
        itgt.setInterface(new insert_to_grade_table.GradeInterface() {
            @Override
            public void onSuccess() {
                int g = Integer.parseInt(hiddenGrade.getText().toString());
                GradeAlgorithm gradeAlgorithm = new GradeAlgorithm(data.getString("grade")); //Algorithm Implementation
                String newGrade = gradeAlgorithm.calcGrade(g);
                igts.execute(newGrade, userToGrade);
            }

            @Override
            public void onError() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(result.this, "You can't rate the same person twice", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });


        new AlertDialog.Builder(this)
                .setTitle("דרג את המורה")
                .setView(gradeDialogView)
                .setPositiveButton("סיום", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (toStudentID.getText().toString().length() < 9) {
                            Toast.makeText(result.this, "ID is too short", Toast.LENGTH_LONG).show();
                        }
                        userToGrade = userID;
//                        userToGrade = toStudentID.getText().toString();
                        if (userToGrade.equals(toStudentID.getText().toString())) {
                            Toast.makeText(result.this, "You can't grade yourself!", Toast.LENGTH_LONG).show();
                        } else {
                            itgt.execute(toStudentID.getText().toString(), userID);
                        }

                    }
                })
                .setNeutralButton("בטל", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
        getIntent().putExtra("grade",data.getString("grade"));
    }

    private void put(Intent i, Bundle b) {
        i.putExtra("first", b.getString("first"));
        i.putExtra("last", b.getString("last"));
        i.putExtra("id", b.getString("id"));
        i.putExtra("email", b.getString("email"));
        i.putExtra("line of business", b.getString("line of business"));
        i.putExtra("to teach", b.getBoolean("to teach"));
        i.putExtra("phone", b.getString("phone"));
        i.putExtra("grade",b.getString("grade"));
    }

    private boolean pressTwice = false;
    @Override
    public void onBackPressed()
    {
        if(pressTwice)
        {
            Intent i = new Intent(context, MainActivity.class);
            startActivity(i);
            finish();

        }
        pressTwice = true;
        Toast.makeText(context, "press BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pressTwice = false;
            }
        }, 2000);

    }


}
