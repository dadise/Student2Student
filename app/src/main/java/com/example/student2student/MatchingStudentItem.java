package com.example.student2student;

import java.util.ArrayList;

public class MatchingStudentItem {
    private String firstName, lastName, studentMail, teach, grade, phone;

    public MatchingStudentItem(String firstName, String lastName, String studentMail, String teach, String grade, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentMail = studentMail;
        this.teach = teach;
        this.grade = grade;
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStudentMail() {
        return studentMail;
    }

    public void setStudentMail(String studentMail) {
        this.studentMail = studentMail;
    }

    public String getTeach() {
        return teach;
    }

    public void setTeach(String teach) {
        this.teach = teach;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
