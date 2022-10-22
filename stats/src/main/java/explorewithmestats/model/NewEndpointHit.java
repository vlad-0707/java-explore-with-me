package explorewithmestats.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
public class NewEndpointHit {

    private Long id;
    @NotEmpty
    private String app;
    @NotEmpty
    private String uri;
    @NotEmpty
    private String ip;
    @NotEmpty
    private LocalDateTime time;
}
