package co.com.maxpetshop.usecase.user.getuserbyemail;

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

@ExtendWith(MockitoExtension.class)
class GetUserByEmailUseCaseTest {

    @Mock
    UserRepository repository;

    GetuserbyEmailUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new GetuserbyEmailUseCase(repository);
    }

    @Test
    @DisplayName("GetUserByEmailUseCase_Success")
    void getUserByEmail() {
        var user = new User("1", "Diego", "Sanchez",
                "dif@gmail.com", "1235",
                new Cart("5", new HashSet<>(), 0.0));

        Mockito.when(repository.getUserByEmail("dif@gmail.com")).thenReturn(Mono.just(user));

        var result = useCase.apply("dif@gmail.com");

        StepVerifier.create(result)
                .expectNext(user)
                .expectComplete()
                .verify();

        Mockito.verify(repository, Mockito.times(1)).getUserByEmail("dif@gmail.com");
    }

    @Test
    @DisplayName("GetUserByEmailUseCase_Failed")
    void getUserByEmail_Failed() {
        Mockito.when(repository.getUserByEmail(Mockito.any(String.class)))
                .thenReturn(Mono.error(new Throwable(Integer.toString(
                        HttpURLConnection.HTTP_NOT_FOUND))));

        var result = useCase.apply("dif@gmail.com");

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable != null &&
                        throwable.getMessage().equals(Integer.toString(
                                HttpURLConnection.HTTP_NOT_FOUND)))
                .verify();

        Mockito.verify(repository, Mockito.times(1))
                .getUserByEmail("dif@gmail.com");
    }

}