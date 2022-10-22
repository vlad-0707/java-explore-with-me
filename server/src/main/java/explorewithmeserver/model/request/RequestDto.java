package explorewithmeserver.model.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RequestDto {

    private Long id;

    private LocalDateTime created;

    private Long event;

    private Long requester;

    private String status;
}
