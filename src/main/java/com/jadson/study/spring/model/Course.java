package com.jadson.study.spring.model;

public class Course {

    private String name;
    private Integer age;
    private String teacherName;

    public Course() {
    }

    public Course(String teacherName) {
        this.teacherName = teacherName;
    }

    public void init(){
        System.out.println("Course init...");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", teacherName='" + teacherName + '\'' +
                '}';
    }
}
