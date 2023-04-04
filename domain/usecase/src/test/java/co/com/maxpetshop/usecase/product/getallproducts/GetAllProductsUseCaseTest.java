package co.com.maxpetshop.usecase.product.getallproducts;

import co.com.maxpetshop.model.product.Product;
import co.com.maxpetshop.model.product.gateways.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.net.HttpURLConnection;

@ExtendWith(MockitoExtension.class)
class GetAllProductsUseCaseTest {

    @Mock
    ProductRepository repository;

    GetAllProductsUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new GetAllProductsUseCase(repository);
    }

    @Test
    @DisplayName("getAllProductsUseCase_Success")
    void getAllProducts(){
        var fluxProducts = Flux.just(new Product("1", "Ringo Premium", "Ringo",
                "Food for adult dogs", "Image1", "Dog",
                "Food", 968.452, 5, true), new Product("2", "Ball",
                "Hasbro", "ball to dogs", "Image4", "Dog",
                "toy", 5.6, 6, true));

        Mockito.when(repository.getAllProducts()).thenReturn(fluxProducts);

        var result = useCase.get();

        StepVerifier.create(result)
                .expectNextCount(2)
                .verifyComplete();

        Mockito.verify(repository, Mockito.times(1)).getAllProducts();
    }

    @Test
    @DisplayName("getAllProductsUseCase_Failed")
    void getAllProducts_Failed(){
        Mockito.when(repository.getAllProducts())
                .thenReturn(Flux.error(new Throwable(Integer.toString(
                        HttpURLConnection.HTTP_NOT_FOUND))));

        var result = useCase.get();

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable != null &&
                        throwable.getMessage().equals(Integer.toString(
                                HttpURLConnection.HTTP_NOT_FOUND)))
                .verify();

        Mockito.verify(repository, Mockito.times(1)).getAllProducts();
    }
}