package co.com.maxpetshop.model.category.gateways;

import co.com.maxpetshop.model.category.Category;
import co.com.maxpetshop.model.product.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoryRepository {

    Flux<Category> getAllCategories();

    Mono<Category> getCategoryById(String categoryId);

    Mono<Category> saveCategory( Category category);

    Mono<Category> updateCategory ( String categoryId, Category category);

    Mono<Void> deleteCategory( String categoryId);
}
