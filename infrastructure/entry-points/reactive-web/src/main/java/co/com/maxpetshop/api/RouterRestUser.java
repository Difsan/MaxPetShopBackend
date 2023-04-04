package co.com.maxpetshop.api;

import co.com.maxpetshop.model.product.Product;
import co.com.maxpetshop.model.user.User;
import co.com.maxpetshop.usecase.product.deleteproduct.DeleteProductUseCase;
import co.com.maxpetshop.usecase.product.getallproducts.GetAllProductsUseCase;
import co.com.maxpetshop.usecase.product.getproductbyanimaltype.GetProductByanimalTypeUseCase;
import co.com.maxpetshop.usecase.product.getproductbycategory.GetProductByCategoryUseCase;
import co.com.maxpetshop.usecase.product.getproductbyid.GetProductByIdUseCase;
import co.com.maxpetshop.usecase.product.getproductbyname.GetProductByNameUseCase;
import co.com.maxpetshop.usecase.product.saveproduct.SaveProductUseCase;
import co.com.maxpetshop.usecase.product.updateproduct.UpdateProductUseCase;
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
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRestUser {


    @Bean
    @RouterOperation(path = "/users/{userId}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = GetUserByIdUseCase.class,
            method = RequestMethod.GET,
            beanMethod = "apply",
            operation = @Operation(operationId = "getUserById", tags = "User usecases",
                    parameters = {@Parameter(name = "userId", description = "user Id", required= true, in = ParameterIn.PATH)},
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Success",
                                    content = @Content (schema = @Schema(implementation = User.class))),
                            @ApiResponse(responseCode = "404", description = "Not Found")
                    }))
    public RouterFunction<ServerResponse> getUserById (GetUserByIdUseCase getUserByIdUseCase){
        return route(GET("/users/{userId}"),
                request -> getUserByIdUseCase.apply(request.pathVariable("userId"))
                        .flatMap(product -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(product))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(throwable.getMessage()))
        );
    }

    //Doesn'twork yet
    @Bean
    @RouterOperation(path = "/users/byCartId/{cartId}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = GetUserByCartIdUseCase.class,
            method = RequestMethod.GET,
            beanMethod = "apply",
            operation = @Operation(operationId = "getUserByCartId", tags = "User usecases",
                    parameters = {@Parameter(name = "cartId", description = "product by cartId", required= true, in = ParameterIn.PATH)},
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Success",
                                    content = @Content (schema = @Schema(implementation = User.class))),
                            @ApiResponse(responseCode = "404", description = "Not Found")
                    }))
    public RouterFunction<ServerResponse> getUserByCartId (GetUserByCartIdUseCase getUserByCartIdUseCase){
        return route(GET("/users/byCartId/{cartId}"),
                request -> getUserByCartIdUseCase.apply(request.pathVariable("cartId"))
                        .flatMap(product -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(product))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(throwable.getMessage()))
        );
    }

    @Bean
    @RouterOperation(path = "/users", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = SaveUserUseCase.class, method = RequestMethod.POST,
            beanMethod = "apply",
            operation = @Operation(operationId = "saveUSer", tags = "User usecases",
                    parameters = {@Parameter(name = "user", in = ParameterIn.PATH,
                            schema = @Schema(implementation = User.class))},
                    responses = {
                            @ApiResponse(responseCode = "201", description = "Success",
                                    content = @Content (schema = @Schema(implementation = User.class))),
                            @ApiResponse(responseCode = "406", description = "Not acceptable, Try again")
                    }
                    ,
                    requestBody = @RequestBody(required = true, description = "Save an User following the schema",
                            content = @Content(schema = @Schema(implementation = User.class)))
                    ))
    public RouterFunction<ServerResponse> saveUser (SaveUserUseCase saveUserUseCase){
        return route(POST("/users").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(User.class)
                        .flatMap(user -> saveUserUseCase.apply(user)
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.BAD_REQUEST)
                                        .bodyValue(throwable.getMessage()))));
    }

    @Bean
    @RouterOperation(path = "/users/{userId}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = UpdateUserUseCase.class, method = RequestMethod.PUT,
            beanMethod = "apply",
            operation = @Operation(operationId = "updateUser", tags = "User usecases",
                    parameters = {@Parameter(name = "userId", description = "user Id", required= true, in = ParameterIn.PATH),
                            @Parameter(name = "user", in = ParameterIn.PATH,
                                    schema = @Schema(implementation = User.class))},
                    responses = {
                            @ApiResponse(responseCode = "201", description = "Success",
                                    content = @Content (schema = @Schema(implementation = User.class))),
                            @ApiResponse(responseCode = "406", description = "Not acceptable, Try again")
                    },
                    requestBody = @RequestBody(required = true, description = "Update an User following the schema",
                            content = @Content(schema = @Schema(implementation = User.class)))
                    ))
    public RouterFunction<ServerResponse> updateUser (UpdateUserUseCase updateUserUseCase){
        return route(PUT("/users/{userId}").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(User.class)
                        .flatMap(user -> updateUserUseCase.apply(request.pathVariable("userId"),
                                        user)
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.BAD_REQUEST)
                                        .bodyValue(throwable.getMessage()))
                        )
        );
    }

    @Bean
    @RouterOperation(path = "/users/{userId}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = DeleteUserUseCase.class, method = RequestMethod.DELETE,
            beanMethod = "apply",
            operation = @Operation(operationId = "deleteUserById", tags = "User usecases",
                    parameters = {@Parameter(name = "userId", description = "user Id", required= true, in = ParameterIn.PATH)},
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Success",
                                    content = @Content (schema = @Schema(implementation = User.class))),
                            @ApiResponse(responseCode = "404", description = "Not Found")
                    }))
    public RouterFunction<ServerResponse> deleteUser (DeleteUserUseCase deleteUserUseCase){
        return route(DELETE("/users/{userId}"),
                request -> deleteUserUseCase.apply(request.pathVariable("userId"))
                        .thenReturn(ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue("User deleted"))
                        .flatMap(serverResponseMono -> serverResponseMono)
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(throwable.getMessage()))
        );
    }

}
