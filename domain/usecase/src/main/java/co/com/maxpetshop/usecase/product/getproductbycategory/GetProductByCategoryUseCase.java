package co.com.maxpetshop.usecase.product.getproductbycategory;

import co.com.maxpetshop.model.product.Product;
import co.com.maxpetshop.model.product.gateways.ProductRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.function.BiFunction;
import java.util.function.Function;

@RequiredArgsConstructor
public class GetProductByCategoryUseCase implements BiFunction<String, String, Flux<Product>> {

    private final ProductRepository productRepository;

    @Override
    public Flux<Product> apply(String productCategory, String productAnimalType) {
        return productRepository.getProductsByCategory(productCategory, productAnimalType);
    }
}
