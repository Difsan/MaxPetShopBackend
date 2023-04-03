package co.com.maxpetshop.mongo;

import co.com.maxpetshop.model.product.Product;
import co.com.maxpetshop.model.user.User;
import co.com.maxpetshop.mongo.data.ProductData;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MongoDBRepositoryProduct extends ReactiveMongoRepository<ProductData, String> {
}
