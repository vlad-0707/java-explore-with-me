package explorewithmeserver.service.general.impl;

import explorewithmeserver.client.EventClient;
import explorewithmeserver.mapper.EventMapper;
import explorewithmeserver.model.event.Event;
import explorewithmeserver.model.event.EventDto;
import explorewithmeserver.model.event.EventShortDto;
import explorewithmeserver.model.event.EventSort;
import explorewithmeserver.repository.EventRepository;
import explorewithmeserver.service.general.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository repository;

    private final EventClient eventClient;

    private final EventMapper mapper;

    @Override
    public List<EventShortDto> searchEvents(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart,
                                            LocalDateTime rangeEnd, Boolean onlyAvailable, String sort, Integer from,
                                            Integer size, HttpServletRequest request) {


        List<Event> events;
        Pageable page;
        if (sort.equals(EventSort.EVENT_DATE.name())) {
            page = PageRequest.of(from, size, Sort.by("eventDate").descending());
        } else {
            page = PageRequest.of(from, size);
        }
        if (Objects.isNull(rangeStart) || Objects.isNull(rangeEnd)) {
            rangeStart = LocalDateTime.now();
            return repository.searchEventsIsFuture(text, categories, paid, rangeStart, page)
                    .getContent()
                    .stream()
                    .map(mapper::mapToEventShortDto)
                    .collect(Collectors.toList());
        }
        if (onlyAvailable) {
            events = repository.searchEventsOnlyAvailable(text, categories, paid, rangeStart, rangeEnd, sort,
                    page).getContent();
        } else {
            events = repository.searchEvents(text, categories, paid, rangeStart, rangeEnd, sort, page).getContent();
        }
        eventClient.addViews(request);
        if (sort.equals(EventSort.VIEWS.name())) {
            return events.stream()
                    .map(mapper::mapToEventShortDto)
                    .sorted(Comparator.comparingLong(EventShortDto::getViews).reversed())
                    .collect(Collectors.toList());
        }
        return events.stream()
                .map(mapper::mapToEventShortDto)
                .collect(Collectors.toList());
    }


    @Override
    public EventDto getEventById(Long id, HttpServletRequest request) {
        Event event = repository.findById(id).orElseThrow();
        EventDto eventDto = mapper.mapToEventDto(event);
        eventClient.addViews(request);
        return eventDto;
    }


}
