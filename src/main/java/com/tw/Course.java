package com.tw;

public class Course {

    private String course;
    private int score;

    public Course(String course, int score) {
        this.course = course;
        this.score = score;
    }

    public String getCourseName() {
        return course;
    }

    public int getScore() {
        return score;
    }
}
