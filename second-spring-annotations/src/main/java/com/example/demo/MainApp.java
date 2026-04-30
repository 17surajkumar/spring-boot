package com.example.demo;

import com.example.loose.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {


    public static void main(String[] args) {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

//        GreetingService greetingService =
//                (GreetingService) context.getBean("myBean");

        GreetingService greetingService = context.getBean(GreetingService.class);
        greetingService.sayHello();


        UserService userServiceSMS = context.getBean(UserService.class);
        userServiceSMS.notifyUser("Whats Up!");
        
//
//        UserService userServiceEmail =
//                (UserService) context.getBean("UserServiceEmail");
//        userServiceEmail.notifyUser("Whats Up!");
    }


}
