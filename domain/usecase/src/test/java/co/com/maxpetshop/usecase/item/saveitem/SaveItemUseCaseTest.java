package co.com.maxpetshop.usecase.item.saveitem;

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
class SaveItemUseCaseTest {

    @Mock
    ItemRepository repository;

    SaveItemUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new SaveItemUseCase(repository);
    }

    @Test
    @DisplayName("SaveItemUseCase_Success")
    void saveItem() {
        var item = new Item("5", new Product("1", "Ringo Premium", "Ringo",
                "Food for adult dogs", "Image1", "Dog",
                "Food", 968.452, 5, true), 1, 968.452);

        Mockito.when(repository.saveItem(item)).thenReturn(Mono.just(item));

        var result = useCase.apply(item);

        StepVerifier.create(result)
                .expectNext(item)
                .expectComplete()
                .verify();

        Mockito.verify(repository, Mockito.times(1)).saveItem(item);
    }

    @Test
    @DisplayName("SaveItemUseCase_Failed")
    void saveItem_Failed() {
        var item = new Item("5", new Product("1", "Ringo Premium", "Ringo",
                "Food for adult dogs", "Image1", "Dog",
                "Food", 968.452, 5, true), 1, 968.452);

        Mockito.when(repository.saveItem(Mockito.any(Item.class)))
                .thenReturn(Mono.error(new Throwable(Integer.toString(
                        HttpURLConnection.HTTP_BAD_REQUEST))));

        var result = useCase.apply(item);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable != null &&
                        throwable.getMessage().equals(Integer.toString(
                                HttpURLConnection.HTTP_BAD_REQUEST)))
                .verify();

        Mockito.verify(repository, Mockito.times(1)).saveItem(item);
    }
}