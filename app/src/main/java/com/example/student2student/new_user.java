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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void toWhichToTeach(View view)
    {
        Intent i = new Intent(this,which_to_teach.class);
//        Intent si = new Intent(this,main.class);

        final EditText firstNameInput = (EditText) findViewById(R.id.firstNameInput);
        final EditText lastNameInput = (EditText) findViewById(R.id.lastnameInput);
        final EditText IDInput = (EditText) findViewById(R.id.IDInput);
        final EditText emailInput = (EditText) findViewById(R.id.e_mailInput);
        final Spinner s = (Spinner) findViewById(R.id.line_of_business);
        final CheckBox teach = (CheckBox) findViewById(R.id.teatcingCheckBox);

        String first ,last , id , email , lob;
        boolean toTeach;
        first = firstNameInput.getText().toString();
        last = lastNameInput.getText().toString();
        id = IDInput.getText().toString();
        email = emailInput.getText().toString();
        lob = s.getSelectedItem().toString();
        if(teach.isChecked())
            toTeach = true;
        else
            toTeach = false;

        i.putExtra("first",first);
        i.putExtra("last",last);
        i.putExtra("id",id);
        i.putExtra("email",email);
        i.putExtra("line of business",lob);
        i.putExtra("to teach" , toTeach);


        Toast.makeText(this,"--"+first+"--",Toast.LENGTH_LONG).show();
        if(first != "")
        {
            startActivity(i);
        }
        else
        {
            Toast.makeText(this,"you left some empty fields",Toast.LENGTH_LONG).show();
//            startActivity(si);
        }
    }

    public void toMain(View view)
    {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }

}
