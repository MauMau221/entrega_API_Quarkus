package entrega.repositories;

import entrega.models.Profile;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ProfileRepository implements PanacheRepository<Profile> {
    
    public Profile findByCustomerId(Long customerId) {
        return find("customer.id = ?1", customerId).firstResult();
    }
    
    public List<Profile> findByCity(String city) {
        return find("LOWER(city) = LOWER(?1)", city).list();
    }
    
    public List<Profile> findByState(String state) {
        return find("LOWER(state) = LOWER(?1)", state).list();
    }
}
