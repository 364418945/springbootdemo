package com.example.demo.model;

import lombok.Data;

@Data
public class Student {

    private int i = 0;

    private Integer age;
    private String name;

    public Student(Integer age, String name) {
        this.age = age;
        this.name = name;
    }

    public Student(int i) {
        this.i = i;
    }


    @Override
    public String toString() {
        return "Student{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
//    public static void say(String word){
//        System.out.println(word);
//    }

}
