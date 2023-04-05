package co.com.maxpetshop.usecase.cart.getcartbyid;

import co.com.maxpetshop.model.cart.Cart;
import co.com.maxpetshop.model.cart.gateways.CartRepository;
import co.com.maxpetshop.model.item.Item;
import co.com.maxpetshop.model.product.Product;
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
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GetCartByIdUseCaseTest {

    @Mock
    CartRepository repository;

    GetCartByIdUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new GetCartByIdUseCase(repository);
    }

    @Test
    @DisplayName("GetCartByIdUseCase_Success")
    void getCartById() {

        Set<Item> listItems = new HashSet<>();
        listItems.add(new Item("5", new Product("1", "Ringo Premium", "Ringo",
                "Food for adult dogs", "Image1", "Dog",
                "Food", 968.452, 5, true), 1, 968.452));

        var cart = new Cart("1", listItems, 968.452);

        Mockito.when(repository.getCartById("1")).thenReturn(Mono.just(cart));

        var result = useCase.apply("1");

        StepVerifier.create(result)
                .expectNext(cart)
                .expectComplete()
                .verify();

        Mockito.verify(repository, Mockito.times(1)).getCartById("1");
    }

    @Test
    @DisplayName("GetCartByIdUseCase_Failed")
    void getCartById_Failed() {
        Mockito.when(repository.getCartById(Mockito.any(String.class)))
                .thenReturn(Mono.error(new Throwable(Integer.toString(
                        HttpURLConnection.HTTP_NOT_FOUND))));

        var result = useCase.apply("1");

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable != null &&
                        throwable.getMessage().equals(Integer.toString(
                                HttpURLConnection.HTTP_NOT_FOUND)))
                .verify();

        Mockito.verify(repository, Mockito.times(1))
                .getCartById("1");
    }
}