package co.com.maxpetshop.usecase.cart.additemtolist;

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
class AddItemToListUseCaseTest {

    @Mock
    CartRepository repository;

    AddItemToListUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new AddItemToListUseCase(repository);
    }

    @Test
    @DisplayName("AddItemToListUseCase_Success")
    void addItemToList(){

        //Item to be added
        var item = new Item("5", new Product("1", "Ringo Premium", "Ringo",
                "Food for adult dogs", "Image1", "Dog",
                "Food", 968.452, 5, true), 1, 968.452);

        //Cart found by id
        Set<Item> listItems = new HashSet<>();
        var cart = new Cart("1", listItems, 0.0);

        // List of items to be updated
        var items = cart.getItems();
        items.add(item);

        //Cart Updated
        var updatedCart = new Cart("1", items, 968.452);

        Mockito.when(repository.addItemToList(Mockito.any(String.class), Mockito.any(Item.class)))
                .thenReturn(Mono.just(updatedCart));

        var result = useCase.apply("1", item);

        StepVerifier.create(result)
                .expectNext(updatedCart)
                .expectComplete()
                .verify();

        Mockito.verify(repository, Mockito.times(1)).addItemToList("1", item);
    }

    @Test
    @DisplayName("AddItemToListUseCase_Failed")
    void addItemToList_Failed(){
        //Item to be added
        var item = new Item("5", new Product("1", "Ringo Premium", "Ringo",
                "Food for adult dogs", "Image1", "Dog",
                "Food", 968.452, 5, true), 1, 968.452);

        String cartId = "1";

        Mockito.when(repository.addItemToList(Mockito.any(String.class), Mockito.any(Item.class)))
                .thenReturn(Mono.error(new Throwable(Integer.toString(
                        HttpURLConnection.HTTP_BAD_REQUEST))));

        var result = useCase.apply(cartId, item);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable != null &&
                        throwable.getMessage().equals(Integer.toString(
                                HttpURLConnection.HTTP_BAD_REQUEST)))
                .verify();

        Mockito.verify(repository, Mockito.times(1)).addItemToList(cartId, item);
    }

}