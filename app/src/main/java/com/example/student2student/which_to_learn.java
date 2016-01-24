package com.example.student2student;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class which_to_learn extends AppCompatActivity {

    ArrayAdapter<String> adapter;
    ListView listOfCourse;
    ArrayList<String>  myItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_which_to_learn);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String course = intent.getStringExtra("item");
        listOfCourse = (ListView) findViewById(R.id.listOfCourses2);

        filTheList();

        if(listOfCourse != null)
        {
            final Context context = this;
            listOfCourse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String item = myItems.get(position);
                    Intent intent = new Intent(context,result.class);
                    intent.putExtra("item", item );
                    startActivity(intent);

                }
            });
        }

    }

    private void filTheList() {
//        String[] myItem = {"אלגוריתמיקה", "הנדסת תוכנה", "חדוא 2"  , "חדוא 1" ,  "מבוא לחדוא" , "מתמטיקה בדידה 1" , "מתמטיקה בדידה 2" , "אלגברה ליניארית 1" , "אלגברה ליניארית 2" , "מיקרופרוססורים" , "תיכון מונחה עצמים" ,  "מערכות מבוזרות"};
        this.myItems = new ArrayList<String>();
        myItems.add(0, "אלגוריתמיקה");
        myItems.add(1, "הנדסת תוכנה");
        myItems.add(2, "מבוא לחדוא");
        myItems.add(3, "חדוא 1");
        myItems.add(4, "חדוא 2");
        myItems.add(5, "מתמטיקה בדידה 1");
        myItems.add(6, "מתמטיקה בדידה 2");
        myItems.add(7, "אלגברה ליניארית 1");
        myItems.add(8, "אלגברה ליניארית 2");
        myItems.add(9, "מיקרופרוססורים");
        myItems.add(10, "מערכות מבוזרות");
        myItems.add(11, "תיכון מונחה עצמים");
        myItems.add(12, "כלכלה");
        myItems.add(13, "סגנונות מוזיקליים");

        adapter = new ArrayAdapter<String>(this, R.layout.learnitems, myItems);
        listOfCourse.setAdapter(adapter);
    }
}
