package com.example.demo;

import com.example.loose.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {
    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationBeanContext.xml");

        GreetingService greetingService =
                (GreetingService) context.getBean("myBean");

        greetingService.sayHello();


        UserService userServiceSMS =
                (UserService) context.getBean("UserServiceSMS");
        userServiceSMS.notifyUser("Whats Up!");

        UserService userServiceEmail =
                (UserService) context.getBean("UserServiceEmail");
        userServiceEmail.notifyUser("Whats Up!");
    }

}
