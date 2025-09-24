package entrega.services;

import entrega.models.Product;
import entrega.repositories.ProductRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ProductService {

    @Inject
    ProductRepository productRepository;

    public List<Product> listAll() {
        return productRepository.listAll();
    }

    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(productRepository.findById(id));
    }

    @Transactional
    public Product create(@Valid Product product) {
        productRepository.persist(product);
        return product;
    }

    @Transactional
    public Optional<Product> update(Long id, @Valid Product productData) {
        Product product = productRepository.findById(id);
        if (product != null) {
            product.name = productData.name;
            product.price = productData.price;
            product.description = productData.description;
            productRepository.persist(product);
            return Optional.of(product);
        }
        return Optional.empty();
    }

    @Transactional
    public boolean delete(Long id) {
        Product product = productRepository.findById(id);
        if (product != null) {
            productRepository.delete(product);
            return true;
        }
        return false;
    }

    public List<Product> findByNameContaining(String name) {
        return productRepository.findByNameContaining(name);
    }

    public List<Product> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepository.findByPriceRange(minPrice, maxPrice);
    }
}