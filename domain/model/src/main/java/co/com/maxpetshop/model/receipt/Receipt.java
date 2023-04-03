package co.com.maxpetshop.model.receipt;
import co.com.maxpetshop.model.cart.User;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Receipt {

    private String Id;
    private User cart;
    private Date createDate;
    private co.com.maxpetshop.model.user.User user;
    private String phone;
    private String address;
}
