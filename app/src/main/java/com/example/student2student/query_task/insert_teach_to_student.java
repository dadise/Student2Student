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
import java.util.ArrayList;

public class insert_teach_to_student extends AsyncTask<String, Integer, Void> {
    //    private final Activity activity;
    public static final String IS_TEACH = "isTeach", IS_LEARN = "isLearn";
    private final Context context;
    private int brenchid;
    public String query;

    String DB_URL = "jdbc:mysql://a757fb85-09c9-49bc-8772-a58f008e58f6.mysql.sequelizer.com:3306/dba757fb8509c949bc8772a58f008e58f6?useUnicode=yes&characterEncoding=UTF-8";
    String USER = "bjqdlncpsginpfvs";
    String PASS = "BJeASLFDyGpkwA5dzbmJkWFsfwvF7KVGngwtuUhzXiS2q3oqspfHbpFMcUvuqaEW";
    private StringQueryInterface queryInterface;
    private String studentID;
    private String type;

    public insert_teach_to_student(String type, String ID,Context context, int brenchid) {
        this.type = type;
        this.context = context;
        this.brenchid = brenchid;
        this.studentID = ID;
    }


    @Override
    protected Void doInBackground(String... params) {

        String response = "";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);

            String result = "\n Database connection success\n";

            Statement st = con.createStatement();


            String coursesToUpdate = params[0];
//            String coursesToUpdate = "";
//            for (int i = 0; i < coursesToTeach.size(); i++) {
//                coursesToUpdate += coursesToTeach.get(i).getCourse();
//                if (i < coursesToTeach.size() - 1) {
//                    coursesToUpdate += "#";
//                }
//            }

            if (type.equals(IS_TEACH)) {
                query = "update students set teach='" + coursesToUpdate + "' where studentID ='" + studentID + "'";
            } else if (type.equals(IS_LEARN)) {
                query = "update students set learn='" + coursesToUpdate + "' where studentID ='" + studentID + "'";
            }
//            update students set teach='שדגדשגשדגשדג' where studentID ='123'

Log.e("sss","q="+query);
            st.executeUpdate(query);
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void setInterface(StringQueryInterface ArrayQueryInterface) {
        this.queryInterface = ArrayQueryInterface;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate();
    }

}