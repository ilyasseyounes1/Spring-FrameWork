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

    @GetMapping("/products/{prodId}") // @pathvar to match {prodId} with int pordId.
    public Product getProductById ( @PathVariable int prodId ) {
        return service.getProductById ( prodId );
    }

    // all @RequestMapping methode are by default, it s GET
    @PostMapping("/products") // @RequestBody : when u send a body from client to server : we us it to match data .
    public void addProduct (@RequestBody Product prod ) {
        System.out.println (prod);
        service.addProduct ( prod );
    }
    @PutMapping("/products")
    public void updateProduct (@RequestBody Product prod ) {
       service.updateProduct ( prod );
    }
    @DeleteMapping("/products/{prodId}")
    public void deleteProduct (@PathVariable int prodId ) {
        service.deleteProduct (prodId);
    }

}
