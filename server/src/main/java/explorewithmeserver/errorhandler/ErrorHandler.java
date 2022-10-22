package explorewithmeserver.errorhandler;

import explorewithmeserver.exception.ForbiddenException;
import explorewithmeserver.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFound(final NotFoundException e) {
        return new ApiError("Not found exception",
                e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler({ForbiddenException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError handleNotFound(final ForbiddenException e) {
        return new ApiError("For the requested operation the conditions are not met.",
                e.getMessage(), LocalDateTime.now());
    }
}
