package com.example.demo.serviceUse;

import com.example.demo.service.SimpleService;

import java.util.ServiceLoader;


public class ServiceLoaderTest {
    public static void main(String[] args) {
        ServiceLoader<SimpleService> serviceLoader = ServiceLoader.load(SimpleService.class);
        for (SimpleService simpleService : serviceLoader) {
            simpleService.testService();
        }
    }
}
