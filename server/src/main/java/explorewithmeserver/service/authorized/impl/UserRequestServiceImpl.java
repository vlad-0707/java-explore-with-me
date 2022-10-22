package explorewithmeserver.service.authorized.impl;

import explorewithmeserver.mapper.RequestMapper;
import explorewithmeserver.model.request.Request;
import explorewithmeserver.model.request.RequestDto;
import explorewithmeserver.model.request.RequestState;
import explorewithmeserver.repository.EventRepository;
import explorewithmeserver.repository.RequestRepository;
import explorewithmeserver.repository.UserRepository;
import explorewithmeserver.service.authorized.UserRequestService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserRequestServiceImpl implements UserRequestService {

    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    private final RequestMapper requestMapper;

    private final ModelMapper mapper;

    @Override
    public List<RequestDto> getRequests(Long userId) {
        List<Request> requests = requestRepository.findAllRequestsByUserId(userId);
        return requests.stream()
                .map(this::toRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public RequestDto createRequest(Long userId, Long eventId) {
        Request request = new Request();
        request.setRequester(userRepository.findById(userId).orElseThrow());
        request.setEvent(eventRepository.findById(eventId).orElseThrow());
        request.setStatus(RequestState.PENDING);
        requestRepository.save(request);
        return requestMapper.mapToRequestDto(request);
    }

    @Override
    public RequestDto cancelRequest(Long userId, Long requestId) {
        Request request = requestRepository.findById(requestId).orElseThrow();
        request.setStatus(RequestState.CANCELED);
        requestRepository.save(request);
        return requestMapper.mapToRequestDto(request);
    }

    private RequestDto toRequestDto(Request request) {
        return mapper.map(request, RequestDto.class);
    }
}
