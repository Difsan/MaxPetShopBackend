package co.com.maxpetshop.model.cart.gateways;

import co.com.maxpetshop.model.cart.Cart;
import co.com.maxpetshop.model.item.Item;
import reactor.core.publisher.Mono;

public interface CartRepository {

    Mono<Cart> getCartById(String cartId);

    Mono<Cart> saveCart(Cart cart);

    Mono<Void> updateItemsList(String cartId, Item item);
    Mono<Cart> updateCart(String cartId, Cart cart);

    Mono<Void> deleteCart(String cartId);


}
