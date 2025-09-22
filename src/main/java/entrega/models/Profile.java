package entrega.models;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.*;

@Entity
public class Profile extends PanacheEntity {

    public String address;
    public String phone;

    @OneToOne
    @JoinColumn(name = "customer_id")
    public Customer customer;

}
