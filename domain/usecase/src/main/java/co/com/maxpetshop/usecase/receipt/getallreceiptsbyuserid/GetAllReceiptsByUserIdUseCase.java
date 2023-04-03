package co.com.maxpetshop.usecase.receipt.getallreceiptsbyuserid;

import co.com.maxpetshop.model.receipt.Receipt;
import co.com.maxpetshop.model.receipt.gateways.ReceiptRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@RequiredArgsConstructor
public class GetAllReceiptsByUserIdUseCase implements Function<String, Flux<Receipt>> {

    private final ReceiptRepository receiptRepository;

    @Override
    public Flux<Receipt> apply(String userId) {
        return receiptRepository.getAllReceiptsByUserId(userId);
    }
}
