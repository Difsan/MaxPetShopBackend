package co.com.maxpetshop.usecase.product.deleteproduct;

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
class DeleteProductUseCaseTest {

    @Mock
    ProductRepository repository;

    DeleteProductUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new DeleteProductUseCase(repository);
    }

    @Test
    @DisplayName("DeleteProductUseCase_Success")
    void deleteProductById() {
        var product = new Product("1", "Ringo Premium", "Ringo",
                "Food for adult dogs", "Image1", "Dog",
                "Food", 968.452, 5, true);

        Mockito.when(repository.deleteProduct("1")).thenReturn(Mono.empty());

        var result = useCase.apply("1");

        StepVerifier.create(result)
                .expectNext()
                .expectComplete()
                .verify();

        Mockito.verify(repository, Mockito.times(1)).deleteProduct("1");
    }

    @Test
    @DisplayName("DeleteProductUseCase_Failed")
    void deleteProductById_Failed() {
        Mockito.when(repository.deleteProduct(Mockito.any(String.class)))
                .thenReturn(Mono.error(new Throwable(Integer.toString(HttpURLConnection.HTTP_NOT_FOUND))));

        var result = useCase.apply("1");

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable != null &&
                        throwable.getMessage().equals(Integer.toString(HttpURLConnection.HTTP_NOT_FOUND)))
                .verify();

        Mockito.verify(repository, Mockito.times(1)).deleteProduct("1");
    }
}