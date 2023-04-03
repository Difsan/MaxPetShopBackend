package co.com.maxpetshop.usecase.getproductbyanimaltype;

import co.com.maxpetshop.model.product.Product;
import co.com.maxpetshop.model.product.gateways.ProductRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@RequiredArgsConstructor
public class GetProductByanimalTypeUseCase implements Function<String, Flux<Product>> {

    private final ProductRepository productRepository;

    @Override
    public Flux<Product> apply(String animalType) {
        return productRepository.getProductByanimalType(animalType);
    }
}
