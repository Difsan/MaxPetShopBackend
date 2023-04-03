package co.com.maxpetshop.mongo;

import co.com.maxpetshop.model.cart.Cart;
import co.com.maxpetshop.model.cart.gateways.CartRepository;
import co.com.maxpetshop.model.item.Item;
import co.com.maxpetshop.model.user.User;
import co.com.maxpetshop.model.user.gateways.UserRepository;
import co.com.maxpetshop.mongo.data.CartData;
import co.com.maxpetshop.mongo.data.UserData;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class MongoRepositoryAdapterCart implements CartRepository
{
    private final MongoDBRepositoryCart repository;

    private final ObjectMapper mapper;

    @Override
    public Mono<Cart> getCartById(String cartId) {

        return this.repository
                .findById(cartId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("There is not " +
                        "cart with id: " + cartId)))
                .map(cartData -> mapper.map(cartData, Cart.class));
    }

    @Override
    public Mono<Cart> saveCart (Cart cart) {
        return this.repository
                .save(mapper.map(cart, CartData.class))
                .switchIfEmpty(Mono.empty())
                .map(cartData -> mapper.map(cartData, Cart.class));
    }

    @Override
    public Mono<Void> addItemToList(String cartId, Item item) {
        return null;
    }

    @Override
    public Mono<Void> removeItemFromList(String cartId, Item item) {
        return null;
    }


    @Override
    public Mono<Cart> updateCart (String cartId, Cart cart) {

        return this.repository
                .findById(cartId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("There is not " +
                        "cart with id: " + cartId)))
                .flatMap(cartData -> {
                    cart.setId(cartData.getId());
                    return repository.save(mapper.map(cart, CartData.class));
                })
                .map(cartData -> mapper.map(cartData, Cart.class));
    }

    @Override
    public Mono<Void> deleteCart(String cartId) {

        return this.repository
                .findById(cartId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("There is not " +
                        "cart with id: " + cartId)))
                .flatMap(cartData -> this.repository.deleteById(cartData.getId()));
    }
}
