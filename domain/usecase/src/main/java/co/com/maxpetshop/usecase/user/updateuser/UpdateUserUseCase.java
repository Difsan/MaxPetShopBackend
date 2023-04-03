package co.com.maxpetshop.usecase.user.updateuser;

import co.com.maxpetshop.model.user.User;
import co.com.maxpetshop.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@RequiredArgsConstructor
public class UpdateUserUseCase implements BiFunction<String, User, Mono<User>> {

    private final UserRepository userRepository;

    @Override
    public Mono<User> apply(String userId, User user) {
        return userRepository.updateUser(userId, user);
    }
}
