package co.com.maxpetshop.mongo.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Data
@Document(collection = "receipt")
@NoArgsConstructor
public class ReceiptData {

    @Id
    private String id;

    private CartData cart;

    private Date createDate;

    private UserData user;

    @NotNull(message = "Phone can't be null")
    @NotBlank(message = "Phone can't be empty")
    private String phone;

    @NotNull(message = "Address can't be null")
    @NotBlank(message = "Address can't be empty")
    private String address;

    public ReceiptData(String phone, String address) {
        this.id = UUID.randomUUID().toString().substring(0,10);
        this.cart = null;
        this.createDate = new Date();
        this.user = null;
        this.phone = phone;
        this.address = address;
    }
}
