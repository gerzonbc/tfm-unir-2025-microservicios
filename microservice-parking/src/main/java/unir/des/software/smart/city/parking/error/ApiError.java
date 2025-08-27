package unir.des.software.smart.city.parking.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class ApiError {
    private Instant timestamp;          // ISO-8601
    private int status;                 // 400, 404, 500...
    private String error;               // "Bad Request", "Not Found"...
    private String message;             // Mensaje general
    private String path;                // /api/students
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ApiValidationError> errors; // Detalles de validación
}
