package co.com.maxpetshop.usecase.user.getuserbyemail;

import co.com.maxpetshop.model.user.User;
import co.com.maxpetshop.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class GetUserByEmailUseCase implements Function<String, Mono<User>> {

    private final UserRepository userRepository;

    @Override
    public Mono<User> apply(String email) {
        return userRepository.getUserByEmail(email);
    }
}
