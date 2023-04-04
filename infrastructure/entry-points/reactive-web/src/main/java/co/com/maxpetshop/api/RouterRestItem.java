package co.com.maxpetshop.api;

import co.com.maxpetshop.model.item.Item;
import co.com.maxpetshop.model.product.Product;
import co.com.maxpetshop.model.user.User;
import co.com.maxpetshop.usecase.item.deleteitem.DeleteItemUseCase;
import co.com.maxpetshop.usecase.item.getitembyid.GetItemByIdUseCase;
import co.com.maxpetshop.usecase.item.saveitem.SaveItemUseCase;
import co.com.maxpetshop.usecase.item.updateitem.UpdateItemUseCase;
import co.com.maxpetshop.usecase.user.deleteuser.DeleteUserUseCase;
import co.com.maxpetshop.usecase.user.getuserbycartid.GetUserByCartIdUseCase;
import co.com.maxpetshop.usecase.user.getuserbyid.GetUserByIdUseCase;
import co.com.maxpetshop.usecase.user.saveuser.SaveUserUseCase;
import co.com.maxpetshop.usecase.user.updateuser.UpdateUserUseCase;
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
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRestItem {


    @Bean
    @RouterOperation(path = "/items/{itemId}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = GetItemByIdUseCase.class,
            method = RequestMethod.GET,
            beanMethod = "apply",
            operation = @Operation(operationId = "getItemById", tags = "Item usecases",
                    parameters = {@Parameter(name = "itemId", description = "item Id", required= true, in = ParameterIn.PATH)},
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Success",
                                    content = @Content (schema = @Schema(implementation = Item.class))),
                            @ApiResponse(responseCode = "404", description = "Not Found")
                    }))
    public RouterFunction<ServerResponse> getItemById (GetItemByIdUseCase getItemByIdUseCase){
        return route(GET("/items/{itemId}"),
                request -> getItemByIdUseCase.apply(request.pathVariable("itemId"))
                        .flatMap(product -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(product))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(throwable.getMessage()))
        );
    }

    @Bean
    @RouterOperation(path = "/items", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = SaveItemUseCase.class, method = RequestMethod.POST,
            beanMethod = "apply",
            operation = @Operation(operationId = "saveItem", tags = "Item usecases",
                    responses = {
                            @ApiResponse(responseCode = "201", description = "Success",
                                    content = @Content (schema = @Schema(implementation = Item.class))),
                            @ApiResponse(responseCode = "406", description = "Not acceptable, Try again")
                    }))
    public RouterFunction<ServerResponse> saveItem (SaveItemUseCase saveItemUseCase){
        return route(POST("/items").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(Item.class)
                        .flatMap(item -> saveItemUseCase.apply(item)
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.BAD_REQUEST)
                                        .bodyValue(throwable.getMessage()))));
    }

    @Bean
    @RouterOperation(path = "/items/{itemId}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = UpdateItemUseCase.class, method = RequestMethod.PUT,
            beanMethod = "apply",
            operation = @Operation(operationId = "updateItem", tags = "Item usecases",
                    parameters = {@Parameter(name = "itemId", description = "item Id", required= true, in = ParameterIn.PATH)},
                    responses = {
                            @ApiResponse(responseCode = "201", description = "Success",
                                    content = @Content (schema = @Schema(implementation = Item.class))),
                            @ApiResponse(responseCode = "406", description = "Not acceptable, Try again")
                    }))
    public RouterFunction<ServerResponse> updateItem (UpdateItemUseCase updateItemUseCase){
        return route(PUT("/items/{itemId}").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(Item.class)
                        .flatMap(item -> updateItemUseCase.apply(request.pathVariable("itemId"),
                                        item)
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.BAD_REQUEST)
                                        .bodyValue(throwable.getMessage()))
                        )
        );
    }

    @Bean
    @RouterOperation(path = "/items/{itemId}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = DeleteItemUseCase.class, method = RequestMethod.DELETE,
            beanMethod = "apply",
            operation = @Operation(operationId = "deleteItemById", tags = "Item usecases",
                    parameters = {@Parameter(name = "itemId", description = "item Id", required= true, in = ParameterIn.PATH)},
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Success",
                                    content = @Content (schema = @Schema(implementation = Item.class))),
                            @ApiResponse(responseCode = "404", description = "Not Found")
                    }))
    public RouterFunction<ServerResponse> deleteItem (DeleteItemUseCase deleteItemUseCase){
        return route(DELETE("/items/{itemId}"),
                request -> deleteItemUseCase.apply(request.pathVariable("itemId"))
                        .thenReturn(ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue("Item deleted"))
                        .flatMap(serverResponseMono -> serverResponseMono)
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(throwable.getMessage()))
        );
    }

}
