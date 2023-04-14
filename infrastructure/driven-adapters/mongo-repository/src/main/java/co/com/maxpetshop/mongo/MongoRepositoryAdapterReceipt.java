package co.com.maxpetshop.mongo;

import co.com.maxpetshop.model.receipt.Receipt;
import co.com.maxpetshop.model.receipt.gateways.ReceiptRepository;
import co.com.maxpetshop.mongo.data.ReceiptData;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Date;

@Repository
@RequiredArgsConstructor
public class MongoRepositoryAdapterReceipt implements ReceiptRepository
{
    private final MongoDBRepositoryReceipt repository;

    private final ObjectMapper mapper;

    @Override
    public Mono<Receipt> getReceiptById(String receiptId) {

        return this.repository
                .findById(receiptId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("There is not " +
                        "Receipt with id: " + receiptId)))
                .map(receiptData -> mapper.map(receiptData, Receipt.class));
    }

    @Override
    public Flux<Receipt> getAllReceiptsByUserId(String userId) {
        return this.repository
                .findAll()
                .switchIfEmpty(Flux.empty())
                .filter(receiptData -> receiptData.getUser().getId().equals(userId))
                .map(receiptData -> mapper.map(receiptData, Receipt.class));
    }

    @Override
    public Mono<Receipt> saveReceipt (Receipt receipt) {
        return this.repository
                .save(mapper.map(receipt, ReceiptData.class))
                .switchIfEmpty(Mono.empty())
                .map(receiptData -> mapper.map(receiptData, Receipt.class));
    }

    @Override
    public Mono<Receipt> updateReceipt (String receiptId, Receipt receipt) {

        return this.repository
                .findById(receiptId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("There is not " +
                        "Receipt with id: " + receiptId)))
                .flatMap(receiptData -> {
                    receipt.setId(receiptData.getId());
                    receipt.setCreateDate(new Date());
                    return repository.save(mapper.map(receipt, ReceiptData.class));
                })
                .map(receiptData -> mapper.map(receiptData, Receipt.class));
    }

    @Override
    public Mono<Void> deleteReceipt (String receiptId) {

        return this.repository
                .findById(receiptId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("There is not " +
                        "Receipt with id: " + receiptId)))
                .flatMap(receiptData -> this.repository.deleteById(receiptData.getId()));
    }
}
