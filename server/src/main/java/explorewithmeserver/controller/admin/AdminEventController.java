package explorewithmeserver.controller.admin;

import explorewithmeserver.exception.ForbiddenException;
import explorewithmeserver.exception.NotFoundException;
import explorewithmeserver.model.event.*;
import explorewithmeserver.service.admin.AdminEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/admin/events")
@Validated
@RequiredArgsConstructor
public class AdminEventController {

    private final AdminEventService adminEventService;

    @GetMapping
    public List<EventDto> getEvents(@RequestParam List<Long> users,
                                    @RequestParam List<State> states,
                                    @RequestParam List<Long> categories,
                                    @RequestParam
                                    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                    @RequestParam
                                    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                    @Min(0) @RequestParam(required = false, defaultValue = "0") Integer from,
                                    @Min(1) @RequestParam(required = false, defaultValue = "10") Integer size) {
        return adminEventService.getEvents(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PutMapping("/{eventId}")
    public EventDto updateEvent(@PathVariable Long eventId,
                                @RequestBody EventUpdateDto eventUpdateDto) throws NotFoundException {
        return adminEventService.updateEvent(eventId, eventUpdateDto);
    }

    @PatchMapping("/{eventId}/publish")
    public EventDto publishEvent(@PathVariable Long eventId) throws ForbiddenException, NotFoundException {
        return adminEventService.publishEvent(eventId);
    }

    @PatchMapping("/{eventId}/reject")
    public EventDto rejectEvent(@PathVariable Long eventId) throws ForbiddenException, NotFoundException {
        return adminEventService.rejectEvent(eventId);
    }
}
