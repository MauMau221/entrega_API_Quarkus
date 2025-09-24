package entrega.controllers;

import entrega.dtos.ProfileDTO;
import entrega.models.Profile;
import entrega.services.ProfileService;
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

@Path("/profiles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Profiles", description = "Operações para gerenciamento de perfis de clientes")
public class ProfileController {

    @Inject
    ProfileService profileService;

    @Context
    UriInfo uriInfo;

    @GET
    @Operation(summary = "Listar todos os perfis", description = "Retorna uma lista com todos os perfis cadastrados")
    @APIResponse(responseCode = "200", description = "Lista de perfis retornada com sucesso",
                 content = @Content(schema = @Schema(implementation = ProfileDTO.class)))
    public Response list() {
        List<Profile> profiles = profileService.listAll();
        List<ProfileDTO> profileDTOs = profiles.stream()
                .map(this::convertToDTO)
                .toList();
        return Response.ok(profileDTOs).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar perfil por ID", description = "Retorna um perfil específico pelo seu ID")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Perfil encontrado",
                     content = @Content(schema = @Schema(implementation = ProfileDTO.class))),
        @APIResponse(responseCode = "404", description = "Perfil não encontrado")
    })
    public Response findById(@Parameter(description = "ID do perfil") @PathParam("id") Long id) {
        Optional<Profile> profile = profileService.findById(id);
        if (profile.isPresent()) {
            ProfileDTO dto = convertToDTO(profile.get());
            return Response.ok(dto).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Operation(summary = "Criar novo perfil", description = "Cria um novo perfil no sistema")
    @APIResponses({
        @APIResponse(responseCode = "201", description = "Perfil criado com sucesso",
                     content = @Content(schema = @Schema(implementation = Profile.class))),
        @APIResponse(responseCode = "400", description = "Dados inválidos")
    })
    public Response create(@Valid Profile profile) {
        Profile created = profileService.create(profile);
        URI location = uriInfo.getAbsolutePathBuilder().path(created.id.toString()).build();
        return Response.created(location).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Atualizar perfil", description = "Atualiza os dados de um perfil existente")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Perfil atualizado com sucesso"),
        @APIResponse(responseCode = "404", description = "Perfil não encontrado"),
        @APIResponse(responseCode = "400", description = "Dados inválidos")
    })
    public Response update(@Parameter(description = "ID do perfil") @PathParam("id") Long id, @Valid Profile profile) {
        Optional<Profile> updated = profileService.update(id, profile);
        if (updated.isPresent()) {
            return Response.ok(updated.get()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Deletar perfil", description = "Remove um perfil do sistema")
    @APIResponses({
        @APIResponse(responseCode = "204", description = "Perfil removido com sucesso"),
        @APIResponse(responseCode = "404", description = "Perfil não encontrado")
    })
    public Response delete(@Parameter(description = "ID do perfil") @PathParam("id") Long id) {
        boolean deleted = profileService.delete(id);
        if (deleted) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/customer/{customerId}")
    @Operation(summary = "Buscar perfil por cliente", description = "Busca o perfil de um cliente específico")
    @APIResponse(responseCode = "200", description = "Perfil encontrado",
                 content = @Content(schema = @Schema(implementation = ProfileDTO.class)))
    public Response findByCustomerId(@Parameter(description = "ID do cliente") @PathParam("customerId") Long customerId) {
        Optional<Profile> profile = profileService.findByCustomerId(customerId);
        if (profile.isPresent()) {
            ProfileDTO dto = convertToDTO(profile.get());
            return Response.ok(dto).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/city/{city}")
    @Operation(summary = "Buscar perfis por cidade", description = "Busca todos os perfis de uma cidade específica")
    @APIResponse(responseCode = "200", description = "Perfis encontrados",
                 content = @Content(schema = @Schema(implementation = ProfileDTO.class)))
    public Response findByCity(@Parameter(description = "Nome da cidade") @PathParam("city") String city) {
        List<Profile> profiles = profileService.findByCity(city);
        List<ProfileDTO> profileDTOs = profiles.stream()
                .map(this::convertToDTO)
                .toList();
        return Response.ok(profileDTOs).build();
    }

    @GET
    @Path("/state/{state}")
    @Operation(summary = "Buscar perfis por estado", description = "Busca todos os perfis de um estado específico")
    @APIResponse(responseCode = "200", description = "Perfis encontrados",
                 content = @Content(schema = @Schema(implementation = ProfileDTO.class)))
    public Response findByState(@Parameter(description = "Sigla do estado") @PathParam("state") String state) {
        List<Profile> profiles = profileService.findByState(state);
        List<ProfileDTO> profileDTOs = profiles.stream()
                .map(this::convertToDTO)
                .toList();
        return Response.ok(profileDTOs).build();
    }

    private ProfileDTO convertToDTO(Profile profile) {
        return new ProfileDTO(
                profile.id,
                profile.address,
                profile.phone,
                profile.city,
                profile.state,
                profile.zipCode,
                profile.customer.id
        );
    }
}
