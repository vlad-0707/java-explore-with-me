package explorewithmeserver.service.general.impl;

import explorewithmeserver.exception.NotFoundException;
import explorewithmeserver.mapper.CompilationMapper;
import explorewithmeserver.model.compilation.Compilation;
import explorewithmeserver.model.compilation.CompilationDto;
import explorewithmeserver.repository.CompilationRepository;
import explorewithmeserver.service.general.CompilationService;
import explorewithmeserver.valid.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository repository;

    private final Validator validator;
    private final CompilationMapper mapper;

    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size) {
        List<Compilation> compilations = repository
                .findCompilationsByPinnedIsOrderByIdAsc(pinned, PageRequest.of(from, size)).getContent();
        return compilations.stream()
                .map(mapper::mapToCompilationDto)
                .collect(Collectors.toList());
    }

    @Override
    public CompilationDto getCompilationById(Long compId) throws NotFoundException {
        Compilation compilation = validator.validCompilation(compId);
        return mapper.mapToCompilationDto(compilation);
    }


}
