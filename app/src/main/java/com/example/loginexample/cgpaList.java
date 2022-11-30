package com.example.loginexample;

public class cgpaList {
    private String name;
    private float cgpa;

    public cgpaList() {

    }

    public cgpaList(String name, float cgpa) {
        this.name = name;
        this.cgpa = cgpa;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCgpa() {
        return cgpa;
    }

    public void setCgpa(float cgpa) {
        this.cgpa = cgpa;
    }
}
