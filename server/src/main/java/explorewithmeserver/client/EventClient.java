package explorewithmeserver.client;

import explorewithmestats.model.NewEndpointHit;
import explorewithmestats.model.ViewStatsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventClient {

    private final WebClient webClient;

    public void addViews(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        String uri = request.getRequestURI();
        webClient.post()
                .uri("/hit")
                .body(Mono.just(NewEndpointHit.builder()
                        .app("ewm-main-service")
                        .uri(uri)
                        .ip(ip)
                        .build()), NewEndpointHit.class)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }


    public List<ViewStatsDto> getViews(String start, String end, List<String> uris, Boolean unique) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/stats")
                        .queryParam("start", start)
                        .queryParam("end", end)
                        .queryParam("uris", uris)
                        .queryParam("unique", unique)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<ViewStatsDto>>() {
                })
                .block();
    }
}
