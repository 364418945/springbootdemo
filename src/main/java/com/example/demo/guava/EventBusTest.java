package com.example.demo.guava;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class EventBusTest {

    public static void main(String[] args) {
        EventBus eventBus = new EventBus();
        eventBus.register(new EventListener());
        eventBus.register(new EventListener1());
        eventBus.post(new Event("hao"));
        eventBus.post(new Event1("hao","other"));
    }

    public static class Event{
        String message;
        public Event(String message){
            this.message = message;
        }

        public String getMessage(){
            return message;
        }
    }

    public static class Event1{
        String message;
        String other;
        public Event1(String message,String other){
            this.message = message;
            this.other = other;
        }

        public String getMessage(){
            return message;
        }
        public String getOther(){
            return other;
        }
    }

    public static class EventListener{
        @Subscribe
        public void listen(Event event){
            System.out.println(event.getMessage());
        }
    }
    public static class EventListener1{
        @Subscribe
        public void listen(Event event){
            System.out.println(event.getMessage());
        }
        @Subscribe
        public void listen1(Event1 event){
            System.out.println(event.getMessage()+event.getOther());
        }
    }
}
