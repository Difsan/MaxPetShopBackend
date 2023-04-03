package co.com.maxpetshop.usecase.cart.deletecart;

import co.com.maxpetshop.model.cart.gateways.CartRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class DeleteCartUseCase implements Function<String, Mono<Void>> {

    private final CartRepository cartRepository;

    @Override
    public Mono<Void> apply(String cartId) {
        return cartRepository.deleteCart(cartId);
    }
}
