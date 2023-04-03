package co.com.maxpetshop.usecase.user.deleteuser;

import co.com.maxpetshop.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class DeleteUserUseCase implements Function<String, Mono<Void>> {

    private final UserRepository userRepository;

    @Override
    public Mono<Void> apply(String userId) {
        return userRepository.deleteUser(userId);
    }
}
