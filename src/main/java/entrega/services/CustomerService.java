package entrega.services;

import entrega.models.Customer;
import entrega.repositories.CustomerRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class CustomerService {

    @Inject
    CustomerRepository customerRepository;

    public List<Customer> listAll() {
        return customerRepository.listAll();
    }

    @Transactional
    public Customer create(Customer customer) {
        customerRepository.persist(customer);
        return customer;
    }
}
