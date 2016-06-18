package com.example.student2student;

public class GradeAlgorithm {

    private String grade; //Grade is in the following format: gradeAvrg#numOfGraders

    public GradeAlgorithm(String grade) {
        this.grade = grade;
    }


    //((DB_grade x Graders)+currentGrade)/(graders+1)
    public String calcGrade(int currentGrade) {
        String arr[] = grade.split("\\#");

        double db_grade = Double.parseDouble(arr[0]);
        int graders = Integer.parseInt(arr[1]);
        double res= ((db_grade*graders)+currentGrade)/(graders+1);
        graders++;

        return res+"#"+graders;
    }
}
