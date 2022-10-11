package explorewithmeserver.service.general;

import explorewithmeserver.exception.NotFoundException;
import explorewithmeserver.model.compilation.CompilationDto;

import java.util.List;

public interface CompilationService {
    List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size);

    CompilationDto getCompilationById(Long compId) throws NotFoundException;
}
