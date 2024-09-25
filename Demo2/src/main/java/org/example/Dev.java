package org.example;

public class Dev{
    private Computer com;

    public Computer getCom () {
        return com;
    }
    public void setCom ( Computer com ) {
        this.com = com;
    }


    /***********************************************/
    //setter injection & constructor injection for ref var:
    private Laptop laptop;
    public void setLaptop ( Laptop laptop ) {
        this.laptop = laptop;
    }
    public Laptop getLaptop () {
        return laptop;
    }

    public Dev ( Laptop laptop ) {
        this.laptop = laptop;
    }

    /**************************************************/
    // setter injection & constractor injection for primitive var;
    int age;
    public int getAge () {
        return age;
    }
    public void setAge ( int age ) {
        this.age = age;
    }
    public Dev ( int age ) {
        this.age = age;
        System.out.println ("constructor injection of age");
    }
    /***************************************************/

    public Dev (){
        System.out.println ("Dev Constructor");
    }
    public void build (){
        System.out.println ("Building Dev project");
        com.compile ();
    }
    // stop in Spring config.
}
