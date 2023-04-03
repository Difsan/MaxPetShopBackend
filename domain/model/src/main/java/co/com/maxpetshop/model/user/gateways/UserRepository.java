package co.com.maxpetshop.model.user.gateways;


import co.com.maxpetshop.model.user.User;
import reactor.core.publisher.Mono;

public interface UserRepository {

    Mono<User> getUserById(String userId);

    //It should be implemented in cart(?)
    Mono<User> getUserByCartId(String cartId);

    Mono<User> saveUser( User user);

    Mono<User> updateUser( String userId, User user);

    Mono<Void> deleteUser( String userId);

}
