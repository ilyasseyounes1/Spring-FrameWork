package org.example.springboot1.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class HomeController{
    @RequestMapping("/")
    public String home (){
        return "Welcom to Home Page";
        // if I use @Controller it s going to return a file named "welcome to home page"
    }
    @RequestMapping("/about")
    public String about (){
        return "Welcome to About";
    }
}
