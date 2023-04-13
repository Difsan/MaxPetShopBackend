package co.com.maxpetshop.usecase.receipt.getallreceiptsbyuserid;

import co.com.maxpetshop.model.cart.Cart;
import co.com.maxpetshop.model.item.Item;
import co.com.maxpetshop.model.product.Product;
import co.com.maxpetshop.model.receipt.Receipt;
import co.com.maxpetshop.model.receipt.gateways.ReceiptRepository;
import co.com.maxpetshop.model.user.User;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GetAllReceiptsByUserIdUseCaseTest {

    @Mock
    ReceiptRepository repository;

    GetAllReceiptsByUserIdUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new GetAllReceiptsByUserIdUseCase(repository);
    }

    @Test
    @DisplayName("GetAllReceiptsByUserIdUseCase_Success")
    void GetAllReceiptsByUserId() {

        var cart1 = new Cart("5", new HashSet<>(), 0.0);

        Set<Item> listItems = new HashSet<>();
        listItems.add(new Item("5", new Product("1", "Ringo Premium", "Ringo",
                "Food for adult dogs", "Image1", "Dog",
                "Food", 968.452, 5, true), 1, 968.452));

        var cart2 = new Cart("5", listItems, 0.0);

        var fluxReceipts = Flux.just(new Receipt("1", cart1, new Date(),
                new User("2", "Diego", "Sanchez",
                        "dif@gmail.com", "1235",
                        cart1),"35874125", "Street false 123"),
                new Receipt("3", cart2, new Date(),
                        new User("2", "Diego", "Sanchez",
                                "dif@gmail.com", "1235",
                                cart2),"35874125", "Street false 123"));

        Mockito.when(repository.getAllReceiptsByUserId("2")).thenReturn(fluxReceipts);

        var result = useCase.apply("2");

        StepVerifier.create(result)
                .expectNextCount(2)
                .verifyComplete();

        Mockito.verify(repository, Mockito.times(1)).getAllReceiptsByUserId("2");
    }

    @Test
    @DisplayName("GetAllReceiptsByUserIdUseCase_Failed")
    void GetAllReceiptsByUserId_Failed() {
        Mockito.when(repository.getAllReceiptsByUserId(Mockito.any(String.class)))
                .thenReturn(Flux.error(new Throwable(Integer.toString(
                        HttpURLConnection.HTTP_NOT_FOUND))));

        var result = useCase.apply("2");

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable != null &&
                        throwable.getMessage().equals(Integer.toString(
                                HttpURLConnection.HTTP_NOT_FOUND)))
                .verify();

        Mockito.verify(repository, Mockito.times(1)).getAllReceiptsByUserId("2");
    }
}