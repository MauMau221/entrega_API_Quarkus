package entrega.services;

import entrega.models.Customer;
import entrega.repositories.CustomerRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CustomerService {

    @Inject
    CustomerRepository customerRepository;

    public List<Customer> listAll() {
        return customerRepository.listAll();
    }

    public Optional<Customer> findById(Long id) {
        return Optional.ofNullable(customerRepository.findById(id));
    }

    @Transactional
    public Customer create(@Valid Customer customer) {
        customerRepository.persist(customer);
        return customer;
    }

    @Transactional
    public Optional<Customer> update(Long id, @Valid Customer customerData) {
        Customer customer = customerRepository.findById(id);
        if (customer != null) {
            customer.name = customerData.name;
            customer.email = customerData.email;
            customerRepository.persist(customer);
            return Optional.of(customer);
        }
        return Optional.empty();
    }

    @Transactional
    public boolean delete(Long id) {
        Customer customer = customerRepository.findById(id);
        if (customer != null) {
            customerRepository.delete(customer);
            return true;
        }
        return false;
    }

    public List<Customer> findByNameContaining(String name) {
        return customerRepository.find("LOWER(name) LIKE LOWER(?1)", "%" + name + "%").list();
    }

    public Optional<Customer> findByEmail(String email) {
        Customer customer = customerRepository.find("email = ?1", email).firstResult();
        return Optional.ofNullable(customer);
    }
}
