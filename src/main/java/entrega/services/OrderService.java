package entrega.services;

import entrega.models.Order;
import entrega.models.Product;
import entrega.models.enums.OrderStatus;
import entrega.repositories.OrderRepository;
import entrega.repositories.ProductRepository;
import entrega.repositories.CustomerRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class OrderService {

    @Inject
    OrderRepository orderRepository;
    
    @Inject
    ProductRepository productRepository;
    
    @Inject
    CustomerRepository customerRepository;

    public List<Order> listAll() {
        return orderRepository.listAll();
    }

    public Optional<Order> findById(Long id) {
        return Optional.ofNullable(orderRepository.findById(id));
    }

    @Transactional
    public Order create(@Valid Order order) {
        // Calcular total do pedido
        if (order.products != null && !order.products.isEmpty()) {
            BigDecimal total = order.products.stream()
                    .map(product -> product.price)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            order.totalAmount = total;
        }
        
        order.orderDate = LocalDateTime.now();
        order.status = OrderStatus.NEW;
        orderRepository.persist(order);
        return order;
    }

    @Transactional
    public Optional<Order> update(Long id, @Valid Order orderData) {
        Order order = orderRepository.findById(id);
        if (order != null) {
            if (orderData.products != null) {
                order.products = orderData.products;
                // Recalcular total
                BigDecimal total = order.products.stream()
                        .map(product -> product.price)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                order.totalAmount = total;
            }
            if (orderData.status != null) {
                order.status = orderData.status;
            }
            orderRepository.persist(order);
            return Optional.of(order);
        }
        return Optional.empty();
    }

    @Transactional
    public boolean delete(Long id) {
        Order order = orderRepository.findById(id);
        if (order != null) {
            orderRepository.delete(order);
            return true;
        }
        return false;
    }

    public List<Order> findByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    public List<Order> findByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    public List<Order> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findByDateRange(startDate, endDate);
    }

    @Transactional
    public Optional<Order> addProductToOrder(Long orderId, Long productId) {
        Order order = orderRepository.findById(orderId);
        Product product = productRepository.findById(productId);
        
        if (order != null && product != null) {
            if (order.products == null) {
                order.products = new java.util.ArrayList<>();
            }
            order.products.add(product);
            
            // Recalcular total
            BigDecimal total = order.products.stream()
                    .map(p -> p.price)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            order.totalAmount = total;
            
            orderRepository.persist(order);
            return Optional.of(order);
        }
        return Optional.empty();
    }
}