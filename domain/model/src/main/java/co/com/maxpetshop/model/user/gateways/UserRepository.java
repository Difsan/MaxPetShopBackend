package co.com.maxpetshop.model.user.gateways;

import co.com.maxpetshop.model.cart.Cart;
import co.com.maxpetshop.model.user.User;
import reactor.core.publisher.Mono;

public interface UserRepository {

    Mono<User> getUserById(String userId);

    //It should be implemented in cart(?)
    Mono<Cart> getUserCartById(String cartId);

    Mono<User> saveUser( User user);

    Mono<User> updateUser( String userId, User flower);

    Mono<Void> deleteUser( String userId);

}
