package entrega.controllers;

import entrega.models.Customer;
import entrega.repositories.CustomerRepository;
import entrega.services.CustomerService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerController {

    @Inject
    CustomerRepository customerRepository;
    @Inject
    CustomerService customerService;

    @GET
    public List<Customer> list() {
        return customerService.listAll();
    }

    @POST
    public Customer create(Customer customer) {
        return customerService.create(customer);
    }
}
