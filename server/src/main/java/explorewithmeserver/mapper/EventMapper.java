package explorewithmeserver.mapper;

import explorewithmeserver.client.EventClient;
import explorewithmeserver.model.Location;
import explorewithmeserver.model.category.Category;
import explorewithmeserver.model.event.*;
import explorewithmeserver.repository.CategoryRepository;
import explorewithmeserver.repository.CommentRepository;
import explorewithmestats.model.ViewStatsDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EventMapper {

    private final ModelMapper mapper;

    private final CategoryRepository categoryRepository;

    private final CommentRepository commentRepository;

    private final EventClient client;

    private final CommentMapper commentMapper;


    Converter<Long, Category> convertToCategory = src -> src
            .getSource() == null ? null : findCategory(src.getSource());

    Converter<Location, Double> convertLatitude = src -> src
            .getSource() == null ? null : src.getSource().getLat();

    Converter<Location, Double> convertLongitude = src -> src
            .getSource() == null ? null : src.getSource().getLon();

    Converter<Long, Long> convertId = src -> src
            .getSource() == null ? null : src.getSource();


    public EventDto mapToEventDto(Event event) {
        EventDto eventDto = mapper.map(event, EventDto.class);
        eventDto.setLocation(Location.builder()
                .lat(event.getLatitude())
                .lon(event.getLongitude())
                .build());
        setViews(event, eventDto);
        convertComment(event, eventDto);
        return eventDto;
    }

    public Event mapToEvent(NewEventDto newEventDto) {
        mapper.typeMap(NewEventDto.class, Event.class)
                .addMappings(m -> m.using(convertToCategory)
                        .map(NewEventDto::getCategory, Event::setCategory))
                .addMappings(lat -> lat.using(convertLatitude)
                        .map(NewEventDto::getLocation, Event::setLatitude))
                .addMappings(lon -> lon.using(convertLongitude)
                        .map(NewEventDto::getLocation, Event::setLongitude));
        return mapper.map(newEventDto, Event.class);
    }


    public void mapToEvent(EventUpdateDto eventUpdateDto, Event event) {
        mapper.typeMap(EventUpdateDto.class, Event.class)
                .addMappings(m -> m.using(convertId)
                        .map(EventUpdateDto::getEventId, Event::setId));
        mapper.map(eventUpdateDto, event);
    }

    public EventShortDto mapToEventShortDto(Event event) {
        EventShortDto eventShortDto = mapper.map(event, EventShortDto.class);
        setViews(event, eventShortDto);
        return eventShortDto;
    }

    private void setViews(Event event, EventShortDto eventShortDto) {
        String uri = "/events/" + event.getId();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<ViewStatsDto> views = client.getViews(
                event.getCreatedOn().format(formatter),
                LocalDateTime.now().format(formatter),
                Collections.singletonList(uri),
                false);
        if (!views.isEmpty()) {
            ViewStatsDto view = views
                    .stream()
                    .filter(viewStats -> viewStats.getUri().equals(uri))
                    .findAny().orElseThrow();
            eventShortDto.setViews(view.getHits());
        }
    }

    private void setViews(Event event, EventDto eventDto) {
        String uri = "/events/" + event.getId();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<ViewStatsDto> views = client.getViews(
                event.getCreatedOn().format(formatter),
                LocalDateTime.now().format(formatter),
                Collections.singletonList(uri),
                false);
        if (!views.isEmpty()) {
            ViewStatsDto view = views
                    .stream()
                    .filter(viewStats -> viewStats.getUri().equals(uri))
                    .findAny().orElseThrow();
            eventDto.setViews(view.getHits());
        }
    }

    private void convertComment(Event event, EventDto eventDto) {
        eventDto.setComments(commentRepository.findAllCommentsByEvent(event.getId()).stream()
                .map(commentMapper::mapToCommentDto)
                .collect(Collectors.toList()));
    }

    private Category findCategory(Long id) {
        return categoryRepository.findById(id).orElseThrow();
    }
}
