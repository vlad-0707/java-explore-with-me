package explorewithmeserver.mapper;

import explorewithmeserver.model.user.NewUserDto;
import explorewithmeserver.model.user.User;
import explorewithmeserver.model.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper mapper;


    public User mapToUser(NewUserDto newUserDto) {
        return mapper.map(newUserDto, User.class);
    }

    public UserDto mapToUserDto(User user) {
        return mapper.map(user, UserDto.class);
    }
}
