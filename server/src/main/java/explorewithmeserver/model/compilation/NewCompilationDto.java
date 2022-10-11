package explorewithmeserver.model.compilation;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class NewCompilationDto {

    @NotEmpty
    private List<Long> events;
    @NotNull
    private Boolean pinned;
    @NotEmpty
    private String title;
}
