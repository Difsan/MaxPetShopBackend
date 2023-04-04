package co.com.maxpetshop.usecase.item.updateitem;

import co.com.maxpetshop.model.item.Item;
import co.com.maxpetshop.model.item.gateways.ItemRepository;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UpdateItemUseCaseTest {

    @Mock
    ItemRepository repository;

    UpdateItemUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new UpdateItemUseCase(repository);
    }

    @Test
    @DisplayName("UpdateItemUseCase_Success")
    void UpdateItem() {
        var originalItem = new Item("5", null, 0, 0.0);

        var updatedItem = new Item("5", new Product("1", "Ringo Premium", "Ringo",
                "Food for adult dogs", "Image1", "Dog",
                "Food", 968.452, 5, true), 1, 968.452);

        Mockito.when(repository.updateItem("5", updatedItem))
                .thenReturn(Mono.just(updatedItem));

        var result = useCase.apply("5", updatedItem);

        StepVerifier.create(result)
                .expectNext(updatedItem)
                .expectComplete()
                .verify();

        Mockito.verify(repository, Mockito.times(1))
                .updateItem("5", updatedItem);
    }

    @Test
    @DisplayName("UpdateItemUseCase_Failed")
    void UpdateItem_Failed() {
        var originalItem = new Item("5", null, 0, 0.0);

        var updatedItem = new Item("5", new Product("1", "Ringo Premium", "Ringo",
                "Food for adult dogs", "Image1", "Dog",
                "Food", 968.452, 5, true), 1, 968.452);

        Mockito.when(repository.updateItem(Mockito.any(String.class), Mockito.any(Item.class)))
                .thenReturn(Mono.error(new Throwable(Integer.toString(
                        HttpURLConnection.HTTP_BAD_REQUEST))));

        var result = useCase.apply("5", updatedItem);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable != null &&
                        throwable.getMessage().equals(Integer.toString(
                                HttpURLConnection.HTTP_BAD_REQUEST)))
                .verify();

        Mockito.verify(repository, Mockito.times(1))
                .updateItem("5", updatedItem);
    }
}