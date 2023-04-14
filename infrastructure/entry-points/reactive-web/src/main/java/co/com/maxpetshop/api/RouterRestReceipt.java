package co.com.maxpetshop.api;

import co.com.maxpetshop.model.item.Item;
import co.com.maxpetshop.model.product.Product;
import co.com.maxpetshop.model.receipt.Receipt;
import co.com.maxpetshop.model.user.User;
import co.com.maxpetshop.usecase.receipt.deletereceipt.DeleteReceiptUseCase;
import co.com.maxpetshop.usecase.receipt.getallreceiptsbyuserid.GetAllReceiptsByUserIdUseCase;
import co.com.maxpetshop.usecase.receipt.getreceiptbyid.GetReceiptByIdUseCase;
import co.com.maxpetshop.usecase.receipt.savereceipt.SaveReceiptUseCase;
import co.com.maxpetshop.usecase.receipt.updatereceipt.UpdateReceiptUseCase;
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

import java.util.Collections;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRestReceipt {


    @Bean
    @RouterOperation(path = "/receipts/{receiptId}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = GetReceiptByIdUseCase.class,
            method = RequestMethod.GET,
            beanMethod = "apply",
            operation = @Operation(operationId = "getReceiptById", tags = "Receipt usecases",
                    parameters = {@Parameter(name = "receiptId", description = "receipt Id", required= true, in = ParameterIn.PATH)},
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Success",
                                    content = @Content (schema = @Schema(implementation = Receipt.class))),
                            @ApiResponse(responseCode = "404", description = "Not Found")
                    }))
    public RouterFunction<ServerResponse> getReceiptById (GetReceiptByIdUseCase getReceiptByIdUseCase){
        return route(GET("/receipts/{receiptId}"),
                request -> getReceiptByIdUseCase.apply(request.pathVariable("receiptId"))
                        .flatMap(receipt -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(receipt))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(throwable.getMessage()))
        );
    }

    //Doesn'twork yet
    @Bean
    @RouterOperation(path = "/receipts/byUserId/{userId}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = GetAllReceiptsByUserIdUseCase.class,
            method = RequestMethod.GET,
            beanMethod = "apply",
            operation = @Operation(operationId = "getAllReceiptsByUserId", tags = "Receipt usecases",
                    parameters = {@Parameter(name = "userId", description = "receipts by userId", required= true, in = ParameterIn.PATH)},
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Success",
                                    content = @Content (schema = @Schema(implementation = Receipt.class))),
                            @ApiResponse(responseCode = "404", description = "Not Found")
                    }))
    public RouterFunction<ServerResponse> getReceiptsByUserId (GetAllReceiptsByUserIdUseCase getAllReceiptsByUserIdUseCase){
        return route(GET("/receipts/byUserId/{userId}"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllReceiptsByUserIdUseCase.apply(request.pathVariable("userId")), Receipt.class))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(throwable.getMessage()))
        );
    }

    @Bean
    @RouterOperation(path = "/receipts", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = SaveReceiptUseCase.class, method = RequestMethod.POST,
            beanMethod = "apply",
            operation = @Operation(operationId = "saveReceipt", tags = "Receipt usecases",
                    parameters = {@Parameter(name = "receipt", in = ParameterIn.PATH,
                            schema = @Schema(implementation = Item.class))},
                    responses = {
                            @ApiResponse(responseCode = "201", description = "Success",
                                    content = @Content (schema = @Schema(implementation = Receipt.class))),
                            @ApiResponse(responseCode = "406", description = "Not acceptable, Try again")
                    },
                    requestBody = @RequestBody(required = true, description = "Save a Receipt following the schema",
                            content = @Content(schema = @Schema(implementation = Receipt.class)))
                    ))
    public RouterFunction<ServerResponse> saveReceipt (SaveReceiptUseCase saveReceiptUseCase){
        return route(POST("/receipts").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(Receipt.class)
                        .flatMap(receipt -> saveReceiptUseCase.apply(receipt)
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.BAD_REQUEST)
                                        .bodyValue(throwable.getMessage()))));
    }

    @Bean
    @RouterOperation(path = "/receipts/{receiptId}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = UpdateReceiptUseCase.class, method = RequestMethod.PUT,
            beanMethod = "apply",
            operation = @Operation(operationId = "updateReceipt", tags = "Receipt usecases",
                    parameters = {@Parameter(name = "receiptId", description = "Receipt Id", required= true, in = ParameterIn.PATH),
                            @Parameter(name = "receipt", in = ParameterIn.PATH,
                                    schema = @Schema(implementation = Item.class))},
                    responses = {
                            @ApiResponse(responseCode = "201", description = "Success",
                                    content = @Content (schema = @Schema(implementation = Receipt.class))),
                            @ApiResponse(responseCode = "406", description = "Not acceptable, Try again")
                    }
                    ,
                    requestBody = @RequestBody(required = true, description = "Update a Receipt following the schema",
                            content = @Content(schema = @Schema(implementation = Receipt.class)))
                    ))
    public RouterFunction<ServerResponse> updateReceipt (UpdateReceiptUseCase updateReceiptUseCase){
        return route(PUT("/receipts/{receiptId}").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(Receipt.class)
                        .flatMap(receipt -> updateReceiptUseCase.apply(request.pathVariable("receiptId"),
                                        receipt)
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.BAD_REQUEST)
                                        .bodyValue(throwable.getMessage()))
                        )
        );
    }

    @Bean
    @RouterOperation(path = "/receipts/{receiptId}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = DeleteReceiptUseCase.class, method = RequestMethod.DELETE,
            beanMethod = "apply",
            operation = @Operation(operationId = "deleteReceiptById", tags = "Receipt usecases",
                    parameters = {@Parameter(name = "receiptId", description = "receipt Id", required= true, in = ParameterIn.PATH)},
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Success",
                                    content = @Content (schema = @Schema(implementation = Receipt.class))),
                            @ApiResponse(responseCode = "404", description = "Not Found")
                    }))
    public RouterFunction<ServerResponse> deleteReceipt (DeleteReceiptUseCase deleteReceiptUseCase){
        return route(DELETE("/receipts/{receiptId}"),
                request -> deleteReceiptUseCase.apply(request.pathVariable("receiptId"))
                        .thenReturn(ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                //.bodyValue("Receipt deleted"))
                                .bodyValue(Collections.singletonMap("message", "Receipt deleted")))
                        .flatMap(serverResponseMono -> serverResponseMono)
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(throwable.getMessage()))
        );
    }

}
