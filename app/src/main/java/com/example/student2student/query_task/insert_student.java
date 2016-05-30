package com.example.student2student.query_task;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.student2student.student;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;

public class insert_student extends AsyncTask<student,String,String>{

    private final Activity activity;
    private final Context context;
    private int brenchid;
    public String query;

    ///////////////////// the end of the DB_URL is insert hebrew to db ////////////////////////
    String DB_URL = "jdbc:mysql://a757fb85-09c9-49bc-8772-a58f008e58f6.mysql.sequelizer.com:3306/dba757fb8509c949bc8772a58f008e58f6?useUnicode=yes&characterEncoding=UTF-8";
    String USER = "bjqdlncpsginpfvs";
    String PASS = "BJeASLFDyGpkwA5dzbmJkWFsfwvF7KVGngwtuUhzXiS2q3oqspfHbpFMcUvuqaEW";
    private ArraylistQueryInterface ArrayqueryInterface;

    public insert_student(Activity activity, Context context, int brenchid)
    {

        this.activity = activity;
        this.context = context;
        this.brenchid = brenchid;


    }

    @Override
    protected String doInBackground(student... params) {

        String response = "";

        try
        {
            boolean running = true;
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DB_URL,USER,PASS);

            String result = "\n Database connection succes\n";

            Statement st = con.createStatement();

            if(st != null)
            {
                Log.i("good3"," statement " +con);
                Log.i("good3"," statement " +st);

            }

            student s = params[0];
            Log.i("ssfsdf",s.lob);

            query = "insert into students values('" + s.first + "','" +s.last+ "','"+s.id+"','"+s.email+"','"+s.lob+"','"+s.teacher+"',null,null,null)";
//            Log.e("tttttt",query);

            st.executeUpdate(query);

            Log.i("answer",response);
            con.close();

        }
        catch (Exception e)
        {
            e.printStackTrace();
            if(e.toString().contains("Duplicate"))
            {
                ArrayqueryInterface.onError("Duplicate");
                return response;
            }
        }
        ArrayqueryInterface.onSuccess(null);
        return response;
    }


    public void setInterface(ArraylistQueryInterface ArrayQueryInterface){
        this.ArrayqueryInterface = ArrayQueryInterface;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);

        Log.i("TAG6", "ist fine!!");
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

    }

    //    public void setCallback(ArraylistQueryInterface callback) {
//        queryInterface = callback;
//    }
}
