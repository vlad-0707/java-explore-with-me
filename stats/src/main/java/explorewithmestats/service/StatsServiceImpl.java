package explorewithmestats.service;

import explorewithmestats.mapper.Mapper;
import explorewithmestats.model.EndpointHit;
import explorewithmestats.model.NewEndpointHit;
import explorewithmestats.model.ViewStatsDto;
import explorewithmestats.repository.StatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final StatsRepository repository;

    private final Mapper mapper;

    @Override
    public void save(NewEndpointHit newEndpointHit) {
        EndpointHit endpointHit = mapper.mapToEndpointHit(newEndpointHit);
        endpointHit.setTime(LocalDateTime.now());
        repository.save(endpointHit);
    }

    @Override
    public List<ViewStatsDto> getStats(LocalDateTime start,
                                       LocalDateTime end,
                                       List<String> uris,
                                       Boolean unique) {
        if (unique) {
            return repository.getUniqueViews(start, end, uris).stream()
                    .map(mapper::mapToViewStatsDto)
                    .collect(Collectors.toList());
        } else {
            return repository.getViews(start, end, uris).stream()
                    .map(mapper::mapToViewStatsDto)
                    .collect(Collectors.toList());
        }
    }
}
