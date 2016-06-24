package com.example.student2student;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.student2student.query_task.ArraylistQueryInterface;
import com.example.student2student.query_task.import_list;
import com.example.student2student.query_task.insert_student;
import com.example.student2student.query_task.is_exist;
import com.example.student2student.query_task.BooleanQueryInterface;

import java.util.ArrayList;
//import com.example.student2student.query_task.insert_student;

public class new_user extends AppCompatActivity {

    import_list importList;
    is_exist isExist;
    insert_student insertStudent;
    boolean studentExist = false;
    EditText firstNameInput;
    EditText lastNameInput;
    EditText IDInput;
    EditText phoneImput;
    EditText emailInput;
    CheckBox isTeach;
    CheckBox regulation;
    Spinner s;
    student student;
    private Context context;
    String teaching = "", teach = "nothing", learn = "nothing", grade = "",phone ;
    String first, last, id, email, lob;
    boolean toTeach;

    Intent toWhichToTeachIntent, toWhichToLearnIntent,toNewUser;
    private TextView reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        context = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firstNameInput = (EditText) findViewById(R.id.firstNameInput);
        lastNameInput = (EditText) findViewById(R.id.lastnameInput);
        IDInput = (EditText) findViewById(R.id.IDInput);
        emailInput = (EditText) findViewById(R.id.e_mailInput);
        s = (Spinner) findViewById(R.id.line_of_business);
        isTeach = (CheckBox) findViewById(R.id.teatcingCheckBox);
        regulation = (CheckBox) findViewById(R.id.regulationsCheckBox);
        phoneImput = (EditText) findViewById(R.id.phone);

        reg = (TextView) findViewById(R.id.regulationsText);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "Student2Student \n" +
                        "סטודנט לסטודנט \n\n" +
                        "אפליקציה בשיתוף אגודת הסטודנטים \"עזריאלי - מכללה אקד להנדסה ירושלים\" \n" +
                        "האפליקציה מציעה שירות לחיפוש מורים פרטיים \n\n" +
                        "כאשר משתמש נכנס לראשונה לאפליקציה, עליו להזין את פרטיו, לבחור מקצועות אותם ירצה ללמד, לבחור מקצועות אותם ירצה ללמוד והאפליקציה תמצא לו התאמה עם מאגר המורים. \n" +
                        "כאשר המשתמש מצא מורה שמתאים לו, קיימות שתי דרכי תשלום: \n" +
                        "1. תשלום של 60 שקלים עבור שעת לימוד.  \n" +
                        "2. מתן שיעור פרטי, שיעור כנגד שיעור. \n\n" +
                        "האפליקציה ממיינת את המורים על פי דירוג של הסטודנטים אותם לימדו. \n" +
                        "בתום כל שיעור על המורה להכנס לחשבונו הפרטי ללחוץ על כפתור הדירוג וכאשר קופץ חלון הדירוג, להעביר את מכשיר הטלפון לסטודנט הלומד. \n" +
                        "על הסטודנט הלומד להזין את תעודת הזהות שלו בשדה הנדרש ואת דירוג המורה. \n\n" +
                        "כדי למנוע דירוג חוזר בין חברים \n" +
                        "לא יהיה ניתן לדרג את המורה שנית!!!!! \n" +
                        "לכן לפני שמאשרים חייבים להיות בטוחים שזה הדירוג אותו תרצו לתת למורה.\n";

                new AlertDialog.Builder(context)
                        .setTitle("regulations")
                        .setMessage(msg)
                        .setNeutralButton("בטל", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
    }

    public void toWhichToTeach(View view) {
        if (firstNameInput.getText().toString().equals("") || lastNameInput.getText().toString().equals("") ||
                IDInput.getText().toString().equals("") || emailInput.getText().toString().equals("")) {
            Toast.makeText(this, "you left some empty fields", Toast.LENGTH_LONG).show();
            return;
        }

        if(phoneImput.getText().toString().equals(""))
            phoneImput.setText("empty");
        if (isTeach.isChecked()) {
            teaching = "1";
        } else {
            teaching = "0";
        }
        student = new student(firstNameInput.getText().toString(), lastNameInput.getText().toString(), IDInput.getText().toString(), emailInput.getText().toString(), s.getSelectedItem().toString(), teaching, teach, learn, grade, phoneImput.getText().toString());

        insertStudent = new insert_student(this, getApplicationContext(), getTaskId());
        insertStudent.setInterface(new ArraylistQueryInterface() {
            @Override
            public void onSuccess(ArrayList<CourseItem> response) {
                    moveToNextActivity();
            }

            @Override
            public void onError(String errType) {
                switch (errType) {
                    case "Duplicate":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "User with that ID already exists", Toast.LENGTH_SHORT).show();
                            }
                        });

                        break;
                    default:
                        break;
                }
            }
        });
        if(regulation.isChecked()) {
            if(IDInput.length() != 9 ) {
                Toast.makeText(new_user.this, "enter your full ID number!!", Toast.LENGTH_SHORT).show();
            }
            else if(!checkID(IDInput.getText().toString())) {
                Toast.makeText(new_user.this, "enter your real ID number!!", Toast.LENGTH_SHORT).show();
            }
            else
                insertStudent.execute(student);
        }
        else {
            Toast.makeText(new_user.this, "please read the regulation", Toast.LENGTH_SHORT).show();
//            stayOnThisActivity();
        }
    }

    private void stayOnThisActivity() {
        toNewUser = new Intent(this,new_user.class);
        startActivity(toNewUser);
    }

    private void moveToNextActivity() {
        if (isTeach.isChecked()) {
            toWhichToTeachIntent = new Intent(this, which_to_teach.class);
            put(toWhichToTeachIntent);
            startActivity(toWhichToTeachIntent);

        } else
        {
            toWhichToLearnIntent = new Intent(this, which_to_learn.class);
            put(toWhichToLearnIntent);
            startActivity(toWhichToLearnIntent);

        }

    }

    private void put(Intent i) {
        first = firstNameInput.getText().toString();
        last = lastNameInput.getText().toString();
        id = IDInput.getText().toString();
        email = emailInput.getText().toString();
        lob = s.getSelectedItem().toString();

        first = first.trim();
        last = last.trim();
        id = id.trim();
        email = email.trim();

        i.putExtra("first", first);
        i.putExtra("last", last);
        i.putExtra("id", id);
        i.putExtra("email", email);
        i.putExtra("line of business", lob);
        i.putExtra("to teach", toTeach);
        i.putExtra("phone", phoneImput.getText().toString());
    }

    public void toMain(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public boolean checkID(String idNumber) {
        int mone = 0;
        int incNum;
        for( int i = 0 ; i < 9 ; i++ ) {
            incNum = Integer.parseInt(String.valueOf(idNumber.charAt(i)));
            incNum *= ( i % 2 ) + 1;
            if( incNum > 9 )
                incNum -= 9;
            mone += incNum;
        }
//        Toast.makeText(new_user.this,  ""+mone % 10 ,Toast.LENGTH_LONG).show();
        if( mone % 10 == 0 )
            return true;
        else
            return false;
    }
}
