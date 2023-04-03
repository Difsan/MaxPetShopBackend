package co.com.maxpetshop.mongo;

import co.com.maxpetshop.model.cart.User;
import co.com.maxpetshop.model.cart.gateways.CartRepository;
import co.com.maxpetshop.model.item.Item;
import co.com.maxpetshop.mongo.data.CartData;
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
    public Mono<User> getCartById(String cartId) {

        return this.repository
                .findById(cartId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("There is not " +
                        "cart with id: " + cartId)))
                .map(cartData -> mapper.map(cartData, User.class));
    }

    @Override
    public Mono<User> saveCart (User cart) {
        return this.repository
                .save(mapper.map(cart, CartData.class))
                .switchIfEmpty(Mono.empty())
                .map(cartData -> mapper.map(cartData, User.class));
    }

    @Override
    public Mono<User> addItemToList(String cartId, Item item) {
        return this.repository
                .findById(cartId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("There is not " +
                        "cart with id: " + cartId)))
                .flatMap(cartData -> {
                    var listOfItems = cartData.getItems();
                    listOfItems.add(item);
                    cartData.setItems(listOfItems);
                    return this.repository.save(cartData);
                })
                .map(cartData -> mapper.map(cartData, User.class));
    }

    @Override
    public Mono<User> removeItemFromList(String cartId, Item item) {
        return this.repository
                .findById(cartId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("There is not " +
                        "cart with id: " + cartId)))
                .flatMap(cartData -> {
                    var listOfItems = cartData.getItems();
                    listOfItems.stream().forEach(item1 -> {
                        if (item1.getId().equals(item.getId())) listOfItems.remove(item1);
                    });
                    cartData.setItems(listOfItems);
                    return this.repository.save(cartData);
                })
                .map(cartData -> mapper.map(cartData, User.class));
    }


    @Override
    public Mono<User> updateCart (String cartId, User cart) {

        return this.repository
                .findById(cartId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("There is not " +
                        "cart with id: " + cartId)))
                .flatMap(cartData -> {
                    cart.setId(cartData.getId());
                    return repository.save(mapper.map(cart, CartData.class));
                })
                .map(cartData -> mapper.map(cartData, User.class));
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
