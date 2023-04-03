package co.com.maxpetshop.usecase.user.getuserbycartid;

import co.com.maxpetshop.model.cart.User;
import co.com.maxpetshop.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class GetUserByCartIdUseCase implements Function<String, Mono<User>> {

    private final UserRepository userRepository;

    @Override
    public Mono<User> apply(String cartId) {
        return userRepository.getUserByCartId(cartId);
    }
}
