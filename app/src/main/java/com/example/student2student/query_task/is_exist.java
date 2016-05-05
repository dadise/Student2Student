package com.example.student2student.query_task;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class is_exist extends AsyncTask<String,String,Boolean>
{
    private final Activity activity;
    private final Context context;
    private int brenchid;
    public String query;

//    private ArraylistQueryInterface queryInterface;

    String DB_URL = "jdbc:mysql://a757fb85-09c9-49bc-8772-a58f008e58f6.mysql.sequelizer.com:3306/dba757fb8509c949bc8772a58f008e58f6?useUnicode=yes&characterEncoding=UTF-8";
    String USER = "bjqdlncpsginpfvs";
    String PASS = "BJeASLFDyGpkwA5dzbmJkWFsfwvF7KVGngwtuUhzXiS2q3oqspfHbpFMcUvuqaEW";
    private BooleanQueryInterface stringQueryInterface;

    public is_exist(Activity activity, Context context, int brenchid)
    {

        this.activity = activity;
        this.context = context;
        this.brenchid = brenchid;


    }

    @Override
    protected Boolean doInBackground(String... params) {

        String response = "";
        boolean is = true;

        try
        {
            boolean running = true;
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);

            String result = "\n Database connection succes\n";

            Statement st = con.createStatement();

            if(st != null)
            {
                Log.i("good1", " statement " + con);
                Log.i("good1"," statement " +st);

            }
            String s = params[0];
            Log.i("kkkkkk",s);

            query = "SELECT firstName FROM students where studentID ='"+s+"'";

            ResultSet rs = st.executeQuery(query);
            if(rs.next())
            {
                response = rs.toString();
            }


            Log.i("answer in is exist", String.valueOf(response.length()));
            con.close();
            if(String.valueOf(response.length()) != "0")
            {
                Log.i("inside",response);
                is = false;
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return is;
    }

    @Override
    protected void onPostExecute(Boolean s) {
        super.onPostExecute(s);
//        if (s == null) {
//            queryInterface.onError();
//        }else {
//            queryInterface.onSuccess(s);
//        }
    }

    public void setInterface(BooleanQueryInterface stringQueryInterface)
    {
        this.stringQueryInterface = stringQueryInterface;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);

        Log.i("TAG6", "ist fine!!");
    }

//    public void setCallback(ArraylistQueryInterface callback) {
//        queryInterface = callback;
//    }

}
