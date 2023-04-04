package co.com.maxpetshop.usecase.product.updateproduct;

import co.com.maxpetshop.model.product.Product;
import co.com.maxpetshop.model.product.gateways.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.net.HttpURLConnection;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UpdateProductUseCaseTest {

    @Mock
    ProductRepository repository;

    UpdateProductUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new UpdateProductUseCase(repository);
    }

    @Test
    @DisplayName("UpdateProductUseCase_Success")
    void UpdateProduct() {
        var originalProduct = new Product("1", "Ringo Premium", "Ringo",
                "Food for adult dogs", "Image1", "Dog",
                "Food", 968.452, 5, true);

        var updatedProduct = new Product("1", "Ringo Premium", "Ringo",
                "Food for adult dogs", "Image5", "Dog",
                "Food", 500.56, 10, true);

        Mockito.when(repository.updateProduct("1", updatedProduct))
                .thenReturn(Mono.just(updatedProduct));

        var result = useCase.apply("1", updatedProduct);

        StepVerifier.create(result)
                .expectNext(updatedProduct)
                .expectComplete()
                .verify();

        Mockito.verify(repository, Mockito.times(1))
                .updateProduct("1",updatedProduct);
    }

    @Test
    @DisplayName("UpdateProductUseCase_Failed")
    void UpdateProduct_Failed() {
        var originalProduct = new Product("1", "Ringo Premium", "Ringo",
                "Food for adult dogs", "Image1", "Dog",
                "Food", 968.452, 5, true);

        var updatedProduct = new Product("1", "Ringo Premium", "Ringo",
                "Food for adult dogs", "Image5", "Dog",
                "Food", 500.56, 10, true);

        Mockito.when(repository.updateProduct(Mockito.any(String.class), Mockito.any(Product.class)))
                .thenReturn(Mono.error(new Throwable(Integer.toString(
                        HttpURLConnection.HTTP_BAD_REQUEST))));

        var result = useCase.apply("1", updatedProduct);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable != null &&
                        throwable.getMessage().equals(Integer.toString(
                                HttpURLConnection.HTTP_BAD_REQUEST)))
                .verify();

        Mockito.verify(repository, Mockito.times(1))
                .updateProduct("1", updatedProduct);
    }
}