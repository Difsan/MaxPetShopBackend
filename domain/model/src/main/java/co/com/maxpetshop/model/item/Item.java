package co.com.maxpetshop.model.item;
import co.com.maxpetshop.model.product.Product;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Item {

    private String id;
    private Product product;
    private Integer quantity;
    private Integer subTotal;
}
