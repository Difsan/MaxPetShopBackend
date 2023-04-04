package co.com.maxpetshop.model.cart;
import co.com.maxpetshop.model.item.Item;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Cart {

    private String id = UUID.randomUUID().toString().substring(0,10);
    private Set<Item> items = new HashSet<>();
    private Double totalPrice;
}
