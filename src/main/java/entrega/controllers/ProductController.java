package entrega.controllers;

import entrega.dtos.ProductDTO;
import entrega.models.Product;
import entrega.services.ProductService;
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

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Products", description = "Operações para gerenciamento de produtos")
public class ProductController {

    @Inject
    ProductService productService;

    @Context
    UriInfo uriInfo;

    @GET
    @Operation(summary = "Listar todos os produtos", description = "Retorna uma lista com todos os produtos cadastrados")
    @APIResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso",
                 content = @Content(schema = @Schema(implementation = ProductDTO.class)))
    public Response list() {
        List<Product> products = productService.listAll();
        List<ProductDTO> productDTOs = products.stream()
                .map(p -> new ProductDTO(p.id, p.name, p.price, p.description))
                .toList();
        return Response.ok(productDTOs).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar produto por ID", description = "Retorna um produto específico pelo seu ID")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Produto encontrado",
                     content = @Content(schema = @Schema(implementation = ProductDTO.class))),
        @APIResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public Response findById(@Parameter(description = "ID do produto") @PathParam("id") Long id) {
        Optional<Product> product = productService.findById(id);
        if (product.isPresent()) {
            ProductDTO dto = new ProductDTO(product.get().id, product.get().name, product.get().price, product.get().description);
            return Response.ok(dto).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Operation(summary = "Criar novo produto", description = "Cria um novo produto no sistema")
    @APIResponses({
        @APIResponse(responseCode = "201", description = "Produto criado com sucesso",
                     content = @Content(schema = @Schema(implementation = Product.class))),
        @APIResponse(responseCode = "400", description = "Dados inválidos")
    })
    public Response create(@Valid Product product) {
        Product created = productService.create(product);
        URI location = uriInfo.getAbsolutePathBuilder().path(created.id.toString()).build();
        return Response.created(location).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Atualizar produto", description = "Atualiza os dados de um produto existente")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
        @APIResponse(responseCode = "404", description = "Produto não encontrado"),
        @APIResponse(responseCode = "400", description = "Dados inválidos")
    })
    public Response update(@Parameter(description = "ID do produto") @PathParam("id") Long id, @Valid Product product) {
        Optional<Product> updated = productService.update(id, product);
        if (updated.isPresent()) {
            return Response.ok(updated.get()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Deletar produto", description = "Remove um produto do sistema")
    @APIResponses({
        @APIResponse(responseCode = "204", description = "Produto removido com sucesso"),
        @APIResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public Response delete(@Parameter(description = "ID do produto") @PathParam("id") Long id) {
        boolean deleted = productService.delete(id);
        if (deleted) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/search")
    @Operation(summary = "Buscar produtos por nome", description = "Busca produtos que contenham o nome especificado")
    @APIResponse(responseCode = "200", description = "Produtos encontrados",
                 content = @Content(schema = @Schema(implementation = ProductDTO.class)))
    public Response findByName(@Parameter(description = "Nome para busca") @QueryParam("name") String name) {
        if (name == null || name.trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Parâmetro 'name' é obrigatório").build();
        }
        List<Product> products = productService.findByNameContaining(name);
        List<ProductDTO> productDTOs = products.stream()
                .map(p -> new ProductDTO(p.id, p.name, p.price, p.description))
                .toList();
        return Response.ok(productDTOs).build();
    }

    @GET
    @Path("/price-range")
    @Operation(summary = "Buscar produtos por faixa de preço", description = "Busca produtos dentro de uma faixa de preço especificada")
    @APIResponse(responseCode = "200", description = "Produtos encontrados",
                 content = @Content(schema = @Schema(implementation = ProductDTO.class)))
    public Response findByPriceRange(
            @Parameter(description = "Preço mínimo") @QueryParam("minPrice") BigDecimal minPrice,
            @Parameter(description = "Preço máximo") @QueryParam("maxPrice") BigDecimal maxPrice) {
        
        if (minPrice == null || maxPrice == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Parâmetros 'minPrice' e 'maxPrice' são obrigatórios").build();
        }
        
        if (minPrice.compareTo(maxPrice) > 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Preço mínimo não pode ser maior que preço máximo").build();
        }
        
        List<Product> products = productService.findByPriceRange(minPrice, maxPrice);
        List<ProductDTO> productDTOs = products.stream()
                .map(p -> new ProductDTO(p.id, p.name, p.price, p.description))
                .toList();
        return Response.ok(productDTOs).build();
    }
}
