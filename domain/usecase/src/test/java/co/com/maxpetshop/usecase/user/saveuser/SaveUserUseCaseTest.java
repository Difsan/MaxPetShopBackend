package co.com.maxpetshop.usecase.user.saveuser;

import co.com.maxpetshop.model.cart.Cart;
import co.com.maxpetshop.model.item.Item;
import co.com.maxpetshop.model.user.User;
import co.com.maxpetshop.model.user.gateways.UserRepository;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SaveUserUseCaseTest {

    @Mock
    UserRepository repository;

    SaveUserUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new SaveUserUseCase(repository);
    }

    @Test
    @DisplayName("SaveUserUseCase_Success")
    void saveUser() {
        var user = new User("1", "Diego", "Sanchez",
                "dif@gmail.com", "1235",
                new Cart("5", new HashSet<>(), 0.0));

        Mockito.when(repository.saveUser(user)).thenReturn(Mono.just(user));

        var result = useCase.apply(user);

        StepVerifier.create(result)
                .expectNext(user)
                .expectComplete()
                .verify();

        Mockito.verify(repository, Mockito.times(1)).saveUser(user);
    }

    @Test
    @DisplayName("SaveUserUseCase_Failed")
    void saveUser_Failed() {
        var user = new User("1", "Diego", "Sanchez",
                "dif@gmail.com", "1235",
                new Cart("5", new HashSet<>(), 0.0));

        Mockito.when(repository.saveUser(Mockito.any(User.class)))
                .thenReturn(Mono.error(new Throwable(Integer.toString(
                        HttpURLConnection.HTTP_BAD_REQUEST))));

        var result = useCase.apply(user);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable != null &&
                        throwable.getMessage().equals(Integer.toString(
                                HttpURLConnection.HTTP_BAD_REQUEST)))
                .verify();

        Mockito.verify(repository, Mockito.times(1)).saveUser(user);
    }
}