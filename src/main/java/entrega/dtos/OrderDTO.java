package entrega.dtos;

import entrega.models.enums.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {
    public Long id;
    public Long customerId;
    public String customerName;
    public OrderStatus status;
    public LocalDateTime orderDate;
    public BigDecimal totalAmount;
    public List<ProductDTO> products;

    public OrderDTO() {}

    public OrderDTO(Long id, Long customerId, String customerName, OrderStatus status, 
                   LocalDateTime orderDate, BigDecimal totalAmount, List<ProductDTO> products) {
        this.id = id;
        this.customerId = customerId;
        this.customerName = customerName;
        this.status = status;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.products = products;
    }
}
