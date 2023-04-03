package co.com.maxpetshop.mongo;

import co.com.maxpetshop.model.product.Product;
import co.com.maxpetshop.model.receipt.Receipt;
import co.com.maxpetshop.mongo.data.ReceiptData;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MongoDBRepositoryReceipt extends ReactiveMongoRepository<ReceiptData, String> {
}
