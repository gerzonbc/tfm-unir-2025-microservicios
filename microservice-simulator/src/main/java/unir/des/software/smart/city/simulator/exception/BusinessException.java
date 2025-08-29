package unir.des.software.smart.city.slots.exception;

import lombok.Getter;

import java.io.Serial;

@Getter
public class BusinessException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Optional numeric error code (0 = undefined).
     */
    private final int code;

    /**
     * Crea una excepción con solo mensaje.
     */
    public BusinessException(String message) {
        super(message);
        this.code = 0;
    }

    /**
     * Crea una excepción con mensaje y código de error.
     */
    public BusinessException(String message, int code) {
        super(message);
        this.code = code;
    }

    /**
     * Crea una excepción con mensaje y causa original.
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.code = 0;
    }

    /**
     * Crea una excepción con mensaje, código de error y causa original.
     */
    public BusinessException(String message, int code, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
