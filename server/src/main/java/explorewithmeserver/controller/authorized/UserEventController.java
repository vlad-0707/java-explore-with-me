package explorewithmeserver.controller.authorized;

import explorewithmeserver.exception.ForbiddenException;
import explorewithmeserver.exception.NotFoundException;
import explorewithmeserver.model.event.*;
import explorewithmeserver.model.request.RequestDto;
import explorewithmeserver.service.authorized.UserEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
public class UserEventController {

    private final UserEventService userEventService;

    @GetMapping
    public List<EventShortDto> getEventsByUserId(@PathVariable Long userId,
                                                 @Min(0) @RequestParam(required = false, defaultValue = "0") Integer from,
                                                 @Min(1) @RequestParam(required = false, defaultValue = "10") Integer size) {
        return userEventService.getEventsByUserId(userId, from, size);
    }

    @PatchMapping
    public EventDto updateEventByUserId(@PathVariable Long userId,
                                        @RequestBody EventUpdateDto event) throws ForbiddenException, NotFoundException {
        return userEventService.updateEventByUserId(userId, event);
    }

    @PostMapping
    public EventDto createEvent(@PathVariable Long userId,
                                @RequestBody NewEventDto event) {
        return userEventService.createEvent(userId, event);
    }

    @GetMapping("/{eventId}")
    public EventDto getEventById(@PathVariable Long userId,
                                 @PathVariable Long eventId) {
        return userEventService.getEventById(userId, eventId);
    }


    @PatchMapping("/{eventId}")
    public EventDto eventCancel(@PathVariable Long userId,
                                @PathVariable Long eventId) {
        return userEventService.eventCancel(userId, eventId);
    }

    @GetMapping("/{eventId}/requests")
    public List<RequestDto> getRequestsByUserId(@PathVariable Long userId,
                                                @PathVariable Long eventId) {
        return userEventService.getRequestsByUserId(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests/{reqId}/confirm")
    public RequestDto confirmRequest(@PathVariable Long userId,
                                     @PathVariable Long eventId,
                                     @PathVariable Long reqId) {
        return userEventService.confirmRequest(userId, eventId, reqId);
    }

    @PatchMapping("/{eventId}/requests/{reqId}/reject")
    public RequestDto rejectRequest(@PathVariable Long userId,
                                    @PathVariable Long eventId,
                                    @PathVariable Long reqId) {
        return userEventService.rejectRequest(userId, eventId, reqId);
    }
}
