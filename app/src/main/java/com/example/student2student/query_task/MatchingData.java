package com.example.student2student.query_task;

import java.util.ArrayList;

public class MatchingData {
    private ArrayList<String> learn, teach;
    private String userID;

    public MatchingData(ArrayList<String> learn, ArrayList<String> teach, String userID) {
        this.learn = learn;
        this.teach = teach;
        this.userID = userID;
    }

    public ArrayList<String> getLearn() {
        return learn;
    }

    public void setLearn(ArrayList<String> learn) {
        this.learn = learn;
    }

    public ArrayList<String> getTeach() {
        return teach;
    }

    public void setTeach(ArrayList<String> teach) {
        this.teach = teach;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
