package com.example.student2student.query_task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.student2student.CourseItem;
import com.example.student2student.student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class import_user extends AsyncTask<String, String, student> {

    private final Context context;
    private int brenchid;
    private String query;
    private ArrayList<CourseItem> listOfCourses;

    private ImportUserInterface queryInterface;

    String DB_URL = "jdbc:mysql://a757fb85-09c9-49bc-8772-a58f008e58f6.mysql.sequelizer.com:3306/dba757fb8509c949bc8772a58f008e58f6?useUnicode=yes&characterEncoding=UTF-8";
    String USER = "bjqdlncpsginpfvs";
    String PASS = "BJeASLFDyGpkwA5dzbmJkWFsfwvF7KVGngwtuUhzXiS2q3oqspfHbpFMcUvuqaEW";

    public import_user(Context context, int brenchid) {
        this.context = context;
        this.brenchid = brenchid;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    public void setInterface(ImportUserInterface queryInterface) {
        this.queryInterface = queryInterface;
    }


    @Override
    protected student doInBackground(String... params) {

        listOfCourses = new ArrayList<>();
        student stud = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);

            String result = "\n Database connection succes\n";

            Statement st = con.createStatement();

            query = "SELECT * FROM students where studentID = '" + params[0] + "' AND studentMail='" + params[1] + "'";
            ResultSet rs = st.executeQuery(query);

            int count = 0;
            rs.next();


            String teach = rs.getString("teach");
            if (teach==null)
                teach="nothing";

            String learn = rs.getString("learn");
            if (learn==null)
                learn="nothing";

            String grade = rs.getString("grade");
            if (grade==null)
                grade="0#0";

            stud = new student(
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("studentID"),
                    rs.getString("studentMail"),
                    rs.getString("studentOcc"),
                    rs.getString("isTeach"),
                    teach,
                    learn,
                    grade,
                    rs.getString("phone"));

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return stud;
    }

    @Override
    protected void onPostExecute(student stud) {
        super.onPostExecute(stud);
        if (stud == null) {
            queryInterface.onError("המשתמש לא נמצא");
        } else {
            queryInterface.onSuccess(stud);
        }
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }


}
