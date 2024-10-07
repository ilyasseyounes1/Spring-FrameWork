package org.example.springboot1.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor

public class Product{
    private int ProdId;
    private String ProdName;
    private int price ;
    // when we use private we need getter and setter ..... so we use lombok to do that for us.

    @Override
    public String toString () {
        return "Product{" +
                "ProdId=" + ProdId +
                ", ProdName='" + ProdName + '\'' +
                ", price=" + price +
                '}';
    }
}
