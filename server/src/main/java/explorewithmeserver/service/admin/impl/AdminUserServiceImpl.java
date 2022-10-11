package explorewithmeserver.service.admin.impl;

import explorewithmeserver.exception.NotFoundException;
import explorewithmeserver.mapper.UserMapper;
import explorewithmeserver.model.user.User;
import explorewithmeserver.model.user.UserDto;
import explorewithmeserver.model.user.NewUserDto;
import explorewithmeserver.repository.UserRepository;
import explorewithmeserver.service.admin.AdminUserService;
import explorewithmeserver.valid.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final UserRepository repository;
    private final Validator validator;
    private final UserMapper mapper;

    @Override
    public List<UserDto> getUsers(List<Long> ids, Integer from, Integer size) {
        List<User> list = repository.findAllByIdInOrderByIdAsc(ids, PageRequest.of(from, size)).getContent();
        return list.stream()
                .map(mapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto create(NewUserDto newUserDto) {
        User user = repository.save(mapper.mapToUser(newUserDto));
        return mapper.mapToUserDto(user);
    }

    @Override
    public void delete(Long userId) throws NotFoundException {
        validator.validUser(userId);
        repository.deleteById(userId);
    }
}
