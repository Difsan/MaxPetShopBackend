package co.com.maxpetshop.model.user.gateways;

import co.com.maxpetshop.model.user.User;
import reactor.core.publisher.Mono;

public interface UserRepository {

    Mono<User> getUserById(String userId);

    Mono<User> saveUser( User user);

    Mono<User> updateUser( String userId, User flower);

    Mono<Void> deleteUser( String userId);

}
