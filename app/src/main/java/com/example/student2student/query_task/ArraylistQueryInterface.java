package com.example.student2student.query_task;

import com.example.student2student.CourseItem;

import java.util.ArrayList;

public interface ArraylistQueryInterface {
    void onSuccess(ArrayList<CourseItem> response);
    void onError(String errType);
}
