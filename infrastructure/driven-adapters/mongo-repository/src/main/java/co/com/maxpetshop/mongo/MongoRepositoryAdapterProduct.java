package co.com.maxpetshop.mongo;

import co.com.maxpetshop.model.product.Product;
import co.com.maxpetshop.model.product.gateways.ProductRepository;
import co.com.maxpetshop.model.user.gateways.UserRepository;
import co.com.maxpetshop.mongo.data.ProductData;
import co.com.maxpetshop.mongo.data.UserData;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class MongoRepositoryAdapterProduct implements ProductRepository
{
    private final MongoDBRepositoryProduct repository;

    private final ObjectMapper mapper;

    @Override
    public Flux<Product> getAllProducts() {
        return this.repository
                .findAll()
                .switchIfEmpty(Flux.empty())
                .map(productData -> mapper.map(productData, Product.class));
    }

    @Override
    public Mono<Product>getProductById(String productId) {

        return this.repository
                .findById(productId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("There is not " +
                        "Product with id: " + productId)))
                .map(productData -> mapper.map(productData, Product.class));
    }

    @Override
    public Flux<Product> getProductsByName(String productName) {
        return this.repository
                .findAll()
                .switchIfEmpty(Flux.empty())
                .filter(productData -> productData.getName().startsWith(productName))
                .map(productData -> mapper.map(productData, Product.class));
    }

    @Override
    public Flux<Product> getProductsByAnimalType(String productAnimalType) {
        return this.repository
                .findAll()
                .switchIfEmpty(Flux.empty())
                .filter(productData -> productData.getName().startsWith(productAnimalType))
                .map(productData -> mapper.map(productData, Product.class));
    }

    @Override
    public Flux<Product> getProductsByCategory(String productCategory) {
        return this.repository
                .findAll()
                .switchIfEmpty(Flux.empty())
                .filter(productData -> productData.getName().startsWith(productCategory))
                .map(productData -> mapper.map(productData, Product.class));
    }


    @Override
    public Mono<Product> saveProduct(Product product) {
        return this.repository
                .save(mapper.map(product, ProductData.class))
                .switchIfEmpty(Mono.empty())
                .map(productData -> mapper.map(productData, Product.class));
    }

    @Override
    public Mono<Product> updateProduct(String productId, Product product) {

        return this.repository
                .findById(productId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("There is not " +
                        "product with id: " + productId)))
                .flatMap(productData -> {
                    product.setId(productData.getId());
                    return repository.save(mapper.map(product, ProductData.class));
                })
                .map(productData -> mapper.map(productData, Product.class));
    }

    @Override
    public Mono<Void> deleteProduct (String productId) {

        return this.repository
                .findById(productId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("There is not " +
                        "product with id: " + productId)))
                .flatMap(userData -> this.repository.deleteById(userData.getId()));
    }
}
