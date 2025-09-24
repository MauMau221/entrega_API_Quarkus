package entrega.repositories;

import entrega.models.Product;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ProductRepository implements PanacheRepository<Product> {
    
    public List<Product> findByNameContaining(String name) {
        return find("LOWER(name) LIKE LOWER(?1)", "%" + name + "%").list();
    }
    
    public List<Product> findByPriceRange(java.math.BigDecimal minPrice, java.math.BigDecimal maxPrice) {
        return find("price BETWEEN ?1 AND ?2", minPrice, maxPrice).list();
    }
}