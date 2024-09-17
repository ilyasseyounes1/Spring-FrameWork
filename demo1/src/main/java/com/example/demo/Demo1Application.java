package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Demo1Application{

    public static void main ( String[] args ) {
        //SpringApplication.run ( Demo1Application.class , args );
        ApplicationContext context = SpringApplication.run ( Demo1Application.class , args );
        Dev obj = context.getBean ( Dev.class );
        obj.build ();
    }

}
