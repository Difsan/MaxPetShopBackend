package co.com.maxpetshop.usecase.receipt.updatereceipt;

import co.com.maxpetshop.model.cart.Cart;
import co.com.maxpetshop.model.item.Item;
import co.com.maxpetshop.model.product.Product;
import co.com.maxpetshop.model.receipt.Receipt;
import co.com.maxpetshop.model.receipt.gateways.ReceiptRepository;
import co.com.maxpetshop.model.user.User;
import co.com.maxpetshop.usecase.receipt.savereceipt.SaveReceiptUseCase;
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
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UpdateReceiptUseCaseTest {

    @Mock
    ReceiptRepository repository;

    UpdateReceiptUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new UpdateReceiptUseCase(repository);
    }

    @Test
    @DisplayName("UpdateReceiptUseCase_Success")
    void updateReceipt() {
        var cart1 = new Cart("5", new HashSet<>(), 0.0);

        var originalReceipt = new Receipt("1", cart1, new Date(),
                new User("2", "Diego", "Sanchez",
                        "dif@gmail.com", "1235",
                        cart1),"35874125", "Street false 123");

        Set<Item> listItems = new HashSet<>();
        listItems.add(new Item("5", new Product("1", "Ringo Premium", "Ringo",
                "Food for adult dogs", "Image1", "Dog",
                "Food", 968.452, 5, true), 1, 968.452));

        var cart = new Cart("5", listItems, 0.0);

        var updatedReceipt = new Receipt("1", cart, new Date(),
                new User("2", "Diego", "Sanchez",
                        "dif@gmail.com", "1235",
                        cart),"35874125", "Street false 123");

        Mockito.when(repository.updateReceipt("1", updatedReceipt)).thenReturn(Mono.just(updatedReceipt));

        var result = useCase.apply("1", updatedReceipt);

        StepVerifier.create(result)
                .expectNext(updatedReceipt)
                .expectComplete()
                .verify();

        Mockito.verify(repository, Mockito.times(1))
                .updateReceipt("1", updatedReceipt);
    }

    @Test
    @DisplayName("UpdateReceiptUseCase_Failed")
    void updateReceipt_Failed() {
        var cart1 = new Cart("5", new HashSet<>(), 0.0);

        var originalReceipt = new Receipt("1", cart1, new Date(),
                new User("2", "Diego", "Sanchez",
                        "dif@gmail.com", "1235",
                        cart1),"35874125", "Street false 123");

        Set<Item> listItems = new HashSet<>();
        listItems.add(new Item("5", new Product("1", "Ringo Premium", "Ringo",
                "Food for adult dogs", "Image1", "Dog",
                "Food", 968.452, 5, true), 1, 968.452));

        var cart = new Cart("5", listItems, 0.0);

        var updatedReceipt = new Receipt("1", cart, new Date(),
                new User("2", "Diego", "Sanchez",
                        "dif@gmail.com", "1235",
                        cart),"35874125", "Street false 123");

        Mockito.when(repository.updateReceipt(Mockito.any(String.class),Mockito.any(Receipt.class)))
                .thenReturn(Mono.error(new Throwable(Integer.toString(
                        HttpURLConnection.HTTP_BAD_REQUEST))));

        var result = useCase.apply("1", updatedReceipt);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable != null &&
                        throwable.getMessage().equals(Integer.toString(
                                HttpURLConnection.HTTP_BAD_REQUEST)))
                .verify();

        Mockito.verify(repository, Mockito.times(1)).updateReceipt("1", updatedReceipt);
    }
}