package com.example.student2student.query_task;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.student2student.student;
import com.example.student2student.which_to_teach;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class insert_teach_to_student extends AsyncTask<courseToTeach,Integer,Void> {
//    private final Activity activity;
    private final Context context;
    private int brenchid;
    public String query;

    String DB_URL = "jdbc:mysql://a757fb85-09c9-49bc-8772-a58f008e58f6.mysql.sequelizer.com:3306/dba757fb8509c949bc8772a58f008e58f6?useUnicode=yes&characterEncoding=UTF-8";
    String USER = "bjqdlncpsginpfvs";
    String PASS = "BJeASLFDyGpkwA5dzbmJkWFsfwvF7KVGngwtuUhzXiS2q3oqspfHbpFMcUvuqaEW";
    private StringQueryInterface queryInterface;

    public insert_teach_to_student( Context context, int brenchid)
    {
//        this.activity = (Activity) activity;
        this.context = context;
        this.brenchid = brenchid;
    }



    @Override
    protected Void doInBackground(courseToTeach... params) {

        String response = "";

        try {
            boolean running = true;
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);

            String result = "\n Database connection success\n";

            Statement st = con.createStatement();

            if (st != null) {
                Log.i("good4", " statement " + con);
                Log.i("good4", " statement " + st);

            }

            courseToTeach ctt = params[0];
            Log.e("sdsf",ctt.course);


            query = "update students set teach='"+ ctt.course +"' where studentID ='"+ctt.id+"'";
//            update students set teach='שדגדשגשדגשדג' where studentID ='123'

            st.executeUpdate(query);

            Log.i("answer", response);
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

//        return;

        return null;
    }

    public void setInterface(StringQueryInterface ArrayQueryInterface){
        this.queryInterface = ArrayQueryInterface;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate();

        Log.i("TAG6", "ist fine!!");
    }

}