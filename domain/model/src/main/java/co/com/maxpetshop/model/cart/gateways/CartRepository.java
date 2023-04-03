package co.com.maxpetshop.model.cart.gateways;

import co.com.maxpetshop.model.cart.User;
import co.com.maxpetshop.model.item.Item;
import reactor.core.publisher.Mono;

public interface CartRepository {

    Mono<User> getCartById(String cartId);

    Mono<User> saveCart(User cart);

    Mono<User> addItemToList(String cartId, Item item);
    Mono<User> removeItemFromList(String cartId, Item item);
    Mono<User> updateCart(String cartId, User cart);

    Mono<Void> deleteCart(String cartId);


}
