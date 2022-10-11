package explorewithmeserver.service.admin;

import explorewithmeserver.exception.NotFoundException;
import explorewithmeserver.model.compilation.CompilationDto;
import explorewithmeserver.model.compilation.NewCompilationDto;

public interface AdminCompilationService {

    CompilationDto create(NewCompilationDto newCompilationDto);

    void deleteById(Long compId) throws NotFoundException;

    void deleteEventFromCompilation(Long compId, Long eventId) throws NotFoundException;

    CompilationDto addEventToCompilation(Long compId, Long eventId) throws NotFoundException;

    void deleteCompilationFromMainPage(Long compId) throws NotFoundException;

    void pinCompilationOnMainPage(Long compId) throws NotFoundException;
}
