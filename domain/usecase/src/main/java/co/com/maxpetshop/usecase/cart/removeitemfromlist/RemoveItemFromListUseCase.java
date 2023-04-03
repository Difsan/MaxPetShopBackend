package co.com.maxpetshop.usecase.cart.removeitemfromlist;

import co.com.maxpetshop.model.cart.gateways.CartRepository;
import co.com.maxpetshop.model.item.Item;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@RequiredArgsConstructor
public class RemoveItemFromListUseCase implements BiFunction<String, Item, Mono<Void>> {

    private final CartRepository cartRepository;

    @Override
    public Mono<Void> apply(String s, Item item) {
        return cartRepository.removeItemFromList(s, item);
    }
}
