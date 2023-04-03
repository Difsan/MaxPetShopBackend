package co.com.maxpetshop.mongo.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document(collection = "user")
@NoArgsConstructor
public class UserData {

    @Id
    private String id;

    @NotNull(message = "Name can't be null")
    @NotBlank(message = "Name can't be empty")
    private String name;

    @NotNull(message = "LastName can't be null")
    @NotBlank(message = "LastName can't be empty")
    private String lastName;

    @NotNull(message = "Email can't be null")
    @NotBlank(message = "Email can't be empty")
    private String email;

    @NotNull(message = "Password can't be null")
    @NotBlank(message = "Password can't be empty")
    private String password;

    private CartData cart;

    public UserData(String name, String lastName, String email, String password){
        this.id = UUID.randomUUID().toString().substring(0,10);
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.cart = new CartData();
    }
}
