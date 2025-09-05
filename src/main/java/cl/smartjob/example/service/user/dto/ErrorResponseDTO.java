package cl.smartjob.example.service.user.dto;

/**
 * DTO for error responses
 */
public class ErrorResponseDTO {

    private String mensaje;

    // Constructors
    public ErrorResponseDTO() {}

    public ErrorResponseDTO(String mensaje) {
        this.mensaje = mensaje;
    }

    // Getters and Setters
    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
