package explorewithmestats.mapper;

import explorewithmestats.model.EndpointHit;
import explorewithmestats.model.NewEndpointHit;
import explorewithmestats.model.ViewStats;
import explorewithmestats.model.ViewStatsDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class Mapper {

    private final ModelMapper mapper;

    Converter<String, LocalDateTime> convertDate = src ->
            src.getSource() == null ? null : mapToDate(src.getSource());

    public EndpointHit mapToEndpointHit(NewEndpointHit newEndpointHit) {
        mapper.typeMap(NewEndpointHit.class, EndpointHit.class)
                .addMappings(m -> m.using(convertDate)
                        .map(NewEndpointHit::getTime, EndpointHit::setTime));
        return mapper.map(newEndpointHit, EndpointHit.class);
    }

    public ViewStatsDto mapToViewStatsDto(ViewStats viewStats) {
        return ViewStatsDto.builder()
                .app(viewStats.getApp())
                .uri(viewStats.getUri())
                .hits(viewStats.getHits())
                .build();
    }


    public LocalDateTime mapToDate(String date) {
        return LocalDateTime.parse(date);
    }
}
