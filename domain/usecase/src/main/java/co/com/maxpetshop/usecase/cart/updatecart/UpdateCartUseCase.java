package co.com.maxpetshop.usecase.cart.updatecart;

import co.com.maxpetshop.model.cart.Cart;
import co.com.maxpetshop.model.cart.gateways.CartRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@RequiredArgsConstructor
public class UpdateCartUseCase implements BiFunction<String, Cart, Mono<Cart>> {

    private final CartRepository cartRepository;

    @Override
    public Mono<Cart> apply(String cartId, Cart cart) {
        return cartRepository.updateCart(cartId, cart);
    }
}
