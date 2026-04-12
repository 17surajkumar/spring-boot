package com.example.loose;

public class AppMain {
    public static void main(String[] args) {
        NotificationService emailService = new EmailNotificationService();
        NotificationService smsService = new SMSNotificationService();

        //Dependency of userService on emailService (Dependence Injection)

        //Constructor Injection
        UserService userService =
                new UserService(emailService);
        userService.notifyUser("Order Placed!!");


        //Setter Injection
        UserService userServiceSetter =
                new UserService();
        userServiceSetter.setNotificationService(emailService);

        userServiceSetter.notificationService = smsService;

    }
}



/*
        Constructor Injection : dependency is provided via constructor
        Setter Injection : dependency is provided via setter method
        Field Injection : dependency is assigned directly to a field

 */