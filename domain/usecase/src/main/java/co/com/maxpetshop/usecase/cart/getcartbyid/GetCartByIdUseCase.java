package co.com.maxpetshop.usecase.cart.getcartbyid;

import co.com.maxpetshop.model.cart.User;
import co.com.maxpetshop.model.cart.gateways.CartRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class GetCartByIdUseCase implements Function<String, Mono<User>> {

    private final CartRepository cartRepository;

    @Override
    public Mono<User> apply(String cartId) {
        return cartRepository.getCartById(cartId);
    }
}
