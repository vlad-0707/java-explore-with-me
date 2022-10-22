package explorewithmeserver.model.comments;

import explorewithmeserver.model.event.Event;
import explorewithmeserver.model.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Getter
@Setter
@ToString
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(name = "text")
    private String text;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id", referencedColumnName = "user_id")
    private User author;

    @Column(name = "time")
    private LocalDateTime time;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "event_id", referencedColumnName = "event_id")
    private Event event;
}
