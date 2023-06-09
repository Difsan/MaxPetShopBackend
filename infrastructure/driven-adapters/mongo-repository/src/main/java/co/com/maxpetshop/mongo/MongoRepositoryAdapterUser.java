package co.com.maxpetshop.mongo;

import co.com.maxpetshop.model.user.User;
import co.com.maxpetshop.model.user.gateways.UserRepository;
import co.com.maxpetshop.mongo.data.UserData;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class MongoRepositoryAdapterUser implements UserRepository
{
    private final MongoDBRepositoryUser repository;

    private final ObjectMapper mapper;

    @Override
    public Mono<User> getUserById(String userId) {

        return this.repository
                .findById(userId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("There is not " +
                        "user with id: " + userId)))
                .map(userData -> mapper.map(userData, User.class));
    }

    @Override
    public Mono<User> getUserByCartId(String cartId) {
        return this.repository
                .findAll()
                .switchIfEmpty(Mono.empty())
                .filter(userData -> userData.getCart().getId().equals(cartId))
                .switchIfEmpty(Mono.error(new IllegalArgumentException("There is not " +
                        "user with cartId: " + cartId)))
                .next()
                .map(userData -> mapper.map(userData, User.class));
    }

    @Override
    public Mono<User> getUserByEmail(String email) {
        return this.repository
                .findAll()
                .switchIfEmpty(Mono.empty())
                .filter(userData -> userData.getEmail().equals(email))
                .switchIfEmpty(Mono.error(new IllegalArgumentException("There is not " +
                        "user with email: " + email)))
                .next()
                .map(userData -> mapper.map(userData, User.class));
    }

    @Override
    public Mono<User> saveUser(User user) {
        return this.repository
                .save(mapper.map(user, UserData.class))
                .switchIfEmpty(Mono.empty())
                .map(userData -> mapper.map(userData, User.class));
    }

    @Override
    public Mono<User> updateUser(String userId, User user) {

        return this.repository
                .findById(userId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("There is not " +
                        "user with id: " + userId)))
                .flatMap(userData -> {
                    user.setId(userData.getId());
                    return repository.save(mapper.map(user, UserData.class));
                })
                .map(userData -> mapper.map(userData, User.class));
    }

    @Override
    public Mono<Void> deleteUser(String userId) {

        return this.repository
                .findById(userId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("There is not " +
                        "user with id: " + userId)))
                .flatMap(userData -> this.repository.deleteById(userData.getId()));
    }
}
