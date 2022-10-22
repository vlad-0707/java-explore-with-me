package explorewithmeserver.model.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class NewUserDto {

    @NotEmpty
    private String name;
    @NotEmpty
    @Email
    private String email;
}
