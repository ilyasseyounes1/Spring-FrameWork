package org.example;

public class Dev{
    //private Laptop laptop;

    private int age ;

    public int getAge () {
        return age;
    }

    public void setAge ( int age ) {
        this.age = age;
    }

    public Dev (){
        System.out.println ("Dev Constructor");
    }
    public void build (){
        System.out.println ("Building Dev project");
    }
    // stop in Spring config.
}
