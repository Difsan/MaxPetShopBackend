package co.com.maxpetshop.mongo.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document(collection = "product")
@NoArgsConstructor
public class ProductData {

    @Id
    private String id;

    @NotNull(message = "Name can't be null")
    @NotBlank(message = "Name can't be empty")
    private String name;

    @NotNull(message = "Brand can't be null")
    @NotBlank(message = "Brand can't be empty")
    private String brand;

    private String description;

    @NotNull(message = "Image can't be null")
    @NotBlank(message = "Image can't be empty")
    private String image;

    @NotNull(message = "AnimalType can't be null")
    @NotBlank(message = "AnimalType can't be empty")
    private String animalType;

    @NotNull(message = "Category can't be null")
    @NotBlank(message = "Category can't be empty")
    private String category;

    @NotNull(message = "UnitaryPrice can't be null")
    @NotBlank(message = "UnitaryPrice can't be empty")
    private Double unitaryPrice;

    @NotNull(message = "Inventory can't be null")
    @NotBlank(message = "Inventory can't be empty")
    private Integer inventory;

    private Boolean inStock;

    public ProductData(String id, String name, String brand, String description,
                       String image, String animalType, String category,
                       Double unitaryPrice, Integer inventory) {
        this.id = UUID.randomUUID().toString().substring(0,10);
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.image = image;
        this.animalType = animalType;
        this.category = category;
        this.unitaryPrice = unitaryPrice;
        this.inventory = inventory;
        this.inStock = true;
    }
}
