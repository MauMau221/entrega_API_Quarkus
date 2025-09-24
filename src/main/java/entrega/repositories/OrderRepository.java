package entrega.repositories;

import entrega.models.Order;
import entrega.models.enums.OrderStatus;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class OrderRepository implements PanacheRepository<Order> {
    
    public List<Order> findByCustomerId(Long customerId) {
        return find("customer.id = ?1", customerId).list();
    }
    
    public List<Order> findByStatus(OrderStatus status) {
        return find("status = ?1", status).list();
    }
    
    public List<Order> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return find("orderDate BETWEEN ?1 AND ?2", startDate, endDate).list();
    }
}