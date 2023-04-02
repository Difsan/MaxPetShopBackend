package co.com.maxpetshop.model.receipt;
import co.com.maxpetshop.model.cart.Cart;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Receipt {

    private String Id;
    private Cart cart;
    private Date createDate;
    private String phone;
    private String address;
}
