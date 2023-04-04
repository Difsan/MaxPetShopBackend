package co.com.maxpetshop.model.receipt;
import co.com.maxpetshop.model.cart.Cart;
import co.com.maxpetshop.model.user.User;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Receipt {

    private String Id;
    private Cart cart;
    private LocalDate createDate;
    private User user;
    private String phone;
    private String address;
}
