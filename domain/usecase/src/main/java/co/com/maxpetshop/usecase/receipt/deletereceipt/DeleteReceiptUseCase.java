package co.com.maxpetshop.usecase.receipt.deletereceipt;

import co.com.maxpetshop.model.receipt.gateways.ReceiptRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class DeleteReceiptUseCase implements Function<String, Mono<Void>> {

    private final ReceiptRepository receiptRepository;

    @Override
    public Mono<Void> apply(String receiptId) {
        return receiptRepository.deleteReceipt(receiptId);
    }
}
