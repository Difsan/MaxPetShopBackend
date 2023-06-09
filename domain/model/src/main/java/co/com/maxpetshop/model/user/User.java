package co.com.maxpetshop.model.user;
import co.com.maxpetshop.model.cart.Cart;
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
public class User {

    private String id;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private Cart cart;
    //private String cartId;
}
