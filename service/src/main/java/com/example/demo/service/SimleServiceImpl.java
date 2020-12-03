package com.example.demo.service;

public class SimleServiceImpl implements SimpleService {
    @Override
    public void testService() {
        System.out.println("i come from service loader!");
    }
}
