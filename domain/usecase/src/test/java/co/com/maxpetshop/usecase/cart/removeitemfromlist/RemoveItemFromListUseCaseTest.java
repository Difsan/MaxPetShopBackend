package co.com.maxpetshop.usecase.cart.removeitemfromlist;

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
class RemoveItemFromListUseCaseTest {

    @Mock
    CartRepository repository;

    RemoveItemFromListUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new RemoveItemFromListUseCase(repository);
    }

    @Test
    @DisplayName("RemoveItemFromListUseCas_Success")
    void removeItemFromList(){

        // items already added in cart
        var item1 = new Item("5", new Product("1", "Ringo Premium", "Ringo",
                "Food for adult dogs", "Image1", "Dog",
                "Food", 968.452, 5, true), 1, 968.452);

        var item2 = new Item("6", new Product("2", "Ball",
                "Hasbro", "ball to dogs", "Image4", "Dog",
                "toy", 5.6, 6, true), 2, 11.2);

        // cart found by Id
        Set<Item> listItems = new HashSet<>();
        var cart = new Cart("1", listItems, 0.0);

        // add the items to the customer, that's how we get the cart from findbyId
        var items = cart.getItems();
        items.add(item1);
        items.add(item2);
        cart.setItems(items);

        // item to be removed
        var removedItem = new Item("6", new Product("2", "Ball",
                "Hasbro", "ball to dogs", "Image4", "Dog",
                "toy", 5.6, 6, true), 2, 11.2);


        // remover item from cart
        var cartUpdated = cart;
        var itemsChanged = cartUpdated.getItems();
        itemsChanged.remove(removedItem);
        cartUpdated.setItems(itemsChanged);

        Mockito.when(repository.removeItemFromList("1", removedItem))
                .thenReturn(Mono.just(cartUpdated));

        var result = useCase.apply("1", removedItem);

        StepVerifier.create(result)
                .expectNext(cartUpdated)
                .expectComplete()
                .verify();

        Mockito.verify(repository, Mockito.times(1))
                .removeItemFromList("1",removedItem);
    }

    @Test
    @DisplayName("RemoveItemFromListUseCas_Failed")
    void removeItemFromList_Failed(){
        var removedItem = new Item("6", new Product("2", "Ball",
                "Hasbro", "ball to dogs", "Image4", "Dog",
                "toy", 5.6, 6, true), 2, 11.2);

        String cartId = "1";

        Mockito.when(repository.removeItemFromList(Mockito.any(String.class), Mockito.any(Item.class)))
                .thenReturn(Mono.error(new Throwable(Integer.toString(
                        HttpURLConnection.HTTP_BAD_REQUEST))));

        var result = useCase.apply(cartId, removedItem);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable != null &&
                        throwable.getMessage().equals(Integer.toString(
                                HttpURLConnection.HTTP_BAD_REQUEST)))
                .verify();

        Mockito.verify(repository, Mockito.times(1)).removeItemFromList(cartId, removedItem);

    }
}