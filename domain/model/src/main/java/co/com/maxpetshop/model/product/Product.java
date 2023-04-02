package co.com.maxpetshop.model.product;
import co.com.maxpetshop.model.category.Category;
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
public class Product {

    private String id;
    private String name;
    private String brand;
    private String description;
    private String image;
    private String animalType;
    private Category category;
    private Integer unitaryPrice;
    private Integer iva;
    private Integer inventory;
    private Boolean inStock;
}
