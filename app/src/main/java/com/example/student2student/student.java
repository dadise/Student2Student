package com.example.student2student;

public class student {
    private String first;
    private String last;
    private String id;
    private String email;
    private String lob;
    private String teacher;
    private String teach;
    private String learn;
    private String grade;
    private String phone;

    public student(String first, String last, String id, String email, String lob, String teacher, String teach, String learn, String grade, String phone) {
        this.first = first;
        this.last = last;
        this.id = id;
        this.email = email;
        this.lob = lob;
        this.teacher = teacher;
        this.teach = teach;
        this.learn = learn;
        this.grade = grade;
        this.phone = phone;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLob() {
        return lob;
    }

    public void setLob(String lob) {
        this.lob = lob;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getTeach() {
        return teach;
    }

    public void setTeach(String teach) {
        this.teach = teach;
    }

    public String getLearn() {
        return learn;
    }

    public void setLearn(String learn) {
        this.learn = learn;
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

    @Override
    public String toString() {
        String str = "";

        str += first;
        str += "\t" + last;
        str += "\t" + id;
        str += "\t" + email;
        str += "\t" + lob;
        str += "\t" + teacher;
        str += "\t" + teach;
        str += "\t" + learn;
        str += "\t" + grade;
        str += "\t" + phone;

        return str;
    }
}
