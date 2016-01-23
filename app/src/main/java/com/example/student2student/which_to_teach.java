package com.example.student2student;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class which_to_teach extends AppCompatActivity {

//    DALServer ds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_which_to_teach);

        Bundle data = getIntent().getExtras();
        if(data == null)
            return;

        String first ,last , id , email , lob;
        boolean toTeach;
        first = data.getString("first");
        last = data.getString("last");
        id = data.getString("id");
        email = data.getString("email");
        lob = data.getString("line of business");
        toTeach = data.getBoolean("to teach");

//        ds = new DALServer(this,this,getTaskId());
//        ds.execute();

        final TextView change = (TextView) findViewById(R.id.change);
        change.setText("_"+first+"_"+last+"_"+id+"_"+email+"_"+toTeach+"_");
        if(first == "" || last == "" || id == "" || email == "")
        {
            Intent intent = new Intent(this,new_user.class);
            startActivity(intent);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }



}
