package co.com.maxpetshop.mongo.data;

import co.com.maxpetshop.model.product.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document(collection = "item")
@NoArgsConstructor
public class ItemData {

    @Id
    private String id;


    private ProductData product;

    @NotNull(message = "Quantity can't be null")
    @NotBlank(message = "Quantity can't be empty")
    private Integer quantity;

    @NotNull(message = "Password can't be null")
    @NotBlank(message = "Password can't be empty")
    private Double subTotal;

    public ItemData(Integer quantity) {
        this.id = UUID.randomUUID().toString().substring(0,10);
        this.product = new ProductData();
        this.quantity = quantity;
        this.subTotal = this.quantity * product.getUnitaryPrice();
    }
}
