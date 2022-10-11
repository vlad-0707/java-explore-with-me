package explorewithmeserver.service.admin;

import explorewithmeserver.exception.NotFoundException;
import explorewithmeserver.model.user.UserDto;
import explorewithmeserver.model.user.NewUserDto;

import java.util.List;

public interface AdminUserService {
    List<UserDto> getUsers(List<Long> ids, Integer from, Integer size);

    UserDto create(NewUserDto user);

    void delete(Long userId) throws NotFoundException;
}
