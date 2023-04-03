package co.com.maxpetshop.usecase.product.getallproducts;

import co.com.maxpetshop.model.product.Product;
import co.com.maxpetshop.model.product.gateways.ProductRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class GetAllProductsUseCase implements Supplier<Flux<Product>> {

    private final ProductRepository productRepository;

    @Override
    public Flux<Product> get() {
        return productRepository.getAllProducts();
    }
}
