package co.com.maxpetshop.usecase.product.getproductbyid;

import co.com.maxpetshop.model.product.Product;
import co.com.maxpetshop.model.product.gateways.ProductRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class GetProductByIdUseCase implements Function<String, Mono<Product>> {

    private final ProductRepository productRepository;

    @Override
    public Mono<Product> apply(String productId) {
        return productRepository.getProductById(productId);
    }
}
