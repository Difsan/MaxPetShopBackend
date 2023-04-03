package co.com.maxpetshop.model.product.gateways;

import co.com.maxpetshop.model.product.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository {

    Flux<Product> getAllProducts();

    Mono<Product> getProductById(String productId);

    Flux<Product> getProductByName (String productName);

    Flux<Product> getProductByanimalType (String productAnimalType);

    Flux<Product> getProductByCategory (String productCategory);

    Mono<Product> saveProduct( Product product);

    Mono<Product> updateProduct ( String productId, Product product);

    Mono<Void> deleteProduct( String productId);
}
