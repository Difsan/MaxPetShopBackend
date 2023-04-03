package co.com.maxpetshop.usecase.getuserbyid;

import co.com.maxpetshop.model.user.User;
import co.com.maxpetshop.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class GetUserByIdUseCase implements Function<String, Mono<User>> {
    private final UserRepository userRepository;

    @Override
    public Mono<User> apply(String userId) {
        return userRepository.getUserById(userId);
    }
}
