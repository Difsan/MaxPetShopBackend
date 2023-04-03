package co.com.maxpetshop.model.receipt.gateways;

import co.com.maxpetshop.model.product.Product;
import co.com.maxpetshop.model.receipt.Receipt;
import reactor.core.publisher.Mono;

public interface ReceiptRepository {

    Mono<Receipt> getReceiptById(String receiptId);
    Mono<Receipt> saveReceipt( Receipt receipt);
    Mono<Receipt> updateReceipt ( String receiptId, Receipt receipt);
    Mono<Void> deleteReceipt(String receiptId);
}
