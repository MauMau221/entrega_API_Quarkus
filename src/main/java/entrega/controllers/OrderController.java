package entrega.controllers;

import entrega.dtos.OrderDTO;
import entrega.dtos.ProductDTO;
import entrega.models.Order;
import entrega.models.enums.OrderStatus;
import entrega.services.OrderService;
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

import java.util.List;
import java.util.Optional;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Orders", description = "Operações para gerenciamento de pedidos")
public class OrderController {

    @Inject
    OrderService orderService;

    @Context
    UriInfo uriInfo;

    @GET
    @Operation(summary = "Listar todos os pedidos", description = "Retorna uma lista com todos os pedidos cadastrados")
    @APIResponse(responseCode = "200", description = "Lista de pedidos retornada com sucesso",
                 content = @Content(schema = @Schema(implementation = OrderDTO.class)))
    public Response list() {
        List<Order> orders = orderService.listAll();
        List<OrderDTO> orderDTOs = orders.stream()
                .map(this::convertToDTO)
                .toList();
        return Response.ok(orderDTOs).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar pedido por ID", description = "Retorna um pedido específico pelo seu ID")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Pedido encontrado",
                     content = @Content(schema = @Schema(implementation = OrderDTO.class))),
        @APIResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    public Response findById(@Parameter(description = "ID do pedido") @PathParam("id") Long id) {
        Optional<Order> order = orderService.findById(id);
        if (order.isPresent()) {
            OrderDTO dto = convertToDTO(order.get());
            return Response.ok(dto).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Operation(summary = "Criar novo pedido", description = "Cria um novo pedido no sistema")
    @APIResponses({
        @APIResponse(responseCode = "201", description = "Pedido criado com sucesso",
                     content = @Content(schema = @Schema(implementation = Order.class))),
        @APIResponse(responseCode = "400", description = "Dados inválidos")
    })
    public Response create(@Valid Order order) {
        Order created = orderService.create(order);
        return Response.created(uriInfo.getAbsolutePathBuilder().path(created.id.toString()).build())
                .entity(created).build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Atualizar pedido", description = "Atualiza os dados de um pedido existente")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Pedido atualizado com sucesso"),
        @APIResponse(responseCode = "404", description = "Pedido não encontrado"),
        @APIResponse(responseCode = "400", description = "Dados inválidos")
    })
    public Response update(@Parameter(description = "ID do pedido") @PathParam("id") Long id, @Valid Order order) {
        Optional<Order> updated = orderService.update(id, order);
        if (updated.isPresent()) {
            return Response.ok(updated.get()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Deletar pedido", description = "Remove um pedido do sistema")
    @APIResponses({
        @APIResponse(responseCode = "204", description = "Pedido removido com sucesso"),
        @APIResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    public Response delete(@Parameter(description = "ID do pedido") @PathParam("id") Long id) {
        boolean deleted = orderService.delete(id);
        if (deleted) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/customer/{customerId}")
    @Operation(summary = "Buscar pedidos por cliente", description = "Busca todos os pedidos de um cliente específico")
    @APIResponse(responseCode = "200", description = "Pedidos encontrados",
                 content = @Content(schema = @Schema(implementation = OrderDTO.class)))
    public Response findByCustomerId(@Parameter(description = "ID do cliente") @PathParam("customerId") Long customerId) {
        List<Order> orders = orderService.findByCustomerId(customerId);
        List<OrderDTO> orderDTOs = orders.stream()
                .map(this::convertToDTO)
                .toList();
        return Response.ok(orderDTOs).build();
    }

    @GET
    @Path("/status/{status}")
    @Operation(summary = "Buscar pedidos por status", description = "Busca todos os pedidos com um status específico")
    @APIResponse(responseCode = "200", description = "Pedidos encontrados",
                 content = @Content(schema = @Schema(implementation = OrderDTO.class)))
    public Response findByStatus(@Parameter(description = "Status do pedido") @PathParam("status") OrderStatus status) {
        List<Order> orders = orderService.findByStatus(status);
        List<OrderDTO> orderDTOs = orders.stream()
                .map(this::convertToDTO)
                .toList();
        return Response.ok(orderDTOs).build();
    }

    @POST
    @Path("/{orderId}/products/{productId}")
    @Operation(summary = "Adicionar produto ao pedido", description = "Adiciona um produto a um pedido existente")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Produto adicionado com sucesso"),
        @APIResponse(responseCode = "404", description = "Pedido ou produto não encontrado")
    })
    public Response addProductToOrder(
            @Parameter(description = "ID do pedido") @PathParam("orderId") Long orderId,
            @Parameter(description = "ID do produto") @PathParam("productId") Long productId) {
        
        Optional<Order> order = orderService.addProductToOrder(orderId, productId);
        if (order.isPresent()) {
            return Response.ok(order.get()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    private OrderDTO convertToDTO(Order order) {
        List<ProductDTO> productDTOs = order.products != null ? 
                order.products.stream()
                        .map(p -> new ProductDTO(p.id, p.name, p.price, p.description))
                        .toList() : List.of();

        return new OrderDTO(
                order.id,
                order.customer.id,
                order.customer.name,
                order.status,
                order.orderDate,
                order.totalAmount,
                productDTOs
        );
    }
}
