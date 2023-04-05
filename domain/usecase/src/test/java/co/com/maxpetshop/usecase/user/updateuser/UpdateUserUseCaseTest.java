package co.com.maxpetshop.usecase.user.updateuser;

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
class UpdateUserUseCaseTest {

    @Mock
    UserRepository repository;

    UpdateUserUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new UpdateUserUseCase(repository);
    }

    @Test
    @DisplayName("UpdateUserUseCase_Success")
    void UpdateUser() {
        var originalUser = new User("1", "Diego", "Sanchez",
                "dif@gmail.com", "1235",null);

        var updatedUser = new User("1", "Diego", "Fernandez",
                "dif@gmail.com", "78910",
                new Cart("5", new HashSet<>(), 0.0));

        Mockito.when(repository.updateUser("1", updatedUser))
                .thenReturn(Mono.just(updatedUser));

        var result = useCase.apply("1", updatedUser);

        StepVerifier.create(result)
                .expectNext(updatedUser)
                .expectComplete()
                .verify();

        Mockito.verify(repository, Mockito.times(1))
                .updateUser("1", updatedUser);
    }

    @Test
    @DisplayName("UpdateUserUseCase_Failed")
    void UpdateUser_Failed() {
        var originalUser = new User("1", "Diego", "Sanchez",
                "dif@gmail.com", "1235",null);

        var updatedUser = new User("1", "Diego", "Fernandez",
                "dif@gmail.com", "78910",
                new Cart("5", new HashSet<>(), 0.0));

        Mockito.when(repository.updateUser(Mockito.any(String.class), Mockito.any(User.class)))
                .thenReturn(Mono.error(new Throwable(Integer.toString(
                        HttpURLConnection.HTTP_BAD_REQUEST))));

        var result = useCase.apply("1", updatedUser);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable != null &&
                        throwable.getMessage().equals(Integer.toString(
                                HttpURLConnection.HTTP_BAD_REQUEST)))
                .verify();

        Mockito.verify(repository, Mockito.times(1))
                .updateUser("1", updatedUser);
    }
}