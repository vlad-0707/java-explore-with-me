package explorewithmeserver.mapper;

import explorewithmeserver.model.compilation.Compilation;
import explorewithmeserver.model.compilation.CompilationDto;
import explorewithmeserver.model.compilation.NewCompilationDto;
import explorewithmeserver.model.event.Event;
import explorewithmeserver.model.event.EventShortDto;
import explorewithmeserver.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CompilationMapper {

    private final ModelMapper modelMapper;

    private final EventRepository eventRepository;

    private final EventMapper eventMapper;

    public Compilation mapToCompilation(NewCompilationDto newCompilationDto) {
        Compilation compilation = modelMapper.map(newCompilationDto, Compilation.class);
        compilation.setEvents(mapToUserShortDto(newCompilationDto.getEvents()));
        return compilation;
    }

    public CompilationDto mapToCompilationDto(Compilation compilation) {
        List<EventShortDto> events = compilation.getEvents().stream()
                .map(eventMapper::mapToEventShortDto)
                .collect(Collectors.toList());
        CompilationDto compilationDto = modelMapper.map(compilation, CompilationDto.class);
        compilationDto.setEvents(events);
        return compilationDto;
    }

    private List<Event> mapToUserShortDto(List<Long> ids) {
        return eventRepository.findEventsById(ids);
    }
}
