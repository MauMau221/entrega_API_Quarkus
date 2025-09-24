package entrega.dtos;

import java.math.BigDecimal;

public class ProductDTO {
    public Long id;
    public String name;
    public BigDecimal price;
    public String description;

    public ProductDTO() {}

    public ProductDTO(Long id, String name, BigDecimal price, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
    }
}
