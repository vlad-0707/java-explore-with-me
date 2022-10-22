package explorewithmeserver.model.comments;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class CommentDto {

    private Long id;
    @NotNull
    @NotBlank
    private String text;
    private String author;
    private LocalDateTime time;
}
