package co.com.maxpetshop.api;

import co.com.maxpetshop.model.product.Product;
import co.com.maxpetshop.usecase.product.deleteproduct.DeleteProductUseCase;
import co.com.maxpetshop.usecase.product.getallproducts.GetAllProductsUseCase;
import co.com.maxpetshop.usecase.product.getproductbyanimaltype.GetProductByanimalTypeUseCase;
import co.com.maxpetshop.usecase.product.getproductbycategory.GetProductByCategoryUseCase;
import co.com.maxpetshop.usecase.product.getproductbyid.GetProductByIdUseCase;
import co.com.maxpetshop.usecase.product.getproductbyname.GetProductByNameUseCase;
import co.com.maxpetshop.usecase.product.saveproduct.SaveProductUseCase;
import co.com.maxpetshop.usecase.product.updateproduct.UpdateProductUseCase;
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
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRestProduct {
    @Bean
    @RouterOperation(path = "/products", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = GetAllProductsUseCase.class, method = RequestMethod.GET,
            beanMethod = "get",
            operation = @Operation(operationId = "getAllProducts", tags = "Products usecases",
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Success",
                                    content = @Content(schema = @Schema(implementation = Product.class))),
                            @ApiResponse(responseCode = "204", description = "Nothing to show")
                    }))
    public RouterFunction<ServerResponse> getAllProducts(GetAllProductsUseCase getAllProductsUseCase) {
        return route(GET("/products"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllProductsUseCase.get(), Product.class))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(throwable.getMessage()))
        );
    }

    @Bean
    @RouterOperation(path = "/products/{productId}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = GetProductByIdUseCase.class,
            method = RequestMethod.GET,
            beanMethod = "apply",
            operation = @Operation(operationId = "getProductById", tags = "Products usecases",
                    parameters = {@Parameter(name = "productId", description = "product Id", required= true, in = ParameterIn.PATH)},
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Success",
                                    content = @Content (schema = @Schema(implementation = Product.class))),
                            @ApiResponse(responseCode = "404", description = "Not Found")
                    }))
    public RouterFunction<ServerResponse> getProductsById (GetProductByIdUseCase getProductByIdUseCase){
        return route(GET("/products/{productId}"),
                request -> getProductByIdUseCase.apply(request.pathVariable("productId"))
                        .flatMap(product -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(product))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(throwable.getMessage()))
        );
    }

    //Doesn'twork yet
    @Bean
    @RouterOperation(path = "/products/byName/{productName}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = GetProductByNameUseCase.class,
            method = RequestMethod.GET,
            beanMethod = "apply",
            operation = @Operation(operationId = "getProductByName", tags = "Products usecases",
                    parameters = {@Parameter(name = "productName", description = "product Name", required= true, in = ParameterIn.PATH)},
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Success",
                                    content = @Content (schema = @Schema(implementation = Product.class))),
                            @ApiResponse(responseCode = "404", description = "Not Found")
                    }))
    public RouterFunction<ServerResponse> getProductsByName (GetProductByNameUseCase getProductByNameUseCase){
        return route(GET("/products/byName/{productName}"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getProductByNameUseCase.apply(request.pathVariable("productName")), Product.class))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(throwable.getMessage()))
        );
    }

    //Doesn'twork yet
    @Bean
    @RouterOperation(path = "/products/byAnimalType/{productAnimalType}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = GetProductByanimalTypeUseCase.class,
            method = RequestMethod.GET,
            beanMethod = "apply",
            operation = @Operation(operationId = "getProductByAnimalType", tags = "Products usecases",
                    parameters = {@Parameter(name = "productAnimalType", description = "product animalType", required= true, in = ParameterIn.PATH)},
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Success",
                                    content = @Content (schema = @Schema(implementation = Product.class))),
                            @ApiResponse(responseCode = "404", description = "Not Found")
                    }))
    public RouterFunction<ServerResponse> getProductsByAnimalType (GetProductByanimalTypeUseCase getProductByanimalTypeUseCase){
        return route(GET("/products/byAnimalType/{productAnimalType}"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getProductByanimalTypeUseCase.apply(request.pathVariable("productAnimalType")), Product.class))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(throwable.getMessage()))
        );
    }

    //Doesn'twork yet
    @Bean
    @RouterOperation(path = "/products/byCategory/{productCategory}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = GetProductByanimalTypeUseCase.class,
            method = RequestMethod.GET,
            beanMethod = "apply",
            operation = @Operation(operationId = "getProductByAnimalType", tags = "Products usecases",
                    parameters = {@Parameter(name = "productAnimalType", description = "product animalType", required= true, in = ParameterIn.PATH)},
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Success",
                                    content = @Content (schema = @Schema(implementation = Product.class))),
                            @ApiResponse(responseCode = "404", description = "Not Found")
                    }))
    public RouterFunction<ServerResponse> getProductsByCategory (GetProductByCategoryUseCase getProductByCategoryUseCase){
        return route(GET("/products/byCategory/{productCategory}"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getProductByCategoryUseCase.apply(request.pathVariable("productCategory")), Product.class))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(throwable.getMessage()))
        );
    }

    @Bean
    @RouterOperation(path = "/products", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = SaveProductUseCase.class, method = RequestMethod.POST,
            beanMethod = "apply",
            operation = @Operation(operationId = "saveProduct", tags = "Products usecases",
                    responses = {
                            @ApiResponse(responseCode = "201", description = "Success",
                                    content = @Content (schema = @Schema(implementation = Product.class))),
                            @ApiResponse(responseCode = "406", description = "Not acceptable, Try again")
                    }))
    public RouterFunction<ServerResponse> saveProduct (SaveProductUseCase saveProductUseCase){
        return route(POST("/products").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(Product.class)
                        .flatMap(product -> saveProductUseCase.apply(product)
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.BAD_REQUEST)
                                        .bodyValue(throwable.getMessage()))));
    }

    @Bean
    @RouterOperation(path = "/products/{productId}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = UpdateProductUseCase.class, method = RequestMethod.PUT,
            beanMethod = "apply",
            operation = @Operation(operationId = "updateProduct", tags = "Products usecases",
                    parameters = {@Parameter(name = "productId", description = "product Id", required= true, in = ParameterIn.PATH)},
                    responses = {
                            @ApiResponse(responseCode = "201", description = "Success",
                                    content = @Content (schema = @Schema(implementation = Product.class))),
                            @ApiResponse(responseCode = "406", description = "Not acceptable, Try again")
                    }))
    public RouterFunction<ServerResponse> updateProduct (UpdateProductUseCase updateProductUseCase){
        return route(PUT("/products/{productId}").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(Product.class)
                        .flatMap(product -> updateProductUseCase.apply(request.pathVariable("productId"),
                                        product)
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.BAD_REQUEST)
                                        .bodyValue(throwable.getMessage()))
                        )
        );
    }

    @Bean
    @RouterOperation(path = "/products/{productId}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = DeleteProductUseCase.class, method = RequestMethod.DELETE,
            beanMethod = "apply",
            operation = @Operation(operationId = "deleteProductById", tags = "Products usecases",
                    parameters = {@Parameter(name = "productId", description = "product Id", required= true, in = ParameterIn.PATH)},
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Success",
                                    content = @Content (schema = @Schema(implementation = Product.class))),
                            @ApiResponse(responseCode = "404", description = "Not Found")
                    }))
    public RouterFunction<ServerResponse> deleteProduct (DeleteProductUseCase deleteProductUseCase){
        return route(DELETE("/products/{productId}"),
                request -> deleteProductUseCase.apply(request.pathVariable("productId"))
                        .thenReturn(ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue("Product deleted"))
                        .flatMap(serverResponseMono -> serverResponseMono)
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(throwable.getMessage()))
        );
    }

}
