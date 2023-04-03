package co.com.maxpetshop.usecase.product.updateproduct;

import co.com.maxpetshop.model.product.Product;
import co.com.maxpetshop.model.product.gateways.ProductRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@RequiredArgsConstructor
public class UpdateProductUseCase implements BiFunction<String, Product, Mono<Product>> {

    private final ProductRepository productRepository;


    @Override
    public Mono<Product> apply(String productId, Product product) {
        return productRepository.updateProduct(productId, product);
    }
}
