package co.com.maxpetshop.usecase.user.getuserbyid;

import co.com.maxpetshop.model.cart.Cart;
import co.com.maxpetshop.model.user.User;
import co.com.maxpetshop.model.user.gateways.UserRepository;
import co.com.maxpetshop.usecase.user.getuserbycartid.GetUserByCartIdUseCase;
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
class GetUserByIdUseCaseTest {
    @Mock
    UserRepository repository;

    GetUserByIdUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new GetUserByIdUseCase(repository);
    }

    @Test
    @DisplayName("GetUserByIdUseCase_Success")
    void getUserById() {
        var user = new User("1", "Diego", "Sanchez",
                "dif@gmail.com", "1235",
                new Cart("5", new HashSet<>(), 0.0));

        Mockito.when(repository.getUserById("1")).thenReturn(Mono.just(user));

        var result = useCase.apply("1");

        StepVerifier.create(result)
                .expectNext(user)
                .expectComplete()
                .verify();

        Mockito.verify(repository, Mockito.times(1)).getUserById("1");
    }

    @Test
    @DisplayName("GetUserByIdUseCase_Failed")
    void getUserById_Failed() {
        Mockito.when(repository.getUserById(Mockito.any(String.class)))
                .thenReturn(Mono.error(new Throwable(Integer.toString(
                        HttpURLConnection.HTTP_NOT_FOUND))));

        var result = useCase.apply("1");

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable != null &&
                        throwable.getMessage().equals(Integer.toString(
                                HttpURLConnection.HTTP_NOT_FOUND)))
                .verify();

        Mockito.verify(repository, Mockito.times(1))
                .getUserById("1");
    }
}