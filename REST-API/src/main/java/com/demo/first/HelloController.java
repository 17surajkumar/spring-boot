package com.demo.first;

//Endpoints recieve request from the browser and send response back to the browser

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")  //To set the prefixes in the api endpoints
public class HelloController {

    // GET: HTTPS request
    @GetMapping("/hello")
    public String sayHello(){
        return "Hello World";
    }

//    @GetMapping("/user")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public User getUser(){
        User user = new User(1, "John Doe", "john@gmail.com");
        return user;
    }
}
