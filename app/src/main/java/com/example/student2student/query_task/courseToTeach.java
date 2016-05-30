package com.example.student2student.query_task;

public class courseToTeach {
    public String id;
    public String course;

    public courseToTeach(String id, String course) {
        this.id = id;
        this.course = course;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return id + "\t" + course;
    }
}
