package com.example.db;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


public class DatabaseConnection {
    private List<String> users;

    public void init(){
        users = new ArrayList<>();
        System.out.println("DB connected (Simulated)");
    }

    public List<String> getUsers(){
        return users;
    }

    public void addUser(String user){
        users.add(user);
    }

    public void cleanup(){
        System.out.println("DB Disconnected (Simulated)");
    }

}
