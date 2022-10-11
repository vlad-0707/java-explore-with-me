package explorewithmestats.repository;

import explorewithmestats.model.EndpointHit;
import explorewithmestats.model.ViewStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository extends JpaRepository<EndpointHit, Long> {


    @Query("select distinct(e.uri) as uri, e.app as app, count(e.id) as hits " +
            "from EndpointHit as e " +
            "where :uris is null or e.uri in :uris " +
            "and e.time between :start and :end " +
            "group by e.app, (e.uri)")
    List<ViewStats> getViews(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("select distinct(e.uri) as uri, e.app as app, count(e.id) as hits " +
            "from EndpointHit e " +
            "where :uris is null or e.uri in :uris " +
            "and e.time between :start and :end " +
            "group by e.app, e.uri, e.ip")
    List<ViewStats> getUniqueViews(LocalDateTime start, LocalDateTime end, List<String> uris);

}
