package com.example.student2student;

public class CourseItem {
    private String courseName;
    private boolean isChecked;

    public CourseItem(String courseName, boolean isChecked) {
        this.courseName = courseName;
        this.isChecked = isChecked;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }


}
