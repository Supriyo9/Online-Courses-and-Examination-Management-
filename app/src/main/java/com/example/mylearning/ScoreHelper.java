package com.example.mylearning;

public class ScoreHelper {

    String name;
   // String email;
    //String course;

    //int set;

    int Score;


    public ScoreHelper()
    {
    }

    public ScoreHelper(String name,  int score) {
        this.name = name;
       // this.email = email;
        //this.course = course;
       // this.set = set;
        Score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*public String getEmail() {
        return email;
    }



    public void setEmail(String email) {
        this.email = email;
    }

    

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public int getSet() {
        return set;
    }

    public void setSet(int set) {
        this.set = set;
    }


 */

    public int  getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }
}
