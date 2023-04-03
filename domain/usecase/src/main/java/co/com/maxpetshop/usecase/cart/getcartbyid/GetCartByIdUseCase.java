package co.com.maxpetshop.usecase.cart.getcartbyid;

import co.com.maxpetshop.model.cart.Cart;
import co.com.maxpetshop.model.cart.gateways.CartRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class GetCartByIdUseCase implements Function<String, Mono<Cart>> {

    private final CartRepository cartRepository;

    @Override
    public Mono<Cart> apply(String cartId) {
        return cartRepository.getCartById(cartId);
    }
}
