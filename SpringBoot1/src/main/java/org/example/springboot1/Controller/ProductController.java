package org.example.springboot1.Controller;

import org.example.springboot1.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

public class ProductController{
    @Autowired
    ProductService service ;
    @RequestMapping("/products")
    public String getProducts (){
        return "";
    }
}
