package explorewithmeserver.controller.admin;

import explorewithmeserver.exception.NotFoundException;
import explorewithmeserver.model.compilation.CompilationDto;
import explorewithmeserver.model.compilation.NewCompilationDto;
import explorewithmeserver.service.admin.AdminCompilationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin/compilations")
@Validated
@RequiredArgsConstructor
public class AdminCompilationController {

    private final AdminCompilationService adminCompilationService;

    @PostMapping
    public CompilationDto create(@Valid @RequestBody NewCompilationDto newCompilationDto) {
        return adminCompilationService.create(newCompilationDto);
    }

    @DeleteMapping("/{compId}")
    public void deleteById(@PathVariable Long compId) throws NotFoundException {
        adminCompilationService.deleteById(compId);
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    public void deleteEventFromCompilation(@PathVariable Long compId,
                                           @PathVariable Long eventId) throws NotFoundException {
        adminCompilationService.deleteEventFromCompilation(compId, eventId);
    }

    @PatchMapping("/{compId}/events/{eventId}")
    public CompilationDto addEventToCompilation(@PathVariable Long compId,
                                                @PathVariable Long eventId) throws NotFoundException {
        return adminCompilationService.addEventToCompilation(compId, eventId);
    }

    @DeleteMapping("/{compId}/pin")
    public void deleteCompilationFromMainPage(@PathVariable Long compId) throws NotFoundException {
        adminCompilationService.deleteCompilationFromMainPage(compId);
    }

    @PatchMapping("/{compId}/pin")
    public void pinCompilationOnMainPage(@PathVariable Long compId) throws NotFoundException {
        adminCompilationService.pinCompilationOnMainPage(compId);
    }
}
