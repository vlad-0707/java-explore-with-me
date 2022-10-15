package explorewithmeserver.service.authorized.impl;

import explorewithmeserver.exception.ForbiddenException;
import explorewithmeserver.exception.NotFoundException;
import explorewithmeserver.mapper.CommentMapper;
import explorewithmeserver.mapper.EventMapper;
import explorewithmeserver.mapper.RequestMapper;
import explorewithmeserver.model.comments.Comment;
import explorewithmeserver.model.comments.CommentDto;
import explorewithmeserver.model.comments.NewCommentDto;
import explorewithmeserver.model.event.*;
import explorewithmeserver.model.request.Request;
import explorewithmeserver.model.request.RequestDto;
import explorewithmeserver.model.request.RequestState;
import explorewithmeserver.repository.CommentRepository;
import explorewithmeserver.repository.EventRepository;
import explorewithmeserver.repository.RequestRepository;
import explorewithmeserver.repository.UserRepository;
import explorewithmeserver.service.authorized.UserEventService;
import explorewithmeserver.valid.Validator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserEventServiceImpl implements UserEventService {

    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final EventMapper eventMapper;
    private final Validator validator;
    private final ModelMapper modelMapper;
    private final RequestMapper requestMapper;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    public List<EventShortDto> getEventsByUserId(Long userId, Integer from, Integer size) {
        return eventRepository.findEventsByInitiatorId(userId, PageRequest.of(from, size)).getContent().stream()
                .map(eventMapper::mapToEventShortDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventDto updateEventByUserId(Long userId, EventUpdateDto eventUpdateDto)
            throws ForbiddenException, NotFoundException {

        Event event = eventRepository.findById(eventUpdateDto.getEventId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Event with id=%d was not found.", eventUpdateDto.getEventId())));

        if (event.getState().equals(State.PUBLISHED)) {
            throw new ForbiddenException("Only pending or canceled events can be changed");
        }

        eventMapper.mapToEvent(eventUpdateDto, event);

        if (event.getState().equals(State.CANCELED)) {
            event.setState(State.PENDING);
        }

        eventRepository.save(event);
        return eventMapper.mapToEventDto(event);
    }

    @Override
    public EventDto createEvent(Long userId, NewEventDto newEventDto) {
        Event event = eventMapper.mapToEvent(newEventDto);
        event.setInitiator(userRepository.findById(userId).orElseThrow());
        event.setState(State.PENDING);
        eventRepository.save(event);
        return eventMapper.mapToEventDto(event);
    }

    @Override
    public EventDto getEventById(Long userId, Long eventId) {
        Event event = eventRepository.findEventByIdAndInitiatorId(eventId, userId);
        return eventMapper.mapToEventDto(event);
    }

    @Override
    public EventDto eventCancel(Long userId, Long eventId) {
        Event event = eventRepository.findEventByIdAndInitiatorId(eventId, userId);
        if (event.getState().equals(State.PENDING)) {
            event.setState(State.CANCELED);
            eventRepository.save(event);
        }
        return eventMapper.mapToEventDto(event);
    }

    @Override
    public List<RequestDto> getRequestsByUserId(Long userId, Long eventId) {
        List<Request> requests = requestRepository.findRequestsByRequesterIdAndEventId(userId, eventId);
        return requests.stream()
                .map(requestMapper::mapToRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public RequestDto confirmRequest(Long userId, Long eventId, Long reqId) {
        Request request = requestRepository.findRequestByUserIdAndEventIdAndReqId(userId, eventId, reqId);
        request.setStatus(RequestState.CONFIRMED);
        requestRepository.saveAndFlush(request);
        return toRequestDto(request);
    }

    @Override
    public RequestDto rejectRequest(Long userId, Long eventId, Long reqId) {
        Request request = requestRepository.findRequestByUserId(reqId);
        request.setStatus(RequestState.REJECTED);
        requestRepository.saveAndFlush(request);
        return toRequestDto(request);
    }

    @Override
    public CommentDto addComment(Long userId, Long eventId, NewCommentDto newCommentDto) throws NotFoundException {
        validator.validUser(userId);
        validator.validEvent(eventId);
        Comment comment = new Comment();
        comment.setText(newCommentDto.getText());
        comment.setAuthor(userRepository.findById(userId).orElseThrow());
        comment.setTime(LocalDateTime.now());
        comment.setEvent(eventRepository.findById(eventId).orElseThrow());
        return commentMapper.mapToCommentDto(commentRepository.save(comment));
    }

    @Override
    public void deleteComment(Long userId, Long eventId, Long commentId) throws NotFoundException {
        validator.validUser(userId);
        validator.validEvent(eventId);
        commentRepository.deleteById(commentId);
    }

    private RequestDto toRequestDto(Request request) {
        return modelMapper.map(request, RequestDto.class);
    }
}
