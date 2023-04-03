package co.com.maxpetshop.mongo;

import co.com.maxpetshop.model.item.Item;
import co.com.maxpetshop.mongo.data.CartData;
import co.com.maxpetshop.mongo.data.ItemData;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MongoDBRepositoryCart extends ReactiveMongoRepository<CartData, String> {
}
