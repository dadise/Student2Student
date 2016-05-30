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

import com.example.student2student.query_task.ArraylistQueryInterface;
import com.example.student2student.query_task.StringQueryInterface;
import com.example.student2student.query_task.courseToTeach;
import com.example.student2student.query_task.import_list;
import com.example.student2student.query_task.insert_learn_to_student;
import com.example.student2student.query_task.insert_teach_to_student;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class which_to_learn extends AppCompatActivity {

    ArrayAdapter<String> adapter;
    ListView listOfCourse;
    ArrayList<String>  myItems;
    import_list importList;
    courseToTeach ctt;
    insert_learn_to_student ilts;
    static ArrayList<String> res;
    public static android.os.Handler h = new android.os.Handler();
    public static Runnable r;
    final Context context = this;

    /////////////////////////////////////////fix this class/////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_which_to_learn);

        final Bundle data = getIntent().getExtras();

        if(data == null)
        {
            Log.i("data from whichToTeach activity","not here");
            return;
        }

        final String first, last, ID, email, lob;
        boolean toTeach;
        first = data.getString("first");
        last = data.getString("last");
        ID = data.getString("id");
        email = data.getString("email");
        lob = data.getString("line of business");
        toTeach = data.getBoolean("to teach");

        importList = new import_list(getApplicationContext(), getTaskId());
        importList.setInterface(new ArraylistQueryInterface() {
            @Override
            public void onSuccess(ArrayList<CourseItem> response) {


//                filTheList(lob, response);
                h.post(r);
            }

            @Override
            public void onError(String errType)
            {
                Log.d("error whichToLearn", "111");
            }
        });
        
        importList.execute(lob);

        r = new Runnable()
        {
            @Override
            public void run()
            {
                Log.d("12345", "before");

                if(listOfCourse != null)
                {
                    listOfCourse.setOnItemClickListener(new AdapterView.OnItemClickListener()
                    {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                        {
                            Log.e("whichToLearn", "here");

                            String item = myItems.get(position);
                            Intent intent = new Intent(context,result.class);
                            put(intent,data);
                            ctt = new courseToTeach(ID,item);
                            Log.e("asdasdasd", item + "  " + ID);
                            ilts = new insert_learn_to_student(getApplicationContext(),getTaskId());
                            ilts.setInterface(new StringQueryInterface() {
                                @Override
                                public void onSuccess(String response) {
                                    Log.e("another", response.toString());
                                }

                                @Override
                                public void onError() {

                                }
                            });
                            ilts.execute(ctt);

                            startActivity(intent);
                        }
                    });

                }
                else
                {
                    Log.i("list is", "null");
                }

                Log.d("12345", "after");
            }
        };
        if(listOfCourse != null)
        {
            final Context context = this;
            listOfCourse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String item = myItems.get(position);
                    Intent intent = new Intent(context, result.class);
                    intent.putExtra("item", item);
                    startActivity(intent);

                }
            });
        }

    }

    private void filTheList(String lob, ArrayList<String> response) 
    {
        myItems = new ArrayList<String>();

        Log.e("test", response.toString());
        for (int i = 0; i < response.size(); i++)
        {
            Log.e("course", response.get(i));
            myItems.add(response.get(i));
        }


        adapter = new ArrayAdapter<String>(this, R.layout.teachitems, myItems);

//        h.post(r);
        listOfCourse = (ListView) findViewById(R.id.listOfCourses2);
        listOfCourse.setAdapter(adapter);


    }

    private void put(Intent i,Bundle b) 
    {
        i.putExtra("first",b.getString("first"));
        i.putExtra("last", b.getString("last"));
        i.putExtra("id", b.getString("id"));
        i.putExtra("email", b.getString("email"));
        i.putExtra("line of business", b.getString("line of business"));
        i.putExtra("to teach", b.getBoolean("to teach"));

    }

}
