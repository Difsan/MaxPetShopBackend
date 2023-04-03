package co.com.maxpetshop.usecase.cart.updateitemslist;

import co.com.maxpetshop.model.cart.Cart;
import co.com.maxpetshop.model.cart.gateways.CartRepository;
import co.com.maxpetshop.model.item.Item;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@RequiredArgsConstructor
public class UpdateItemsListUseCase implements BiFunction<String, Item, Mono<Void>> {

    private final CartRepository cartRepository;

    @Override
    public Mono<Void> apply(String cartId, Item item) {
        return cartRepository.updateItemsList(cartId, item);
    }
}
