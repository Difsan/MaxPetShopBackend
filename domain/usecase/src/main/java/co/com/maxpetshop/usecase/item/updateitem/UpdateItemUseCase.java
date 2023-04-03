package co.com.maxpetshop.usecase.item.updateitem;

import co.com.maxpetshop.model.item.Item;
import co.com.maxpetshop.model.item.gateways.ItemRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@RequiredArgsConstructor
public class UpdateItemUseCase implements BiFunction<String, Item, Mono<Item>> {

    private final ItemRepository itemRepository;
    
    @Override
    public Mono<Item> apply(String itemId, Item item) {
        return itemRepository.updateItem(itemId, item);
    }
}
