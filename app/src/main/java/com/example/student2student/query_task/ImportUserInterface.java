package com.example.student2student.query_task;

import com.example.student2student.student;

public interface ImportUserInterface {
    void onSuccess(student stud);
    void onError(String err);
}
