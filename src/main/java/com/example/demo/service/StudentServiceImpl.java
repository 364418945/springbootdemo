package com.example.demo.service;

public class StudentServiceImpl implements StudentService {
    String lastName;
    @Override
    public void say() {
        System.out.println("username is sun");
        System.out.println(lastName);
        this.sayY("ss", 1, "ssssss");
    }

    private void sayY(String ss, int age, String a) {
        System.out.println(a);
        System.out.println(a);
        System.out.println(a);
        System.out.println(a);
        System.out.println(a);
    }
}
