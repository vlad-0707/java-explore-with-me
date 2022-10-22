package explorewithmeserver.service.admin.impl;

import explorewithmeserver.exception.NotFoundException;
import explorewithmeserver.mapper.CompilationMapper;
import explorewithmeserver.model.compilation.Compilation;
import explorewithmeserver.model.compilation.CompilationDto;
import explorewithmeserver.model.compilation.NewCompilationDto;
import explorewithmeserver.model.event.Event;
import explorewithmeserver.repository.CompilationRepository;
import explorewithmeserver.service.admin.AdminCompilationService;
import explorewithmeserver.valid.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminCompilationServiceImpl implements AdminCompilationService {

    private final CompilationRepository repository;
    private final CompilationMapper mapper;
    private final Validator validator;

    @Override
    public CompilationDto create(NewCompilationDto newCompilationDto) {
        Compilation compilation = mapper.mapToCompilation(newCompilationDto);
        repository.save(compilation);
        return mapper.mapToCompilationDto(compilation);
    }

    @Override
    public void deleteById(Long compId) throws NotFoundException {
        validator.validCompilation(compId);
        repository.deleteById(compId);
    }

    @Override
    public void deleteEventFromCompilation(Long compId, Long eventId) throws NotFoundException {
        Compilation compilation = validator.validCompilation(compId);
        Event event = validator.validEvent(eventId);
        compilation.getEvents().remove(event);
        repository.save(compilation);
    }

    @Override
    public CompilationDto addEventToCompilation(Long compId, Long eventId) throws NotFoundException {
        Compilation compilation = validator.validCompilation(compId);
        Event event = validator.validEvent(eventId);
        compilation.getEvents().add(event);
        repository.save(compilation);
        return mapper.mapToCompilationDto(compilation);
    }

    @Override
    public void deleteCompilationFromMainPage(Long compId) throws NotFoundException {
        Compilation compilation = validator.validCompilation(compId);
        compilation.setPinned(false);
        repository.save(compilation);

    }

    @Override
    public void pinCompilationOnMainPage(Long compId) throws NotFoundException {
        Compilation compilation = validator.validCompilation(compId);
        compilation.setPinned(true);
        repository.save(compilation);
    }
}
