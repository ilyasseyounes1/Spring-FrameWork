package org.example.springboot1.Service;

import org.example.springboot1.Model.Product;

import java.util.List;
import java.util.Arrays;

// for business  logic
public class ProductService{
   List <Product> products = Arrays.asList(
           new Product(101,"Iphone",50000),
           new Product ( 102,"Huawei",80000 ),
           new Product(103,"Canon",50000));

   public List<Product> getProducts(){
       return products;
   }
}
