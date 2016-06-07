package com.example.student2student;

import java.util.ArrayList;

public class ItemResult {
    private String name, email, phone, course;

    public ItemResult(String name, String email, String phone, String course) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.course = course;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
