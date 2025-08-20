package unir.des.software.smart.city.parking.error;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiValidationError {
    private String field;       // firstName
    private Object rejectedValue; // null, "abc", etc.
    private String message;
}
