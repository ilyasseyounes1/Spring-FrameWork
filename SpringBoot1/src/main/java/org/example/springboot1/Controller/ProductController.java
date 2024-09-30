package org.example.springboot1.Controller;

import org.example.springboot1.Model.Product;
import org.example.springboot1.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController{
    @Autowired
    ProductService service;

    @GetMapping("/products")
    public String getProducts () {
        return service.getProducts ().toString ();
    }

    @GetMapping("/products/{prodId}")
    public Product getProductById ( @PathVariable int prodId ) {
        return service.getProductById ( prodId );
    }

    // all methode are  by default, it s GET
    @PostMapping("/products")
    public void addProduct ( Product prod ) {
        service.addProduct ( prod );
    }
}
