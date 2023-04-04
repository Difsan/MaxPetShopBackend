package co.com.maxpetshop.usecase.item.deleteitem;

import co.com.maxpetshop.model.item.Item;
import co.com.maxpetshop.model.item.gateways.ItemRepository;
import co.com.maxpetshop.model.product.Product;
import co.com.maxpetshop.model.product.gateways.ProductRepository;
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
class DeleteItemUseCaseTest {

    @Mock
    ItemRepository repository;

    DeleteItemUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new DeleteItemUseCase(repository);
    }

    @Test
    @DisplayName("DeleteItemUseCase_Success")
    void deleteItem() {
        var item = new Item("5", null, 6, 85.6);

        Mockito.when(repository.deleteItem("5")).thenReturn(Mono.empty());

        var result = useCase.apply("5");

        StepVerifier.create(result)
                .expectNext()
                .expectComplete()
                .verify();

        Mockito.verify(repository, Mockito.times(1)).deleteItem("5");
    }

    @Test
    @DisplayName("DeleteItemUseCase_Failed")
    void deleteItem_Failed() {
        Mockito.when(repository.deleteItem(Mockito.any(String.class)))
                .thenReturn(Mono.error(new Throwable(Integer.toString(HttpURLConnection.HTTP_NOT_FOUND))));

        var result = useCase.apply("5");

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable != null &&
                        throwable.getMessage().equals(Integer.toString(HttpURLConnection.HTTP_NOT_FOUND)))
                .verify();

        Mockito.verify(repository, Mockito.times(1)).deleteItem("5");
    }
}