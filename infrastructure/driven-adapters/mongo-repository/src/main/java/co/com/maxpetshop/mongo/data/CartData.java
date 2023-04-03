package co.com.maxpetshop.mongo.data;


import co.com.maxpetshop.model.item.Item;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Document(collection = "cart")
@NoArgsConstructor
public class CartData {

    @Id
    private String id;
    private List<Item> items;

    private Double totalPrice;

    public CartData(Double totalPrice) {
        this.id = UUID.randomUUID().toString().substring(0,10);
        this.items = new ArrayList<>();
        this.totalPrice = totalPrice;
    }
}
