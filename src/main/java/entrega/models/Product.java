package entrega.models;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Product extends PanacheEntity {

    public String name;
    public Double price;

    @ManyToMany(mappedBy = "products")
    public List<Order> orders;

}
