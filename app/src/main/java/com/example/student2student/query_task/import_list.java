package com.example.student2student.query_task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.student2student.CourseItem;
import com.example.student2student.which_to_teach;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class import_list extends AsyncTask<String, String, ArrayList<CourseItem>> {

    private final Context context;
    private int brenchid;
    private String query;
    private ArrayList<CourseItem> listOfCourses;

    private ArraylistQueryInterface queryInterface;

    String DB_URL = "jdbc:mysql://a757fb85-09c9-49bc-8772-a58f008e58f6.mysql.sequelizer.com:3306/dba757fb8509c949bc8772a58f008e58f6?useUnicode=yes&characterEncoding=UTF-8";
    String USER = "bjqdlncpsginpfvs";
    String PASS = "BJeASLFDyGpkwA5dzbmJkWFsfwvF7KVGngwtuUhzXiS2q3oqspfHbpFMcUvuqaEW";

    public import_list(Context context, int brenchid) {
        this.context = context;
        this.brenchid = brenchid;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    public void setInterface(ArraylistQueryInterface queryInterface) {
        this.queryInterface = queryInterface;
    }


    @Override
    protected ArrayList<CourseItem> doInBackground(String... params) {

        listOfCourses = new ArrayList<>();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);

            String result = "\n Database connection succes\n";

            Statement st = con.createStatement();

            String s = params[0];
            query = "SELECT * FROM coursesByOcc where OccName = '" + s + "'";
            ResultSet rs = st.executeQuery(query);

            int count = 0;
            while (rs.next()) {
                listOfCourses.add(new CourseItem(rs.getString(2),false));
                count++;
            }
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listOfCourses;
    }

    @Override
    protected void onPostExecute(ArrayList<CourseItem> strings) {
        super.onPostExecute(strings);
        if (strings == null) {
            queryInterface.onError(null);
        } else {
            queryInterface.onSuccess(strings);
        }
    }


    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);

    }

//    public void setCallback(ArraylistQueryInterface callback) {
//        queryInterface = callback;
//    }

}
