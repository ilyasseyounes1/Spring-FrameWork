package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Dev{
    @Autowired // field injection
    private Computer comp;

    public Dev (Laptop laptop){
        this.comp = laptop;
    }
    public  void build(){
        System.out.println ("working on a project");
        comp.compile();
    }
}
