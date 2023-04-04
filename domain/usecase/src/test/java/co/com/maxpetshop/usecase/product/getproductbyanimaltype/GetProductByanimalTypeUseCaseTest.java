package co.com.maxpetshop.usecase.product.getproductbyanimaltype;

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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GetProductByanimalTypeUseCaseTest {

    @Mock
    ProductRepository repository;

    GetProductByanimalTypeUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new GetProductByanimalTypeUseCase(repository);
    }

    @Test
    @DisplayName("GetProductByAnimalTypeUseCase_Success")
    void GetProductByAnimal() {
        var fluxProducts = Flux.just(new Product("1", "Ringo Premium", "Ringo",
                "Food for adult dogs", "Image1", "Dog",
                "Food", 968.452, 5, true), new Product("2",
                "Ringo No Premium","Ringo", "Food for adult dogs", "Image4",
                "Dog","Food", 100.0, 7, true));

        Mockito.when(repository.getProductsByAnimalType("Dog")).thenReturn(fluxProducts);

        var result = useCase.apply("Dog");

        StepVerifier.create(result)
                .expectNextCount(2)
                .verifyComplete();

        Mockito.verify(repository, Mockito.times(1)).getProductsByAnimalType("Dog");
    }

    @Test
    @DisplayName("GetProductByAnimalTypeUseCase_Failed")
    void GetProductByAnimal_Failed() {
        Mockito.when(repository.getProductsByAnimalType(Mockito.any(String.class)))
                .thenReturn(Flux.error(new Throwable(Integer.toString(
                        HttpURLConnection.HTTP_NOT_FOUND))));

        var result = useCase.apply("Dog");

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable != null &&
                        throwable.getMessage().equals(Integer.toString(
                                HttpURLConnection.HTTP_NOT_FOUND)))
                .verify();

        Mockito.verify(repository, Mockito.times(1)).getProductsByAnimalType("Dog");
    }
}