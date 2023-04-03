package co.com.maxpetshop.usecase.cart.updatecart;

import co.com.maxpetshop.model.cart.User;
import co.com.maxpetshop.model.cart.gateways.CartRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@RequiredArgsConstructor
public class UpdateCartUseCase implements BiFunction<String, User, Mono<User>> {

    private final CartRepository cartRepository;

    @Override
    public Mono<User> apply(String cartId, User cart) {
        return cartRepository.updateCart(cartId, cart);
    }
}
