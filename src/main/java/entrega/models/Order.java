package entrega.models;

import entrega.models.enums.OrderStatus;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends PanacheEntity {

    @NotNull(message = "Cliente é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    public Customer customer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public OrderStatus status = OrderStatus.NEW;

    @Column(name = "order_date", nullable = false)
    public LocalDateTime orderDate = LocalDateTime.now();

    @Column(name = "total_amount", precision = 10, scale = 2)
    public BigDecimal totalAmount = BigDecimal.ZERO;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "order_product",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    public List<Product> products;
}
