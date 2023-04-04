package co.com.maxpetshop.mongo.data;


import co.com.maxpetshop.model.item.Item;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Data
@Document(collection = "cart")
@NoArgsConstructor
public class CartData {

    @Id
    private String id = UUID.randomUUID().toString().substring(0,10);
    private Set<Item> items = new HashSet<>();

    private Double totalPrice;

    public CartData(Double totalPrice) {
        this.id = UUID.randomUUID().toString().substring(0,10);
        this.items = new HashSet<>();
        this.totalPrice = totalPrice;
    }
}
