package co.com.maxpetshop.mongo;

import co.com.maxpetshop.model.cart.Cart;
import co.com.maxpetshop.model.cart.gateways.CartRepository;
import co.com.maxpetshop.model.item.Item;
import co.com.maxpetshop.mongo.data.CartData;
import co.com.maxpetshop.mongo.data.ItemData;
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
    public Mono<Cart> addItemToList(String cartId, Item item) {
        return this.repository
                .findById(cartId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("There is not " +
                        "cart with id: " + cartId)))
                .flatMap(cartData -> {
                    var listOfItems = cartData.getItems();
                    boolean itemAlreadyExists = listOfItems.stream()
                            .anyMatch(cartItem -> cartItem.getProduct().getId().equals(item.getProduct().getId()));
                    if (itemAlreadyExists) {
                        return Mono.error(new IllegalArgumentException("An Item with product: " +
                                item.getProduct().getName() + " already exists in the cart"));
                    }
                    listOfItems.add(mapper.map(item, ItemData.class));
                    cartData.setItems(listOfItems);
                    cartData.setTotalPrice(
                            listOfItems.stream().mapToDouble(itemData -> itemData.getSubTotal()).sum());
                    return this.repository.save(cartData);
                })
                .map(cartData -> mapper.map(cartData, Cart.class));
    }

    @Override
    public Mono<Cart> removeItemFromList(String cartId, Item item) {
        return this.repository
                .findById(cartId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("There is not " +
                        "cart with id: " + cartId)))
                .flatMap(cartData -> {
                    var listOfItems = cartData.getItems();
                    boolean itemRemoved = listOfItems
                            .stream()
                            .filter(itemData -> itemData.getId().equals(item.getId()))
                            .findFirst()
                            .map(itemData -> listOfItems.remove(itemData))
                            .orElse(false);
                    if (!itemRemoved) {
                        return Mono.error(new IllegalArgumentException("Item not found in cart list"));
                    }
                    cartData.setItems(listOfItems);
                    cartData.setTotalPrice(
                            listOfItems.stream().mapToDouble(itemData -> itemData.getSubTotal()).sum());
                    return this.repository.save(cartData);
                })
                .map(cartData -> mapper.map(cartData, Cart.class));
    }

    @Override
    public Mono<Cart> updateCart (String cartId, Cart cart) {

        return this.repository
                .findById(cartId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("There is not " +
                        "cart with id: " + cartId)))
                .flatMap(cartData -> {
                    cart.setId(cartData.getId());
                    //var newTotal = cart.getTotalPrice();
                    cart.getItems().stream().forEach(item -> cart.setTotalPrice(cart.getTotalPrice()+item.getSubTotal()));
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
