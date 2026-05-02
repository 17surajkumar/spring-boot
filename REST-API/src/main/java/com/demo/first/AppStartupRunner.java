package com.demo.first;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppStartupRunner implements CommandLineRunner {
    @Override
    // You can add any initialization logic here, such as loading data, setting up resources, etc.
    public void run(String... args) throws Exception {
        System.out.println("Application has started. Running startup tasks...");
    }
}
