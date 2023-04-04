package co.com.maxpetshop.usecase.item.getitembyid;

import co.com.maxpetshop.model.item.Item;
import co.com.maxpetshop.model.item.gateways.ItemRepository;
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
class GetItemByIdUseCaseTest {

    @Mock
    ItemRepository repository;

    GetItemByIdUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new GetItemByIdUseCase(repository);
    }

    @Test
    @DisplayName("GetItemByIdUseCase_Success")
    void getItemById() {
        var item = new Item("5", null, 6, 85.6);

        Mockito.when(repository.getItemById("5")).thenReturn(Mono.just(item));

        var result = useCase.apply("5");

        StepVerifier.create(result)
                .expectNext(item)
                .expectComplete()
                .verify();

        Mockito.verify(repository, Mockito.times(1)).getItemById("5");
    }

    @Test
    @DisplayName("GetItemByIdUseCase_Failed")
    void getItemById_Failed() {
        Mockito.when(repository.getItemById(Mockito.any(String.class)))
                .thenReturn(Mono.error(new Throwable(Integer.toString(
                        HttpURLConnection.HTTP_NOT_FOUND))));

        var result = useCase.apply("5");

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable != null &&
                        throwable.getMessage().equals(Integer.toString(
                                HttpURLConnection.HTTP_NOT_FOUND)))
                .verify();

        Mockito.verify(repository, Mockito.times(1))
                .getItemById("5");
    }
}