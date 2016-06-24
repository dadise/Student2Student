package com.example.student2student.query_task;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.student2student.student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class insert_to_grade_table extends AsyncTask<String, String, String> {


    private final Context context;
    private int brenchid;
    public String query;

    ///////////////////// the end of the DB_URL is insert hebrew to db ////////////////////////
    String DB_URL = "jdbc:mysql://a757fb85-09c9-49bc-8772-a58f008e58f6.mysql.sequelizer.com:3306/dba757fb8509c949bc8772a58f008e58f6?useUnicode=yes&characterEncoding=UTF-8";
    String USER = "bjqdlncpsginpfvs";
    String PASS = "BJeASLFDyGpkwA5dzbmJkWFsfwvF7KVGngwtuUhzXiS2q3oqspfHbpFMcUvuqaEW";
    private GradeInterface gradeInterface;

    public insert_to_grade_table( Context context, int brenchid) {
        this.context = context;
        this.brenchid = brenchid;
    }

    @Override
    protected String doInBackground(String... params) {

        String response = "";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);

            String result = "\n Database connection success\n";

            Statement st = con.createStatement();

            String toID = params[0];
            String fromID = params[1];

            query = "insert into grade_table values('" + fromID + "','" + toID + "')";
            st.executeUpdate(query);

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            if (e.toString().contains("Duplicate")) {
                gradeInterface.onError();
                return response;
            }
        }
        gradeInterface.onSuccess();
        return response;
    }


    public void setInterface(GradeInterface gradeInterface) {
        this.gradeInterface = gradeInterface;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

    }

    public interface GradeInterface{
        void onSuccess();
        void onError();
    }

}
