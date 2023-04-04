package co.com.maxpetshop.api;

import co.com.maxpetshop.model.cart.Cart;
import co.com.maxpetshop.model.item.Item;
import co.com.maxpetshop.usecase.cart.additemtolist.AddItemToListUseCase;
import co.com.maxpetshop.usecase.cart.deletecart.DeleteCartUseCase;
import co.com.maxpetshop.usecase.cart.getcartbyid.GetCartByIdUseCase;
import co.com.maxpetshop.usecase.cart.removeitemfromlist.RemoveItemFromListUseCase;
import co.com.maxpetshop.usecase.cart.savecart.SaveCartUseCase;
import co.com.maxpetshop.usecase.cart.updatecart.UpdateCartUseCase;
import co.com.maxpetshop.usecase.item.deleteitem.DeleteItemUseCase;
import co.com.maxpetshop.usecase.item.getitembyid.GetItemByIdUseCase;
import co.com.maxpetshop.usecase.item.saveitem.SaveItemUseCase;
import co.com.maxpetshop.usecase.item.updateitem.UpdateItemUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRestCart {

    private WebClient itemAPI;

    public RouterRestCart() { itemAPI = WebClient.create("http://localhost:8080");}

    @Bean
    @RouterOperation(path = "/carts/{cartId}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = GetCartByIdUseCase.class,
            method = RequestMethod.GET,
            beanMethod = "apply",
            operation = @Operation(operationId = "getCartById", tags = "Cart usecases",
                    parameters = {@Parameter(name = "cartId", description = "cart Id", required= true, in = ParameterIn.PATH)},
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Success",
                                    content = @Content (schema = @Schema(implementation = Cart.class))),
                            @ApiResponse(responseCode = "404", description = "Not Found")
                    }))
    public RouterFunction<ServerResponse> getCartById (GetCartByIdUseCase getCartByIdUseCase){
        return route(GET("/carts/{cartId}"),
                request -> getCartByIdUseCase.apply(request.pathVariable("cartId"))
                        .flatMap(cart -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(cart))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(throwable.getMessage()))
        );
    }

    @Bean
    @RouterOperation(path = "/carts", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = SaveCartUseCase.class, method = RequestMethod.POST,
            beanMethod = "apply",
            operation = @Operation(operationId = "saveCart", tags = "Cart usecases",
                    responses = {
                            @ApiResponse(responseCode = "201", description = "Success",
                                    content = @Content (schema = @Schema(implementation = Cart.class))),
                            @ApiResponse(responseCode = "406", description = "Not acceptable, Try again")
                    }))
    public RouterFunction<ServerResponse> saveCart (SaveCartUseCase saveCartUseCase){
        return route(POST("/carts").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(Cart.class)
                        .flatMap(cart -> saveCartUseCase.apply(cart)
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.BAD_REQUEST)
                                        .bodyValue(throwable.getMessage()))));
    }

    @Bean
    @RouterOperation(path = "/carts/{cartId}/addItem/{I_Id}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = AddItemToListUseCase.class, method = RequestMethod.POST,
            beanMethod = "apply",
            operation = @Operation(operationId = "addItemToListInCart", tags = "Cart usecases",
                    parameters = {@Parameter(name = "cartId", description = "Cart Id", required= true, in = ParameterIn.PATH),
                            @Parameter(name = "I_Id", description = "Item Id", required= true, in = ParameterIn.PATH)},
                    responses = {
                            @ApiResponse(responseCode = "201", description = "Success",
                                    content = @Content (schema = @Schema(implementation = Cart.class))),
                            @ApiResponse(responseCode = "406", description = "Not acceptable, Try again")
                    }))
    public RouterFunction<ServerResponse> addItemToList (AddItemToListUseCase addItemToListUseCase){
        return route(POST("/carts/{cartId}/addItem/{I_Id}"),
                request -> itemAPI.get()
                        .uri("/items/"+request.pathVariable("I_Id"))
                        .retrieve()
                        .bodyToMono(Item.class)
                        .flatMap(item -> addItemToListUseCase
                                .apply(request.pathVariable("cartId"), item)
                                .flatMap(cart -> ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(cart))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.BAD_REQUEST)
                                        .bodyValue(throwable.getMessage())))
        );
    }

    @Bean
    @RouterOperation(path = "/carts/{cartId}/removeItem/{I_Id}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = RemoveItemFromListUseCase.class, method = RequestMethod.POST,
            beanMethod = "apply",
            operation = @Operation(operationId = "removeItemFromListInCart", tags = "Cart usecases",
                    parameters = {@Parameter(name = "cartId", description = "Cart Id", required= true, in = ParameterIn.PATH),
                            @Parameter(name = "I_Id", description = "Item Id", required= true, in = ParameterIn.PATH)},
                    responses = {
                            @ApiResponse(responseCode = "201", description = "Success",
                                    content = @Content (schema = @Schema(implementation = Cart.class))),
                            @ApiResponse(responseCode = "406", description = "Not acceptable, Try again")
                    }))
    public RouterFunction<ServerResponse> removeItemFromList (RemoveItemFromListUseCase removeItemFromListUseCase){
        return route(POST("/carts/{cartId}/removeItem/{I_Id}"),
                request -> itemAPI.get()
                        .uri("/items/"+request.pathVariable("I_Id"))
                        .retrieve()
                        .bodyToMono(Item.class)
                        .flatMap(item -> removeItemFromListUseCase
                                .apply(request.pathVariable("cartId"), item)
                                .flatMap(cart -> ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(cart))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.BAD_REQUEST)
                                        .bodyValue(throwable.getMessage())))
        );
    }

    @Bean
    @RouterOperation(path = "/carts/{cartId}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = UpdateCartUseCase.class, method = RequestMethod.PUT,
            beanMethod = "apply",
            operation = @Operation(operationId = "updateCart", tags = "Cart usecases",
                    parameters = {@Parameter(name = "cartId", description = "Cart Id", required= true, in = ParameterIn.PATH)},
                    responses = {
                            @ApiResponse(responseCode = "201", description = "Success",
                                    content = @Content (schema = @Schema(implementation = Cart.class))),
                            @ApiResponse(responseCode = "406", description = "Not acceptable, Try again")
                    }))
    public RouterFunction<ServerResponse> updateCart (UpdateCartUseCase updateCartUseCase){
        return route(PUT("/carts/{cartId}").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(Cart.class)
                        .flatMap(cart -> updateCartUseCase.apply(request.pathVariable("cartId"),
                                        cart)
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.BAD_REQUEST)
                                        .bodyValue(throwable.getMessage()))
                        )
        );
    }

    @Bean
    @RouterOperation(path = "/carts/{cartId}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = DeleteCartUseCase.class, method = RequestMethod.DELETE,
            beanMethod = "apply",
            operation = @Operation(operationId = "deleteCartById", tags = "Cart usecases",
                    parameters = {@Parameter(name = "cartId", description = "cart Id", required= true, in = ParameterIn.PATH)},
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Success",
                                    content = @Content (schema = @Schema(implementation = Cart.class))),
                            @ApiResponse(responseCode = "404", description = "Not Found")
                    }))
    public RouterFunction<ServerResponse> deleteCart (DeleteCartUseCase deleteCartUseCase){
        return route(DELETE("/carts/{cartId}"),
                request -> deleteCartUseCase.apply(request.pathVariable("cartId"))
                        .thenReturn(ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue("Cart deleted"))
                        .flatMap(serverResponseMono -> serverResponseMono)
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(throwable.getMessage()))
        );
    }

}
