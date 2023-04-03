package co.com.maxpetshop.usecase.savereceipt;

import co.com.maxpetshop.model.receipt.Receipt;
import co.com.maxpetshop.model.receipt.gateways.ReceiptRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class SaveReceiptUseCase implements Function<Receipt, Mono<Receipt>> {

    private final ReceiptRepository receiptRepository;

    @Override
    public Mono<Receipt> apply(Receipt receipt) {
        return receiptRepository.saveReceipt(receipt);
    }
}
