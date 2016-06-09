package com.example.student2student.query_task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.student2student.MatchingStudentItem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class matching extends AsyncTask<MatchingData, String, ArrayList<MatchingStudentItem>> {

    private final Context context;
    private int brenchid;
    private String query;
    private ArrayList<MatchingStudentItem> listOfMatches;
    private ProgressDialog progress;
    private MatchingInterface queryInterface;

    private ArrayList<String> whichToLearn, whichToTeach;

    String DB_URL = "jdbc:mysql://a757fb85-09c9-49bc-8772-a58f008e58f6.mysql.sequelizer.com:3306/dba757fb8509c949bc8772a58f008e58f6?useUnicode=yes&characterEncoding=UTF-8";
    String USER = "bjqdlncpsginpfvs";
    String PASS = "BJeASLFDyGpkwA5dzbmJkWFsfwvF7KVGngwtuUhzXiS2q3oqspfHbpFMcUvuqaEW";

    public matching(Context context, int brenchid) {
        this.context = context;
        this.brenchid = brenchid;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    public void setInterface(MatchingInterface queryInterface) {
        this.queryInterface = queryInterface;
    }


    @Override
    protected ArrayList<MatchingStudentItem> doInBackground(MatchingData... params) {
//        progress = ProgressDialog.show(context, "dialog title", "dialog message", true);

        listOfMatches = new ArrayList<>();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);

            String result = "\n Database connection success\n";

            Statement st = con.createStatement();

            String coursesToTeach = "";
            for (int i = 0; i < params[0].getTeach().size(); i++) {
                coursesToTeach += "learn like '%" + params[0].getTeach().get(i) + "%'";
                if (i < params[0].getTeach().size() - 1) {
                    coursesToTeach += " OR ";
                }
            }

            String coursesToLearn = "";
            for (int i = 0; i < params[0].getLearn().size(); i++) {
                coursesToLearn += "teach like '%" + params[0].getLearn().get(i) + "%'";
                if (i < params[0].getLearn().size() - 1) {
                    coursesToLearn += " OR ";
                }
            }

            query = "SELECT firstName,lastName,studentID,studentMail,studentOcc,isTeach,teach,grade,phone,\n" +
                    "learn IN (Select learn FROM students where " + coursesToTeach + ") " +
                    "FROM students " +
                    "where (studentID !=" + params[0].getUserID() + " AND (" + coursesToLearn + "))";
            Log.e("sss",query);
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                listOfMatches.add(
                        new MatchingStudentItem(
                                rs.getString("firstName"),
                                rs.getString("lastName"),
                                rs.getString("studentMail"),
                                rs.getString("teach"),
                                rs.getString("grade"),
                                rs.getString("phone")));
            }
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listOfMatches;
    }

    @Override
    protected void onPostExecute(ArrayList<MatchingStudentItem> strings) {
        super.onPostExecute(strings);
        queryInterface.onSuccess(strings);
//        progress.dismiss();
    }


    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    public interface MatchingInterface {
        void onSuccess(ArrayList<MatchingStudentItem> resultList);
        void onError();
    }
}
