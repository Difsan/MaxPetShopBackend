package co.com.maxpetshop.usecase.receipt.savereceipt;

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
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.net.HttpURLConnection;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SaveReceiptUseCaseTest {

    @Mock
    ReceiptRepository repository;

    SaveReceiptUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new SaveReceiptUseCase(repository);
    }

    @Test
    @DisplayName("SaveReceiptUseCase_Success")
    void SaveReceipt() {
        Set<Item> listItems = new HashSet<>();
        listItems.add(new Item("5", new Product("1", "Ringo Premium", "Ringo",
                "Food for adult dogs", "Image1", "Dog",
                "Food", 968.452, 5, true), 1, 968.452));

        var cart = new Cart("5", listItems, 0.0);

        var receipt = new Receipt("3", cart, LocalDate.now(),
                new User("2", "Diego", "Sanchez",
                        "dif@gmail.com", "1235",
                        cart),"35874125", "Street false 123");

        Mockito.when(repository.saveReceipt(receipt)).thenReturn(Mono.just(receipt));

        var result = useCase.apply(receipt);

        StepVerifier.create(result)
                .expectNext(receipt)
                .expectComplete()
                .verify();

        Mockito.verify(repository, Mockito.times(1)).saveReceipt(receipt);
    }

    @Test
    @DisplayName("SaveReceiptUseCase_Failed")
    void SaveReceipt_Failed() {
        Set<Item> listItems = new HashSet<>();
        listItems.add(new Item("5", new Product("1", "Ringo Premium", "Ringo",
                "Food for adult dogs", "Image1", "Dog",
                "Food", 968.452, 5, true), 1, 968.452));

        var cart = new Cart("5", listItems, 0.0);

        var receipt = new Receipt("3", cart, LocalDate.now(),
                new User("2", "Diego", "Sanchez",
                        "dif@gmail.com", "1235",
                        cart),"35874125", "Street false 123");

        Mockito.when(repository.saveReceipt(Mockito.any(Receipt.class)))
                .thenReturn(Mono.error(new Throwable(Integer.toString(
                        HttpURLConnection.HTTP_BAD_REQUEST))));

        var result = useCase.apply(receipt);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable != null &&
                        throwable.getMessage().equals(Integer.toString(
                                HttpURLConnection.HTTP_BAD_REQUEST)))
                .verify();

        Mockito.verify(repository, Mockito.times(1)).saveReceipt(receipt);
    }
}