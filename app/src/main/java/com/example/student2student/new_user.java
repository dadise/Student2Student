package com.example.student2student;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class new_user extends AppCompatActivity {
    EditText firstNameInput;
    EditText lastNameInput;
    EditText IDInput;
    EditText emailInput;
    CheckBox teach;
    Spinner s;

    String first, last, id, email, lob;
    boolean toTeach;

    Intent toWhichToTeachIntent, toWhichToLearnIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firstNameInput = (EditText) findViewById(R.id.firstNameInput);
        lastNameInput = (EditText) findViewById(R.id.lastnameInput);
        IDInput = (EditText) findViewById(R.id.IDInput);
        emailInput = (EditText) findViewById(R.id.e_mailInput);
        s = (Spinner) findViewById(R.id.line_of_business);
        teach = (CheckBox) findViewById(R.id.teatcingCheckBox);


    }

    public void toWhichToTeach(View view) {


        if(firstNameInput.getText().toString().equals(""))
        {
            Toast.makeText(this,"you left some empty fields",Toast.LENGTH_LONG).show();
        }
        else
        {
            if(!teach.isChecked())
            {
                toWhichToTeachIntent = new Intent(this,which_to_teach.class);
                put(toWhichToTeachIntent);
                startActivity(toWhichToTeachIntent);

            }
            else

            {
                toWhichToLearnIntent = new Intent(this,which_to_learn.class);
                put(toWhichToLearnIntent);
                startActivity(toWhichToLearnIntent);

            }

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


    }

    public void toMain(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

}
