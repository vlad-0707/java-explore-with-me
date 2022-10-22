package explorewithmeserver.model.event;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EventUpdateDto {

    private Long eventId;
    private String annotation;
    private Long category;
    private String description;
    private LocalDateTime eventDate;
    private Boolean paid;
    private Integer participantLimit;
    private String title;
}
