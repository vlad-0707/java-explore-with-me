package explorewithmestats.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stats")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EndpointHit {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "app")
    private String app;

    @Column(name = "uri")
    private String uri;

    @Column(name = "ip")
    private String ip;

    @Column(name = "time")
    private LocalDateTime time;
}
