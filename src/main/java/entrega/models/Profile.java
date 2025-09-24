package entrega.models;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "profiles")
public class Profile extends PanacheEntity {

    @NotBlank(message = "Endereço é obrigatório")
    @Size(min = 10, max = 200, message = "Endereço deve ter entre 10 e 200 caracteres")
    @Column(nullable = false)
    public String address;
    
    @NotBlank(message = "Telefone é obrigatório")
    @Pattern(regexp = "^\\(?\\d{2}\\)?[\\s-]?\\d{4,5}[\\s-]?\\d{4}$", 
             message = "Telefone deve ter formato válido: (XX) XXXXX-XXXX ou (XX) XXXX-XXXX")
    @Column(nullable = false)
    public String phone;
    
    @Size(max = 100, message = "Cidade deve ter no máximo 100 caracteres")
    public String city;
    
    @Size(max = 2, message = "Estado deve ter 2 caracteres")
    public String state;
    
    @Pattern(regexp = "^\\d{5}-?\\d{3}$", message = "CEP deve ter formato válido: XXXXX-XXX")
    public String zipCode;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    public Customer customer;

}
