package com.example.student2student;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.student2student.query_task.ArraylistQueryInterface;
import com.example.student2student.query_task.import_list;
import com.example.student2student.query_task.insert_student;

import java.util.ArrayList;

public class which_to_teach extends AppCompatActivity {

    insert_student is;
    import_list importList;
    ArrayAdapter<String> adapter;
    ListView listOfCourse;
    ArrayList<String>  myItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_which_to_teach);

        Bundle data = getIntent().getExtras();
        if (data == null)
            return;

        String first, last, id, email, lob;
        boolean toTeach;
        first = data.getString("first");
        last = data.getString("last");
        id = data.getString("id");
        email = data.getString("email");
        lob = data.getString("line of business");
        toTeach = data.getBoolean("to teach");


//        ds = new insert_student(this,this,getTaskId());
//        ds.execute();

        filTheList();

        if(listOfCourse != null)
        {
            final Context context = this;
            listOfCourse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String item = myItems.get(position);
                    Intent intent = new Intent(context,which_to_learn.class);
                    intent.putExtra("item", item );
                    startActivity(intent);

                }
            });
        }


        final TextView change = (TextView) findViewById(R.id.change);
        change.setText("שלום לך " + first +" "+last);
        if (first == "" || last == "" || id == "" || email == "") {
            Intent intent = new Intent(this, new_user.class);
            startActivity(intent);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    private void filTheList(){

        importList = new import_list(this,getApplicationContext(),getTaskId());
        importList.setInterface(new ArraylistQueryInterface() {
            @Override
            public void onSuccess(ArrayList<String> response) {
                Log.e("test", response.toString());
            }

            @Override
            public void onError() {

            }
        });
        importList.execute();
//        AsyncTask<String, String, ArrayList<String>> il = importList.execute();
//        ArraylistQueryInterface callback = new ArraylistQueryInterface() {
//            @Override
//            public void onSuccess(ArrayList<String> response) {
//
//            }
//
//            @Override
//            public void onError() {
//
//            }
//        };
//        importList.setCallback(callback);

//        importList.execute();
        int count = 0;
//        String[] myItem = {"אלגוריתמיקה", "הנדסת תוכנה", "חדוא 2"  , "חדוא 1" ,  "מבוא לחדוא" , "מתמטיקה בדידה 1" , "מתמטיקה בדידה 2" , "אלגברה ליניארית 1" , "אלגברה ליניארית 2" , "מיקרופרוססורים" , "תיכון מונחה עצמים" ,  "מערכות מבוזרות"};
        this.myItems = new ArrayList<String>();



//        {
//           myItems.add(count,il.);
//        }
        myItems.add(0, "אלגוריתמיקה");
        myItems.add(1,"הנדסת תוכנה");
        myItems.add(2,"מבוא לחדוא");
        myItems.add(3,"חדוא 1");
        myItems.add(4,"חדוא 2");
        myItems.add(5,"מתמטיקה בדידה 1");
        myItems.add(6,"מתמטיקה בדידה 2");
        myItems.add(7,"אלגברה ליניארית 1");
        myItems.add(8,"אלגברה ליניארית 2");
        myItems.add(9,"מיקרופרוססורים");
        myItems.add(10,"מערכות מבוזרות");
        myItems.add(11,"תיכון מונחה עצמים");
        myItems.add(12,"כלכלה");
        myItems.add(13,"סגנונות מוזיקליים");

        adapter = new ArrayAdapter<String>(this, R.layout.teachitems, myItems);

        listOfCourse = (ListView) findViewById(R.id.listOfCourses);
        listOfCourse.setAdapter(adapter);


    }

    public void toNewUser(View view)
    {
        Intent intent = new Intent(this,new_user.class);
        startActivity(intent);
    }

    public void toWhichToLearn(View view)
    {
        Intent intent = new Intent(this,which_to_learn.class);
        startActivity(intent);
    }


}
