package co.com.maxpetshop.model.cart;
import co.com.maxpetshop.model.item.Item;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {

    private String id;
    private Set<Item> items;
    private Double totalPrice;
}
