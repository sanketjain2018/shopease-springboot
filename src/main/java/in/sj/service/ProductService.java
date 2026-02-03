package in.sj.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import in.sj.entity.Product;
import in.sj.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private static final Logger log =
            LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    // ================= GET ALL PRODUCTS =================
    public Page<Product> getAllProducts(Pageable pageable) {

        log.debug("FETCH ALL PRODUCTS | page={} | size={} | sort={}",
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSort());

        Page<Product> page = productRepository.findAll(pageable);

        log.debug("PRODUCTS FETCHED | count={}", page.getNumberOfElements());
        return page;
    }

    // ================= SEARCH PRODUCTS =================
    public Page<Product> searchProducts(String keyword, Pageable pageable) {

        log.info("SEARCH PRODUCTS | keyword={} | page={}",
                keyword, pageable.getPageNumber());

        Page<Product> page = productRepository.search(keyword, pageable);

        log.debug("SEARCH RESULT COUNT | keyword={} | count={}",
                keyword, page.getNumberOfElements());

        return page;
    }

    // ================= GET PRODUCT BY ID =================
    public Product getProductById(Long id) {

        log.info("GET PRODUCT BY ID | productId={}", id);

        return productRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("PRODUCT NOT FOUND | productId={}", id);
                    return new RuntimeException(
                            "Product not found with id: " + id);
                });
    }

    // ================= SAVE PRODUCT =================
    public Product save(Product product) {

        boolean isNew = (product.getId() == null);

        log.info("{} PRODUCT | name={} | price={}",
                isNew ? "CREATE" : "UPDATE",
                product.getName(),
                product.getPrice());

        Product saved = productRepository.save(product);

        log.info("PRODUCT SAVED | id={} | name={}",
                saved.getId(), saved.getName());

        return saved;
    }

    // ================= DELETE PRODUCT =================
    public void deleteProduct(Long id) {

        log.warn("DELETE PRODUCT | productId={}", id);

        productRepository.deleteById(id);

        log.info("PRODUCT DELETED | productId={}", id);
    }

    // ================= PRODUCT COUNT =================
    public long getProductCount() {

        long count = productRepository.count();

        log.debug("TOTAL PRODUCT COUNT = {}", count);

        return count;
    }
}
