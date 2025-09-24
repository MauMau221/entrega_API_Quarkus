package entrega.models;
//Cliente
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "customers")
public class Customer extends PanacheEntity {
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    @Column(nullable = false)
    public String name;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ter um formato válido")
    @Column(nullable = false, unique = true)
    public String email;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Profile profile;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<Order> orders;

}
