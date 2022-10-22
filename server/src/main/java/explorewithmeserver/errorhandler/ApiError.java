package explorewithmeserver.errorhandler;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;


/**
 * Сведения об ошибке
 */
@Getter
public class ApiError {

    private final String message;

    private final String reason;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime timestamp;


    public ApiError(String reason, String message, LocalDateTime timestamp) {
        this.reason = reason;
        this.message = message;
        this.timestamp = timestamp;
    }
}
