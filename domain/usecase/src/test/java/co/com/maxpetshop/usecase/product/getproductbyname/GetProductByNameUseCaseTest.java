package co.com.maxpetshop.usecase.product.getproductbyname;

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
class GetProductByNameUseCaseTest {

    @Mock
    ProductRepository repository;

    GetProductByNameUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new GetProductByNameUseCase(repository);
    }

    @Test
    @DisplayName("GetProductByNameUseCase_Success")
    void getProductByName() {
        var fluxProducts = Flux.just(new Product("1", "Ringo Premium", "Ringo",
                "Food for adult dogs", "Image1", "Dog",
                "Food", 968.452, 5, true), new Product("2", "Ringo No Premium",
                "Ringo", "Food for adult dogs", "Image4", "Dog",
                "Food", 100.0, 7, true));

        Mockito.when(repository.getProductsByName("Ringo Premium")).thenReturn(Flux.concat(fluxProducts.elementAt(0)));

        var result = useCase.apply("Ringo Premium");

        StepVerifier.create(result)
                .expectNext(fluxProducts.elementAt(0).block())
                .expectComplete()
                .verify();

        Mockito.verify(repository, Mockito.times(1)).getProductsByName("Ringo Premium");
    }

    @Test
    @DisplayName("GetProductByNameUseCase_Failed")
    void getProductByName_Failed() {
        Mockito.when(repository.getProductsByName(Mockito.any(String.class)))
                .thenReturn(Flux.error(new Throwable(Integer.toString(
                        HttpURLConnection.HTTP_NOT_FOUND))));

        var result = useCase.apply("Ringo Premium");

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable != null &&
                        throwable.getMessage().equals(Integer.toString(
                                HttpURLConnection.HTTP_NOT_FOUND)))
                .verify();

        Mockito.verify(repository, Mockito.times(1)).getProductsByName("Ringo Premium");

    }
}