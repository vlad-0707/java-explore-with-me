package explorewithmestats.service;

import explorewithmestats.model.NewEndpointHit;
import explorewithmestats.model.ViewStatsDto;


import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);

    void save(NewEndpointHit newEndpointHit);
}
