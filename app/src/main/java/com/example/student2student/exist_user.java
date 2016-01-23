package com.example.student2student;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class exist_user extends AppCompatActivity {

    EditText existUserNameEditText;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exist_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        existUserNameEditText = (EditText) findViewById(R.id.existUserNameEditText);
        password = (EditText)findViewById(R.id.passwordEditText);
    }

//    public void toResult(View view)
//    {
//
//    }

    public void toMain(View view)
    {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

//    public void forgotPassword(View view)
//    {
//
//    }

}
