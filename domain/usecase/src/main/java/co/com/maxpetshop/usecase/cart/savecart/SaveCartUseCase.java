package co.com.maxpetshop.usecase.cart.savecart;

import co.com.maxpetshop.model.cart.User;
import co.com.maxpetshop.model.cart.gateways.CartRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class SaveCartUseCase implements Function<User, Mono<User>> {

    private final CartRepository cartRepository;

    @Override
    public Mono<User> apply(User cart) {
        return cartRepository.saveCart(cart);
    }
}
