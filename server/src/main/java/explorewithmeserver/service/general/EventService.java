package explorewithmeserver.service.general;

import explorewithmeserver.model.event.EventDto;
import explorewithmeserver.model.event.EventShortDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    List<EventShortDto> searchEvents(String text,
                                     List<Long> catId,
                                     Boolean paid,
                                     LocalDateTime rangeStart,
                                     LocalDateTime rangeEnd,
                                     Boolean onlyAvailable,
                                     String sort,
                                     Integer from,
                                     Integer size,
                                     HttpServletRequest request);

    EventDto getEventById(Long id, HttpServletRequest request);
}
