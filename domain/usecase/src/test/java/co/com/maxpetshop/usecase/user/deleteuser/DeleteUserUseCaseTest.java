package co.com.maxpetshop.usecase.user.deleteuser;

import co.com.maxpetshop.model.cart.Cart;
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
class DeleteUserUseCaseTest {

    @Mock
    UserRepository repository;

    DeleteUserUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new DeleteUserUseCase(repository);
    }

    @Test
    @DisplayName("DeleteUserUseCase_Success")
    void deleteUser() {
        var user = new User("1", "Diego", "Sanchez",
                "dif@gmail.com", "1235",
                new Cart("5", new HashSet<>(), 0.0));

        Mockito.when(repository.deleteUser("1")).thenReturn(Mono.empty());

        var result = useCase.apply("1");

        StepVerifier.create(result)
                .expectNext()
                .expectComplete()
                .verify();

        Mockito.verify(repository, Mockito.times(1)).deleteUser("1");
    }

    @Test
    @DisplayName("DeleteUSerUseCase_Failed")
    void deleteItem_Failed() {
        Mockito.when(repository.deleteUser(Mockito.any(String.class)))
                .thenReturn(Mono.error(new Throwable(Integer.toString(HttpURLConnection.HTTP_NOT_FOUND))));

        var result = useCase.apply("1");

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable != null &&
                        throwable.getMessage().equals(Integer.toString(HttpURLConnection.HTTP_NOT_FOUND)))
                .verify();

        Mockito.verify(repository, Mockito.times(1)).deleteUser("1");
    }
}