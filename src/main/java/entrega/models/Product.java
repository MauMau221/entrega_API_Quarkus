package entrega.models;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "products")
public class Product extends PanacheEntity {

    @NotBlank(message = "Nome do produto é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    @Column(nullable = false)
    public String name;
    
    @NotNull(message = "Preço é obrigatório")
    @DecimalMin(value = "0.01", message = "Preço deve ser maior que zero")
    @Column(nullable = false, precision = 10, scale = 2)
    public BigDecimal price;
    
    @Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres")
    public String description;

    @ManyToMany(mappedBy = "products", fetch = FetchType.LAZY)
    public List<Order> orders;

}
