package entrega.models;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
public class Customer extends PanacheEntity {
    @NotBlank
    public String name;

    @NotBlank
    public String email;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    public Profile profile;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    public List<Order> orders;

}
