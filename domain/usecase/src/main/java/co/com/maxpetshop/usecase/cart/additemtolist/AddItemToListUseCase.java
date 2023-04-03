package co.com.maxpetshop.usecase.cart.additemtolist;

import co.com.maxpetshop.model.cart.Cart;
import co.com.maxpetshop.model.cart.gateways.CartRepository;
import co.com.maxpetshop.model.item.Item;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@RequiredArgsConstructor
public class AddItemToListUseCase implements BiFunction<String, Item, Mono<Cart>> {

    private final CartRepository cartRepository;

    @Override
    public Mono<Cart> apply(String cartId, Item item) {
        return cartRepository.addItemToList(cartId, item);
    }
}
