package co.com.maxpetshop.mongo;

import co.com.maxpetshop.model.user.User;
import co.com.maxpetshop.mongo.data.UserData;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import reactor.core.publisher.Mono;

public interface MongoDBRepositoryUser extends ReactiveMongoRepository<UserData, String> {
    Mono<UserData> findByCartId(String cartId);
}
