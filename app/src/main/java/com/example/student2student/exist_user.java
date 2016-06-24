package com.example.student2student;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.student2student.query_task.ImportUserInterface;
import com.example.student2student.query_task.import_user;

public class exist_user extends AppCompatActivity {

    EditText email;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exist_user);

        email = (EditText) findViewById(R.id.emailEditText);
        password = (EditText) findViewById(R.id.passwordEditText);
    }

    public void toResult(View view) {
        import_user importUser = new import_user(this, getTaskId());
        importUser.setInterface(new ImportUserInterface() {
            @Override
            public void onSuccess(student stud) {
                Intent intent = new Intent(exist_user.this, result.class);
                intent.putExtra("first", stud.getFirst());
                intent.putExtra("last", stud.getLast());
                intent.putExtra("id", stud.getId());
                intent.putExtra("email", stud.getEmail());
                intent.putExtra("line of business", stud.getLob());
                intent.putExtra("grade", stud.getGrade());
                if (stud.getTeacher().equals("1")) {
                    intent.putExtra("to teach", true);
                } else {
                    intent.putExtra("to teach", false);
                }

                intent.putExtra("phone", stud.getPhone());
                intent.putExtra("coursesToTeach", stud.getTeach());
                intent.putExtra("coursesToLearn", stud.getLearn());
                startActivity(intent);
            }

            @Override
            public void onError(final String err) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(exist_user.this, err, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        if (password.getText().toString().length() < 9) {
            Toast.makeText(exist_user.this, "ID is too short", Toast.LENGTH_SHORT).show();
        } else {
            importUser.execute(password.getText().toString(), email.getText().toString());
        }
    }

    public void toMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
