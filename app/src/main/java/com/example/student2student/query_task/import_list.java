package com.example.student2student.query_task;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class import_list extends AsyncTask<String,String,String> {

    private final Activity activity;
    private final Context context;
    private int brenchid;
    public String query;

    private QueryInterface queryInterface;

    String DB_URL = "jdbc:mysql://a757fb85-09c9-49bc-8772-a58f008e58f6.mysql.sequelizer.com:3306/dba757fb8509c949bc8772a58f008e58f6";
    String USER = "bjqdlncpsginpfvs";
    String PASS = "BJeASLFDyGpkwA5dzbmJkWFsfwvF7KVGngwtuUhzXiS2q3oqspfHbpFMcUvuqaEW";

    public import_list(Activity activity, Context context, int brenchid)
    {

        this.activity = activity;
        this.context = context;
        this.brenchid = brenchid;


    }

    @Override
    protected String doInBackground(String... params) {

        String response = "";

        try
        {
            boolean running = true;
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);

//            Log.i("here","aaaaaaaaaaGGGG");
            String result = "\n Database connection succes\n";

            Statement st = con.createStatement();

            if(st != null)
            {
                Log.i("good", " statement " + con);
                Log.i("good"," statement " +st);

            }
            query = "SELECT * FROM occupation where occID = 1";
//            ResultSet rs = st.executeQuery("SELECT * FROM students");

//            int count =0;

            ResultSet rs = st.executeQuery(query);

            while (rs.next())
            {
                response += rs.getString(1) +"  ";
            }
            Log.i("answer",response);
            con.close();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return response;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s == null) {
            queryInterface.onError();
        }else {
            queryInterface.onSuccess(s);
        }
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);

        Log.i("TAG6", "ist fine!!");
    }

    public void setCallback(QueryInterface callback) {
        queryInterface = callback;
    }

}
