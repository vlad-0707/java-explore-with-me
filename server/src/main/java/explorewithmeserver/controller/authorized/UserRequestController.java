package explorewithmeserver.controller.authorized;

import explorewithmeserver.model.request.RequestDto;
import explorewithmeserver.service.authorized.UserRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
public class UserRequestController {

    private final UserRequestService userRequestService;

    @GetMapping
    public List<RequestDto> getRequests(@PathVariable Long userId) {
        return userRequestService.getRequests(userId);
    }

    @PostMapping
    public RequestDto createRequest(@PathVariable Long userId,
                                    @RequestParam Long eventId) {
        return userRequestService.createRequest(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public RequestDto cancelRequest(@PathVariable Long userId,
                                    @PathVariable Long requestId) {
        return userRequestService.cancelRequest(userId, requestId);
    }
}
