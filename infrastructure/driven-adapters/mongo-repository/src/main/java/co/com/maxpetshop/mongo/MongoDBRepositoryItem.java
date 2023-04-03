package co.com.maxpetshop.mongo;

import co.com.maxpetshop.model.item.Item;
import co.com.maxpetshop.model.product.Product;
import co.com.maxpetshop.mongo.data.ItemData;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MongoDBRepositoryItem extends ReactiveMongoRepository<ItemData, String> {
}
