package org.example.springboot1.Service;

import org.example.springboot1.Model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

// for business  logic
@Service
public class ProductService{
   List <Product> products =new ArrayList<> (Arrays.asList(
           new Product(101,"Iphone",50000),
           new Product ( 102,"Huawei",80000 ),
           new Product(103,"Canon",50000)) );
   public List<Product> getProducts(){
       return products;
   }
   public Product getProductById (int prodId){
       return products.stream ()
               .filter ( P -> P.getProdId() == prodId )
               .findFirst ().orElse (new Product (100,"No Item Found",0 ));
   }
   public void addProduct (Product prod){
       products.add(prod);
   }
}
