package co.com.maxpetshop.usecase.cart.savecart;

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
class SaveCartUseCaseTest {

    @Mock
    CartRepository repository;

    SaveCartUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new SaveCartUseCase(repository);
    }

    @Test
    @DisplayName("SaveCartUseCase_Success")
    void saveCart() {
        Set<Item> listItems = new HashSet<>();
        listItems.add(new Item("5", new Product("1", "Ringo Premium", "Ringo",
                "Food for adult dogs", "Image1", "Dog",
                "Food", 968.452, 5, true), 1, 968.452));

        var cart = new Cart("1", listItems, 968.452);

        Mockito.when(repository.saveCart(cart)).thenReturn(Mono.just(cart));

        var result = useCase.apply(cart);

        StepVerifier.create(result)
                .expectNext(cart)
                .expectComplete()
                .verify();

        Mockito.verify(repository, Mockito.times(1)).saveCart(cart);
    }

    @Test
    @DisplayName("SaveCartUseCase_Failed")
    void saveCart_Failed() {
        Set<Item> listItems = new HashSet<>();
        listItems.add(new Item("5", new Product("1", "Ringo Premium", "Ringo",
                "Food for adult dogs", "Image1", "Dog",
                "Food", 968.452, 5, true), 1, 968.452));

        var cart = new Cart("1", listItems, 968.452);

        Mockito.when(repository.saveCart(Mockito.any(Cart.class)))
                .thenReturn(Mono.error(new Throwable(Integer.toString(
                        HttpURLConnection.HTTP_BAD_REQUEST))));

        var result = useCase.apply(cart);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable != null &&
                        throwable.getMessage().equals(Integer.toString(
                                HttpURLConnection.HTTP_BAD_REQUEST)))
                .verify();

        Mockito.verify(repository, Mockito.times(1)).saveCart(cart);
    }
}