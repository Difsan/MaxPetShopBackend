package co.com.maxpetshop.usecase.user.getusercartbyid;

import co.com.maxpetshop.model.cart.Cart;
import co.com.maxpetshop.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class GetUserCartByIdUseCase implements Function<String, Mono<Cart>> {

    private final UserRepository userRepository;

    @Override
    public Mono<Cart> apply(String cartId) {
        return userRepository.getUserCartById(cartId);
    }
}
