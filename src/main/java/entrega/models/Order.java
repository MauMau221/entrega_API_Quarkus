package entrega.models;

import entrega.models.enums.OrderStatus;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends PanacheEntity {

    @ManyToOne
    @JoinColumn(name = "customer_id")
    public Customer customer;

    @Enumerated(EnumType.STRING)
    public OrderStatus Status;

    @ManyToMany
    @JoinTable(
            name = "order_product",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    public List<Product> products;
}
