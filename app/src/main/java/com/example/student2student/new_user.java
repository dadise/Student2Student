package com.example.student2student;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.student2student.query_task.ArraylistQueryInterface;
import com.example.student2student.query_task.import_list;
import com.example.student2student.query_task.insert_student;
import com.example.student2student.query_task.is_exist;
import com.example.student2student.query_task.BooleanQueryInterface;

import java.util.ArrayList;
//import com.example.student2student.query_task.insert_student;

public class new_user extends AppCompatActivity {

    import_list importList;
    is_exist isExist;
    insert_student insertStudent;
    boolean studentExist = false;
    EditText firstNameInput;
    EditText lastNameInput;
    EditText IDInput;
    EditText phone;
    EditText emailInput;
    CheckBox isTeach;
    Spinner s;
    student student;
    private Context context;
    String teaching = "", teach = "nothing", learn = "nothing", grade = "";
    String first, last, id, email, lob;
    boolean toTeach;

    Intent toWhichToTeachIntent, toWhichToLearnIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        context = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firstNameInput = (EditText) findViewById(R.id.firstNameInput);
        lastNameInput = (EditText) findViewById(R.id.lastnameInput);
        IDInput = (EditText) findViewById(R.id.IDInput);
        emailInput = (EditText) findViewById(R.id.e_mailInput);
        s = (Spinner) findViewById(R.id.line_of_business);
        isTeach = (CheckBox) findViewById(R.id.teatcingCheckBox);
        phone = (EditText) findViewById(R.id.phone);

    }

    public void toWhichToTeach(View view) {
        if (firstNameInput.getText().toString().equals("") || lastNameInput.getText().toString().equals("") ||
                IDInput.getText().toString().equals("") || emailInput.getText().toString().equals("")) {
            Toast.makeText(this, "you left some empty fields", Toast.LENGTH_LONG).show();
            return;
        }

        if (isTeach.isChecked()) {
            teaching = "1";
        } else {
            teaching = "0";
        }
        student = new student(firstNameInput.getText().toString(), lastNameInput.getText().toString(), IDInput.getText().toString(), emailInput.getText().toString(), s.getSelectedItem().toString(), teaching, teach, learn, grade,phone.getText().toString());

        insertStudent = new insert_student(this, getApplicationContext(), getTaskId());
        insertStudent.setInterface(new ArraylistQueryInterface() {
            @Override
            public void onSuccess(ArrayList<CourseItem> response) {
                moveToNextActivity();
            }

            @Override
            public void onError(String errType) {
                switch (errType) {
                    case "Duplicate":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "User with that ID already exists", Toast.LENGTH_SHORT).show();
                            }
                        });

                        break;
                    default:
                        break;
                }
            }
        });
        insertStudent.execute(student);
    }

    private void moveToNextActivity() {
        if (isTeach.isChecked()) {
            toWhichToTeachIntent = new Intent(this, which_to_teach.class);
            put(toWhichToTeachIntent);
            startActivity(toWhichToTeachIntent);

        } else {
            toWhichToLearnIntent = new Intent(this, which_to_learn.class);
            put(toWhichToLearnIntent);
            startActivity(toWhichToLearnIntent);

        }

    }

    private void put(Intent i) {
        first = firstNameInput.getText().toString();
        last = lastNameInput.getText().toString();
        id = IDInput.getText().toString();
        email = emailInput.getText().toString();
        lob = s.getSelectedItem().toString();

        first = first.trim();
        last = last.trim();
        id = id.trim();
        email = email.trim();

        i.putExtra("first", first);
        i.putExtra("last", last);
        i.putExtra("id", id);
        i.putExtra("email", email);
        i.putExtra("line of business", lob);
        i.putExtra("to teach", toTeach);
        i.putExtra("phone",phone.getText().toString());
    }

    public void toMain(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

}
