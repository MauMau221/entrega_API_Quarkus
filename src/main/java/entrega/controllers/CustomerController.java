package entrega.controllers;
//Controllers responsavel pelos EndPoints REST (Resource)
import entrega.dtos.CustomerDTO;
import entrega.models.Customer;
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
    CustomerService customerService;

    @GET
    public List<CustomerDTO> list() {
        return customerService.listAll().stream()
                .map(c -> new CustomerDTO(c.id, c.name, c.email))
                .toList();
    }

    @POST
    public Customer create(Customer customer) {
        return customerService.create(customer);
    }
}
