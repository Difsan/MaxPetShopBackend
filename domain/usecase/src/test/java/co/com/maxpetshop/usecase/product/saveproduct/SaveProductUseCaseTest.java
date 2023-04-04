package co.com.maxpetshop.usecase.product.saveproduct;

import co.com.maxpetshop.model.product.Product;
import co.com.maxpetshop.model.product.gateways.ProductRepository;
import co.com.maxpetshop.usecase.product.updateproduct.UpdateProductUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.net.HttpURLConnection;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SaveProductUseCaseTest {

    @Mock
    ProductRepository repository;

    SaveProductUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new SaveProductUseCase(repository);
    }

    @Test
    @DisplayName("SaveProductUseCase_Success")
    void saveProduct() {
        var product = new Product("1", "Ringo Premium", "Ringo",
                "Food for adult dogs", "Image1", "Dog",
                "Food", 968.452, 5, true);

        Mockito.when(repository.saveProduct(product)).thenReturn(Mono.just(product));

        var result = useCase.apply(product);

        StepVerifier.create(result)
                .expectNext(product)
                .expectComplete()
                .verify();

        Mockito.verify(repository, Mockito.times(1)).saveProduct(product);
    }

    @Test
    @DisplayName("SaveProductUseCase_Failed")
    void saveProduct_Failed() {

        var product = new Product("1", "Ringo Premium", "Ringo",
                "Food for adult dogs", "Image1", "Dog",
                "Food", 968.452, 5, true);

        Mockito.when(repository.saveProduct(Mockito.any(Product.class)))
                .thenReturn(Mono.error(new Throwable(Integer.toString(
                        HttpURLConnection.HTTP_BAD_REQUEST))));

        var result = useCase.apply(product);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable != null &&
                        throwable.getMessage().equals(Integer.toString(
                                HttpURLConnection.HTTP_BAD_REQUEST)))
                .verify();

        Mockito.verify(repository, Mockito.times(1)).saveProduct(product);

    }
}