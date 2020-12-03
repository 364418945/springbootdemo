package com.example.demo.guava;

import com.example.demo.model.Student;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class LambadTest {

    public static void main(String[] args) {
        String[] sts = new String[]{"123","333"};
        List<String> list = Arrays.asList(sts);
//        list.forEach(((Consumer<String>) s -> {
//            System.out.println(s);
//        }).andThen(System.out::print));

//        Student student = new Student(3,"mili");
//        Student student1 = new Student(4,"yueyue");
//
//        TreeSet<Student> s = new TreeSet(Comparator.comparing(Student::getName,String.CASE_INSENSITIVE_ORDER));
//        s.add(student);
//        s.add(student1);
//        System.out.println(s);
//        TreeSet<Student> s1 = new TreeSet(Comparator.comparingInt(Student::getAge).reversed());
//        s1.add(student);
//        s1.add(student1);
//        System.out.println(s1);


        Map mapResult = list.stream().collect(()->new HashMap<>(),(map,p)->map.put(p,p),(m,n)->m.putAll(n));
        System.out.println(mapResult);

        String result = list.stream().collect(Collectors.joining());
        System.out.println(result);

        String result1 = list.stream().collect(Collectors.joining());

    }
}
