package entrega.exceptions;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

import java.util.ArrayList;
import java.util.List;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Exception> {
    
    private static final Logger LOG = Logger.getLogger(GlobalExceptionHandler.class);

    @Override
    public Response toResponse(Exception exception) {
        LOG.error("Erro não tratado: ", exception);
        
        if (exception instanceof ConstraintViolationException) {
            return handleConstraintViolation((ConstraintViolationException) exception);
        }
        
        if (exception instanceof IllegalArgumentException) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse("Parâmetros inválidos: " + exception.getMessage()))
                    .build();
        }
        
        if (exception instanceof jakarta.ws.rs.NotFoundException) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse("Recurso não encontrado"))
                    .build();
        }
        
        // Erro genérico
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse("Erro interno do servidor"))
                .build();
    }
    
    private Response handleConstraintViolation(ConstraintViolationException exception) {
        List<String> errors = new ArrayList<>();
        
        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            errors.add(violation.getMessage());
        }
        
        ErrorResponse errorResponse = new ErrorResponse("Dados inválidos", errors);
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errorResponse)
                .build();
    }
    
    public static class ErrorResponse {
        public String message;
        public List<String> details;
        
        public ErrorResponse(String message) {
            this.message = message;
        }
        
        public ErrorResponse(String message, List<String> details) {
            this.message = message;
            this.details = details;
        }
    }
}
