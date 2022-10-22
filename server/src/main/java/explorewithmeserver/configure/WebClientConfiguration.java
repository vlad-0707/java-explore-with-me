package explorewithmeserver.configure;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class WebClientConfiguration {
    @Value("${ewm-stats.url}")
    private String baseUrl;

    @Bean
    public WebClient webClientWithTimeout() {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }
}
