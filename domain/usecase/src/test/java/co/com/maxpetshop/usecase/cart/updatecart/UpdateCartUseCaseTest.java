package co.com.maxpetshop.usecase.cart.updatecart;

import co.com.maxpetshop.model.cart.Cart;
import co.com.maxpetshop.model.cart.gateways.CartRepository;
import co.com.maxpetshop.model.item.Item;
import co.com.maxpetshop.model.product.Product;
import co.com.maxpetshop.usecase.cart.savecart.SaveCartUseCase;
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
class UpdateCartUseCaseTest {
    @Mock
    CartRepository repository;

    UpdateCartUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new UpdateCartUseCase(repository);
    }

    @Test
    @DisplayName("UpdateCartUseCase_Success")
    void updateCart() {

        var originalCart = new Cart("1", new HashSet<>(), 0.0);

        Set<Item> listItems = new HashSet<>();
        listItems.add(new Item("5", new Product("1", "Ringo Premium", "Ringo",
                "Food for adult dogs", "Image1", "Dog",
                "Food", 968.452, 5, true), 1, 968.452));

        var updatedCart = new Cart("1", listItems, 968.452);

        Mockito.when(repository.updateCart("1", updatedCart)).thenReturn(Mono.just(updatedCart));

        var result = useCase.apply("1", updatedCart);

        StepVerifier.create(result)
                .expectNext(updatedCart)
                .expectComplete()
                .verify();

        Mockito.verify(repository, Mockito.times(1)).updateCart("1",updatedCart);
    }

    @Test
    @DisplayName("UpdateCartUseCase_Failed")
    void updateCart_Failed() {

        var originalCart = new Cart("1", new HashSet<>(), 0.0);

        Set<Item> listItems = new HashSet<>();
        listItems.add(new Item("5", new Product("1", "Ringo Premium", "Ringo",
                "Food for adult dogs", "Image1", "Dog",
                "Food", 968.452, 5, true), 1, 968.452));

        var updatedCart = new Cart("1", listItems, 968.452);

        Mockito.when(repository.updateCart(Mockito.any(String.class), Mockito.any(Cart.class)))
                .thenReturn(Mono.error(new Throwable(Integer.toString(
                        HttpURLConnection.HTTP_BAD_REQUEST))));

        var result = useCase.apply("1", updatedCart);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable != null &&
                        throwable.getMessage().equals(Integer.toString(
                                HttpURLConnection.HTTP_BAD_REQUEST)))
                .verify();

        Mockito.verify(repository, Mockito.times(1)).updateCart("1", updatedCart);
    }
}