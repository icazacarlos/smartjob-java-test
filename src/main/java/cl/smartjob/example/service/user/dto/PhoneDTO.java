package cl.smartjob.example.service.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for phone information
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhoneDTO {

    @NotBlank(message = "El número de teléfono es obligatorio")
    private String number;

    private String citycode;

    private String contrycode;
}
