package entrega.controllers;
//Controllers responsavel pelos EndPoints REST (Resource)
import entrega.dtos.CustomerDTO;
import entrega.models.Customer;
import entrega.services.CustomerService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Customers", description = "Operações para gerenciamento de clientes")
public class CustomerController {

    @Inject
    CustomerService customerService;

    @Context
    UriInfo uriInfo;

    @GET
    @Operation(summary = "Listar todos os clientes", description = "Retorna uma lista com todos os clientes cadastrados")
    @APIResponse(responseCode = "200", description = "Lista de clientes retornada com sucesso",
                 content = @Content(schema = @Schema(implementation = CustomerDTO.class)))
    public Response list() {
        List<Customer> customers = customerService.listAll();
        List<CustomerDTO> customerDTOs = customers.stream()
                .map(c -> new CustomerDTO(c.id, c.name, c.email))
                .toList();
        return Response.ok(customerDTOs).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar cliente por ID", description = "Retorna um cliente específico pelo seu ID")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Cliente encontrado",
                     content = @Content(schema = @Schema(implementation = CustomerDTO.class))),
        @APIResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public Response findById(@Parameter(description = "ID do cliente") @PathParam("id") Long id) {
        Optional<Customer> customer = customerService.findById(id);
        if (customer.isPresent()) {
            CustomerDTO dto = new CustomerDTO(customer.get().id, customer.get().name, customer.get().email);
            return Response.ok(dto).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Operation(summary = "Criar novo cliente", description = "Cria um novo cliente no sistema")
    @APIResponses({
        @APIResponse(responseCode = "201", description = "Cliente criado com sucesso",
                     content = @Content(schema = @Schema(implementation = Customer.class))),
        @APIResponse(responseCode = "400", description = "Dados inválidos")
    })
    public Response create(@Valid Customer customer) {
        Customer created = customerService.create(customer);
        URI location = uriInfo.getAbsolutePathBuilder().path(created.id.toString()).build();
        return Response.created(location).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Atualizar cliente", description = "Atualiza os dados de um cliente existente")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
        @APIResponse(responseCode = "404", description = "Cliente não encontrado"),
        @APIResponse(responseCode = "400", description = "Dados inválidos")
    })
    public Response update(@Parameter(description = "ID do cliente") @PathParam("id") Long id, @Valid Customer customer) {
        Optional<Customer> updated = customerService.update(id, customer);
        if (updated.isPresent()) {
            return Response.ok(updated.get()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Deletar cliente", description = "Remove um cliente do sistema")
    @APIResponses({
        @APIResponse(responseCode = "204", description = "Cliente removido com sucesso"),
        @APIResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public Response delete(@Parameter(description = "ID do cliente") @PathParam("id") Long id) {
        boolean deleted = customerService.delete(id);
        if (deleted) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/search")
    @Operation(summary = "Buscar clientes por nome", description = "Busca clientes que contenham o nome especificado")
    @APIResponse(responseCode = "200", description = "Clientes encontrados",
                 content = @Content(schema = @Schema(implementation = CustomerDTO.class)))
    public Response findByName(@Parameter(description = "Nome para busca") @QueryParam("name") String name) {
        if (name == null || name.trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Parâmetro 'name' é obrigatório").build();
        }
        List<Customer> customers = customerService.findByNameContaining(name);
        List<CustomerDTO> customerDTOs = customers.stream()
                .map(c -> new CustomerDTO(c.id, c.name, c.email))
                .toList();
        return Response.ok(customerDTOs).build();
    }
}
